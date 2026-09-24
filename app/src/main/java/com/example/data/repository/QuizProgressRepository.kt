package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.levels.ReasoningLevelsRepository
import com.example.data.model.DayTargetProgress
import com.example.data.model.LevelProgress
import com.example.util.StreakNotificationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Offline repository to store and track language preference, level completion,
 * daily streak, points, and scores locally.
 */
class QuizProgressRepository(context: Context) {
    private val appContext: Context = context.applicationContext

    private val prefs: SharedPreferences =
        appContext.getSharedPreferences("quiz4exam_offline_prefs", Context.MODE_PRIVATE)

    private val _language = MutableStateFlow<String?>(prefs.getString(KEY_LANGUAGE, null))
    val language: StateFlow<String?> = _language.asStateFlow()

    private val _progressMap = MutableStateFlow<Map<Int, LevelProgress>>(loadAllProgress())
    val progressMap: StateFlow<Map<Int, LevelProgress>> = _progressMap.asStateFlow()

    private val _totalPoints = MutableStateFlow<Int>(loadTotalPoints())
    val totalPoints: StateFlow<Int> = _totalPoints.asStateFlow()

    private val _soundEnabled = MutableStateFlow<Boolean>(prefs.getBoolean(KEY_SOUND_ENABLED, true))
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _dailyPoints = MutableStateFlow<Int>(0)
    val dailyPoints: StateFlow<Int> = _dailyPoints.asStateFlow()

    private val _currentStreak = MutableStateFlow<Int>(0)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    init {
        checkAndSyncDailyStreakOnOpen()
        StreakNotificationManager.scheduleDailyStreakReminder(appContext)
    }

    fun setSoundEnabled(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_SOUND_ENABLED, enabled).apply()
        _soundEnabled.value = enabled
    }

    fun isSoundEnabled(): Boolean = _soundEnabled.value

    fun setLanguage(lang: String) {
        prefs.edit().putString(KEY_LANGUAGE, lang).apply()
        _language.value = lang
    }

    fun getLanguage(): String? = prefs.getString(KEY_LANGUAGE, null)

    private fun loadTotalPoints(): Int = prefs.getInt(KEY_TOTAL_POINTS, 0)

    fun getTotalPoints(): Int = _totalPoints.value

    private fun loadAllProgress(): Map<Int, LevelProgress> {
        val map = mutableMapOf<Int, LevelProgress>()
        for (lvl in 1..ReasoningLevelsRepository.TOTAL_LEVELS) {
            val isPassed = prefs.getBoolean("level_${lvl}_passed", false)
            val hasAttempted = prefs.getBoolean("lvl_${lvl}_attempted", isPassed)
            val highestScore = prefs.getInt("lvl_${lvl}_high_score", 0)
            val lastScore = prefs.getInt("lvl_${lvl}_last_score", 0)
            map[lvl] = LevelProgress(
                levelNumber = lvl,
                isCompleted = isPassed,
                highestScore = highestScore,
                lastAttemptScore = lastScore,
                hasAttempted = hasAttempted
            )
        }
        return map
    }

    fun isLevelPassed(levelNumber: Int): Boolean {
        return _progressMap.value[levelNumber]?.isCompleted == true ||
                prefs.getBoolean("level_${levelNumber}_passed", false)
    }

    /**
     * Level 1 is always unlocked by default.
     * Level N unlocks ONLY if user scored at least 5 out of 10 in Level (N-1).
     * Points have ZERO role in unlocking levels.
     */
    fun isLevelUnlocked(levelNumber: Int): Boolean {
        if (levelNumber <= 1) return true
        if (prefs.getBoolean("level_${levelNumber}_unlocked", false)) {
            return true
        }
        val prevLevel = levelNumber - 1
        val prevProgress = _progressMap.value[prevLevel]
        val prevHighScore = prevProgress?.highestScore ?: prefs.getInt("lvl_${prevLevel}_high_score", 0)
        val prevPassed = prevProgress?.isCompleted == true || prefs.getBoolean("level_${prevLevel}_passed", false)
        return prevHighScore >= ReasoningLevelsRepository.PASS_MARK_THRESHOLD || prevPassed
    }

    /**
     * Daily Streak & Calendar Date Check on App Open:
     * - Compare lastActiveDate with today's date.
     * - If lastActiveDate is today, maintain current progress.
     * - If lastActiveDate was yesterday and daily target (15 pts) was met, maintain streak.
     * - If a day was missed (more than 1 day gap) or yesterday's 15-point target was not achieved, reset currentStreak to 0.
     */
    fun checkAndSyncDailyStreakOnOpen() {
        val today = getTodayDateKey()
        val yesterday = getYesterdayDateKey()
        val lastActiveDate = prefs.getString(KEY_LAST_ACTIVE_DATE, null)
        var streak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        var todayPoints = prefs.getInt("daily_points_$today", 0)

        if (lastActiveDate == null) {
            // First run
            streak = 0
            todayPoints = 0
            prefs.edit()
                .putString(KEY_LAST_ACTIVE_DATE, today)
                .putInt(KEY_CURRENT_STREAK, streak)
                .putInt("daily_points_$today", todayPoints)
                .apply()
        } else if (lastActiveDate == today) {
            // Same day, maintain progress
            todayPoints = prefs.getInt("daily_points_$today", 0)
        } else if (lastActiveDate == yesterday) {
            // Was active yesterday: check if yesterday's 15-point target was met
            val yesterdayPoints = prefs.getInt("daily_points_$yesterday", 0)
            val yesterdayCompleted = yesterdayPoints >= DAILY_TARGET_POINTS ||
                    prefs.getString(KEY_LAST_COMPLETED_DATE, "") == yesterday

            if (yesterdayCompleted) {
                // Maintained streak!
            } else {
                // Target was not met yesterday -> reset streak
                streak = 0
                prefs.edit().putInt(KEY_CURRENT_STREAK, 0).apply()
            }
            // Today is a new day with 0 points so far
            todayPoints = prefs.getInt("daily_points_$today", 0)
            prefs.edit()
                .putString(KEY_LAST_ACTIVE_DATE, today)
                .apply()
        } else {
            // More than 1 day gap -> reset streak to 0
            streak = 0
            todayPoints = prefs.getInt("daily_points_$today", 0)
            prefs.edit()
                .putInt(KEY_CURRENT_STREAK, 0)
                .putString(KEY_LAST_ACTIVE_DATE, today)
                .apply()
        }

        _dailyPoints.value = todayPoints
        _currentStreak.value = streak
    }

    /**
     * Saves level attempt, awards 3 points per correct answer, unlocks Level N+1
     * if score >= 5, and updates daily streak progress.
     */
    fun saveLevelAttempt(levelNumber: Int, score: Int, isPassed: Boolean): Int {
        val currentProgress = _progressMap.value[levelNumber] ?: LevelProgress(levelNumber = levelNumber)
        val newHighest = maxOf(currentProgress.highestScore, score)
        val passedThisAttempt = score >= ReasoningLevelsRepository.PASS_MARK_THRESHOLD
        val wasAlreadyPassed = prefs.getBoolean("level_${levelNumber}_passed", false)
        val newlyPassed = wasAlreadyPassed || passedThisAttempt || isPassed

        val pointsEarned = score * ReasoningLevelsRepository.POINTS_PER_CORRECT_ANSWER
        val newTotalPoints = _totalPoints.value + pointsEarned

        val today = getTodayDateKey()
        val currentDayPts = prefs.getInt("daily_points_$today", 0) + pointsEarned

        val editor = prefs.edit()
            .putBoolean("level_${levelNumber}_passed", newlyPassed)
            .putBoolean("lvl_${levelNumber}_attempted", true)
            .putInt("lvl_${levelNumber}_high_score", newHighest)
            .putInt("lvl_${levelNumber}_last_score", score)
            .putInt(KEY_TOTAL_POINTS, newTotalPoints)
            .putInt("daily_points_$today", currentDayPts)
            .putString(KEY_LAST_ACTIVE_DATE, today)

        // Sequential unlock: Level N unlocks ONLY if user answered at least 5 out of 10 in Level N-1
        if (passedThisAttempt || newHighest >= ReasoningLevelsRepository.PASS_MARK_THRESHOLD) {
            val nextLevel = levelNumber + 1
            if (nextLevel <= ReasoningLevelsRepository.TOTAL_LEVELS) {
                editor.putBoolean("level_${nextLevel}_unlocked", true)
            }
        }

        // Streak Engine:
        // Once the user accumulates at least 15 points within current calendar day:
        // - Increment currentStreak by 1 (only once per day).
        // - Mark today's goal as completed.
        // - Update lastActiveDate to today's date.
        var streak = prefs.getInt(KEY_CURRENT_STREAK, 0)
        val streakIncrementedDate = prefs.getString(KEY_STREAK_INCREMENTED_DATE, "")

        if (currentDayPts >= DAILY_TARGET_POINTS) {
            if (streakIncrementedDate != today) {
                streak += 1
                editor.putInt(KEY_CURRENT_STREAK, streak)
                editor.putString(KEY_STREAK_INCREMENTED_DATE, today)
                editor.putString(KEY_LAST_COMPLETED_DATE, today)
            }
            // Intelligent Cancellation: immediately cancel today's scheduled reminder
            StreakNotificationManager.onGoalAchieved(appContext)
        }

        editor.commit()

        _totalPoints.value = newTotalPoints
        _dailyPoints.value = currentDayPts
        _currentStreak.value = streak

        val updatedMap = _progressMap.value.toMutableMap()
        updatedMap[levelNumber] = LevelProgress(
            levelNumber = levelNumber,
            isCompleted = newlyPassed,
            highestScore = newHighest,
            lastAttemptScore = score,
            hasAttempted = true
        )
        _progressMap.value = updatedMap

        return pointsEarned
    }

    fun getTodayDateKey(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(Date())
    }

    private fun getYesterdayDateKey(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, -1)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        return sdf.format(calendar.time)
    }

    fun getDailyTarget(): Int = DAILY_TARGET_POINTS

    fun getTodayPoints(): Int {
        val todayKey = getTodayDateKey()
        return prefs.getInt("daily_points_$todayKey", 0)
    }

    fun getCurrentStreak(): Int = _currentStreak.value

    fun getWeeklyTargetProgress(): List<DayTargetProgress> {
        val target = DAILY_TARGET_POINTS
        val calendar = Calendar.getInstance()
        val todayKey = getTodayDateKey()

        // Set to Monday of the current week
        calendar.firstDayOfWeek = Calendar.MONDAY
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)

        val dayFormat = SimpleDateFormat("EEE", Locale.US)
        val keyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val result = mutableListOf<DayTargetProgress>()

        val todayPts = getTodayPoints()
        for (i in 0 until 7) {
            val dateKey = keyFormat.format(calendar.time)
            val dayName = dayFormat.format(calendar.time)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
            val isToday = dateKey == todayKey
            val earned = if (isToday) todayPts else prefs.getInt("daily_points_$dateKey", 0)

            result.add(
                DayTargetProgress(
                    dayName = dayName,
                    dateString = dateKey,
                    dayOfMonth = dayOfMonth,
                    pointsEarned = earned,
                    targetPoints = target,
                    isToday = isToday,
                    isAchieved = earned >= target
                )
            )
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }
        return result
    }

    // Testing helper
    fun setStreakForTesting(streak: Int, lastActive: String?, dailyPts: Int) {
        val today = getTodayDateKey()
        val editor = prefs.edit()
            .putInt(KEY_CURRENT_STREAK, streak)
            .putInt("daily_points_$today", dailyPts)
        if (lastActive != null) {
            editor.putString(KEY_LAST_ACTIVE_DATE, lastActive)
        }
        editor.commit()
        _currentStreak.value = streak
        _dailyPoints.value = dailyPts
    }

    companion object {
        const val DAILY_TARGET_POINTS = 15 // 15 points per day = 5 correct answers
        private const val KEY_LANGUAGE = "language"
        private const val KEY_TOTAL_POINTS = "total_accumulated_points"
        private const val KEY_SOUND_ENABLED = "sound_effects_enabled"
        private const val KEY_LAST_ACTIVE_DATE = "last_active_date"
        private const val KEY_CURRENT_STREAK = "current_streak"
        private const val KEY_STREAK_INCREMENTED_DATE = "streak_incremented_for_date"
        private const val KEY_LAST_COMPLETED_DATE = "last_completed_date"
    }
}
