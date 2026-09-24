package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.levels.ReasoningLevelsRepository
import com.example.data.model.LevelInfo
import com.example.data.model.LevelProgress
import com.example.data.model.Question
import com.example.data.repository.QuizProgressRepository
import com.example.util.AppStrings
import com.example.util.SoundManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

sealed interface ScreenState {
    data object FirstLanguage : ScreenState
    data object Home : ScreenState
    data object Settings : ScreenState
    data object ReasoningLevels : ScreenState
    data object SelectState : ScreenState
    data class Quiz(
        val levelNumber: Int,
        val questions: List<Question>,
        val currentQuestionIndex: Int = 0,
        val userAnswers: Map<Int, Int> = emptyMap()
    ) : ScreenState
    data class Result(
        val levelNumber: Int,
        val score: Int,
        val totalQuestions: Int = 10,
        val pointsEarned: Int = 0,
        val totalAccumulatedPoints: Int = 0,
        val isNextLevelUnlocked: Boolean = false,
        val isPassed: Boolean = false,
        val hasNextLevel: Boolean = false,
        val questions: List<Question> = emptyList(),
        val userAnswers: Map<Int, Int> = emptyMap()
    ) : ScreenState
    data class DetailedReview(
        val levelNumber: Int,
        val questions: List<Question>,
        val userAnswers: Map<Int, Int>
    ) : ScreenState
}

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private val progressRepo = QuizProgressRepository(application)

    private val _currentLanguage = MutableStateFlow(progressRepo.getLanguage() ?: "en")
    val currentLanguage: StateFlow<String> = _currentLanguage.asStateFlow()

    private val _screenState = MutableStateFlow<ScreenState>(
        if (progressRepo.getLanguage() == null) ScreenState.FirstLanguage else ScreenState.Home
    )
    val screenState: StateFlow<ScreenState> = _screenState.asStateFlow()

    val levelProgressMap: StateFlow<Map<Int, LevelProgress>> = progressRepo.progressMap
    val totalPoints: StateFlow<Int> = progressRepo.totalPoints
    val soundEnabled: StateFlow<Boolean> = progressRepo.soundEnabled
    val currentStreak: StateFlow<Int> = progressRepo.currentStreak
    val todayPoints: StateFlow<Int> = progressRepo.dailyPoints

    val dailyTarget: Int = QuizProgressRepository.DAILY_TARGET_POINTS

    init {
        SoundManager.setSoundEnabled(progressRepo.isSoundEnabled())
        progressRepo.checkAndSyncDailyStreakOnOpen()
    }

    fun setSoundEnabled(enabled: Boolean) {
        progressRepo.setSoundEnabled(enabled)
        SoundManager.setSoundEnabled(enabled)
        if (enabled) {
            SoundManager.playTap()
        }
    }

    // Dynamic levels computation
    val levels: StateFlow<List<LevelInfo>> = combine(
        progressRepo.progressMap,
        _currentLanguage
    ) { progressMap, lang ->
        ReasoningLevelsRepository.getAllLevels(
            language = lang,
            isUnlockedCheck = { levelNum -> progressRepo.isLevelUnlocked(levelNum) },
            isCompletedCheck = { levelNum -> progressMap[levelNum]?.isCompleted == true }
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ReasoningLevelsRepository.getAllLevels(
            language = _currentLanguage.value,
            isUnlockedCheck = { progressRepo.isLevelUnlocked(it) },
            isCompletedCheck = { progressRepo.isLevelPassed(it) }
        )
    )

    private val _toastEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val toastEvent: SharedFlow<String> = _toastEvent.asSharedFlow()

    companion object {
        const val QUIZ_DURATION_SECONDS = 10 * 60 // 10 minutes continuous timer
    }

    private var timerJob: Job? = null
    private val _remainingTimeSeconds = MutableStateFlow(QUIZ_DURATION_SECONDS)
    val remainingTimeSeconds: StateFlow<Int> = _remainingTimeSeconds.asStateFlow()

    private fun startTimer() {
        timerJob?.cancel()
        _remainingTimeSeconds.value = QUIZ_DURATION_SECONDS
        timerJob = viewModelScope.launch {
            while (isActive && _remainingTimeSeconds.value > 0) {
                delay(1000L)
                if (_remainingTimeSeconds.value > 0) {
                    _remainingTimeSeconds.value -= 1
                }
                if (_remainingTimeSeconds.value <= 0) {
                    _toastEvent.tryEmit(AppStrings.timeUpAlert(_currentLanguage.value))
                    submitQuiz()
                    break
                }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    // Navigation and Language Actions
    fun selectFirstLanguage(lang: String) {
        SoundManager.playTap()
        progressRepo.setLanguage(lang)
        _currentLanguage.value = lang
        _screenState.value = ScreenState.Home
    }

    fun changeLanguage(lang: String) {
        SoundManager.playTap()
        progressRepo.setLanguage(lang)
        _currentLanguage.value = lang
    }

    fun openSettings() {
        SoundManager.playTap()
        _screenState.value = ScreenState.Settings
    }

    fun goHome() {
        SoundManager.playTap()
        stopTimer()
        _screenState.value = ScreenState.Home
    }

    fun openReasoning() {
        SoundManager.playTap()
        stopTimer()
        _screenState.value = ScreenState.ReasoningLevels
    }

    fun openSelectState() {
        SoundManager.playTap()
        stopTimer()
        _screenState.value = ScreenState.SelectState
    }

    fun startQuiz(levelNumber: Int) {
        SoundManager.playTap()
        val questions = ReasoningLevelsRepository.getQuestionsForLevel(levelNumber, _currentLanguage.value)
        if (questions.isNotEmpty()) {
            _screenState.value = ScreenState.Quiz(
                levelNumber = levelNumber,
                questions = questions,
                currentQuestionIndex = 0,
                userAnswers = emptyMap()
            )
            startTimer()
        }
    }

    fun startNextLevel(currentLevel: Int) {
        SoundManager.playTap()
        val nextLevel = currentLevel + 1
        startQuiz(nextLevel)
    }

    /**
     * Option selection only highlights the choice.
     * Auto-advance on tap is removed; user must tap "Next →".
     */
    fun selectAnswer(selectedIndex: Int) {
        SoundManager.playOptionSelected()
        val current = _screenState.value as? ScreenState.Quiz ?: return
        val updatedAnswers = current.userAnswers.toMutableMap()
        updatedAnswers[current.currentQuestionIndex] = selectedIndex
        _screenState.value = current.copy(userAnswers = updatedAnswers)
    }

    /**
     * Skips the current question. Advances to the next question,
     * or submits if on the last question.
     */
    fun skipQuestion() {
        SoundManager.playTap()
        val current = _screenState.value as? ScreenState.Quiz ?: return
        val updatedAnswers = current.userAnswers.toMutableMap()
        updatedAnswers.remove(current.currentQuestionIndex)

        if (current.currentQuestionIndex < current.questions.size - 1) {
            _screenState.value = current.copy(
                userAnswers = updatedAnswers,
                currentQuestionIndex = current.currentQuestionIndex + 1
            )
        } else {
            _screenState.value = current.copy(userAnswers = updatedAnswers)
            submitQuiz()
        }
    }

    /**
     * Advances to the next question or submits if on the last question.
     */
    fun nextQuestion() {
        val current = _screenState.value as? ScreenState.Quiz ?: return
        val selectedOption = current.userAnswers[current.currentQuestionIndex]
        if (selectedOption == null) {
            val alertMessage = AppStrings.selectOptionAlert(_currentLanguage.value)
            _toastEvent.tryEmit(alertMessage)
            return
        }
        SoundManager.playTap()
        if (current.currentQuestionIndex == current.questions.size - 1) {
            submitQuiz()
            return
        }
        _screenState.value = current.copy(
            currentQuestionIndex = current.currentQuestionIndex + 1
        )
    }

    /**
     * Goes to the previous question in the quiz.
     */
    fun previousQuestion() {
        SoundManager.playTap()
        val current = _screenState.value as? ScreenState.Quiz ?: return
        if (current.currentQuestionIndex > 0) {
            _screenState.value = current.copy(
                currentQuestionIndex = current.currentQuestionIndex - 1
            )
        }
    }

    /**
     * Submits the quiz, calculates score and points (+3 per correct answer),
     * unlocks Level (N+1) only if score >= 5, updates daily streak, and transitions to Result.
     */
    fun submitQuiz() {
        val current = _screenState.value as? ScreenState.Quiz ?: return
        stopTimer()

        var calculatedScore = 0
        current.questions.forEachIndexed { index, question ->
            if (current.userAnswers[index] == question.correctOptionIndex) {
                calculatedScore++
            }
        }

        val total = current.questions.size
        val pointsEarned = calculatedScore * ReasoningLevelsRepository.POINTS_PER_CORRECT_ANSWER
        val isPassed = calculatedScore >= ReasoningLevelsRepository.PASS_MARK_THRESHOLD

        // Persist attempt locally and increment daily streak if 15 pts reached
        progressRepo.saveLevelAttempt(
            levelNumber = current.levelNumber,
            score = calculatedScore,
            isPassed = isPassed
        )

        val totalAccumulatedPoints = progressRepo.getTotalPoints()
        val nextLevel = current.levelNumber + 1
        val hasNext = ReasoningLevelsRepository.hasNextLevel(current.levelNumber, _currentLanguage.value)
        val isNextLevelUnlocked = if (hasNext) progressRepo.isLevelUnlocked(nextLevel) else false

        _screenState.value = ScreenState.Result(
            levelNumber = current.levelNumber,
            score = calculatedScore,
            totalQuestions = total,
            pointsEarned = pointsEarned,
            totalAccumulatedPoints = totalAccumulatedPoints,
            isNextLevelUnlocked = isNextLevelUnlocked,
            isPassed = isPassed,
            hasNextLevel = hasNext,
            questions = current.questions,
            userAnswers = current.userAnswers
        )
    }

    fun openDetailedReview() {
        SoundManager.playTap()
        val current = _screenState.value as? ScreenState.Result ?: return
        _screenState.value = ScreenState.DetailedReview(
            levelNumber = current.levelNumber,
            questions = current.questions,
            userAnswers = current.userAnswers
        )
    }

    fun backFromDetailedReview() {
        SoundManager.playTap()
        val current = _screenState.value as? ScreenState.DetailedReview ?: return
        // Return to result screen
        var calculatedScore = 0
        current.questions.forEachIndexed { index, question ->
            if (current.userAnswers[index] == question.correctOptionIndex) {
                calculatedScore++
            }
        }
        val isPassed = calculatedScore >= ReasoningLevelsRepository.PASS_MARK_THRESHOLD
        val hasNext = ReasoningLevelsRepository.hasNextLevel(current.levelNumber, _currentLanguage.value)
        val isNextUnlocked = if (hasNext) progressRepo.isLevelUnlocked(current.levelNumber + 1) else false

        _screenState.value = ScreenState.Result(
            levelNumber = current.levelNumber,
            score = calculatedScore,
            totalQuestions = current.questions.size,
            pointsEarned = calculatedScore * ReasoningLevelsRepository.POINTS_PER_CORRECT_ANSWER,
            totalAccumulatedPoints = progressRepo.getTotalPoints(),
            isNextLevelUnlocked = isNextUnlocked,
            isPassed = isPassed,
            hasNextLevel = hasNext,
            questions = current.questions,
            userAnswers = current.userAnswers
        )
    }

    fun retryQuiz() {
        SoundManager.playTap()
        when (val current = _screenState.value) {
            is ScreenState.Result -> startQuiz(current.levelNumber)
            is ScreenState.DetailedReview -> startQuiz(current.levelNumber)
            else -> {}
        }
    }

    fun getLevels(): List<LevelInfo> {
        return ReasoningLevelsRepository.getAllLevels(
            language = _currentLanguage.value,
            isUnlockedCheck = { progressRepo.isLevelUnlocked(it) },
            isCompletedCheck = { progressRepo.isLevelPassed(it) }
        )
    }

    fun setRemainingTimeForTesting(seconds: Int) {
        _remainingTimeSeconds.value = seconds
        if (seconds <= 0) {
            submitQuiz()
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
