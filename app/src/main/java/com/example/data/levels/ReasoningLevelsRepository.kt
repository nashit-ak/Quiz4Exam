package com.example.data.levels

import com.example.data.model.LevelInfo
import com.example.data.model.Question

/**
 * Repository managing reasoning quiz levels and question data for English and Hindi.
 */
object ReasoningLevelsRepository {
    const val TOTAL_LEVELS = 10
    const val QUESTIONS_PER_LEVEL = 10
    const val POINTS_PER_CORRECT_ANSWER = 3
    const val MAX_POINTS_PER_LEVEL = 30
    const val PASS_MARK_THRESHOLD = 5 // User must answer at least 5 out of 10 questions correctly to unlock next level

    /**
     * Retrieves questions for a specific level (1 to 10) in the given language ("en" or "hi").
     */
    fun getQuestionsForLevel(levelNumber: Int, language: String): List<Question> {
        val lang = if (language == "hi") "hi" else "en"
        return when (levelNumber) {
            1 -> Level1Questions.getQuestions(lang)
            2 -> Level2Questions.getQuestions(lang)
            3 -> Level3Questions.getQuestions(lang)
            4 -> Level4Questions.getQuestions(lang)
            5 -> Level5Questions.getQuestions(lang)
            6 -> Level6Questions.getQuestions(lang)
            7 -> Level7Questions.getQuestions(lang)
            8 -> Level8Questions.getQuestions(lang)
            9 -> Level9Questions.getQuestions(lang)
            10 -> Level10Questions.getQuestions(lang)
            else -> emptyList()
        }
    }

    /**
     * Checks if a level has questions available.
     */
    fun hasQuestionsForLevel(levelNumber: Int, language: String): Boolean {
        return getQuestionsForLevel(levelNumber, language).isNotEmpty()
    }

    /**
     * Checks if a level is unlocked.
     * Level 1 is always unlocked by default.
     * Level N (N > 1) is unlocked if isUnlockedCheck(levelNumber) is true AND Level N has questions.
     */
    fun isLevelUnlocked(
        levelNumber: Int,
        language: String,
        isUnlockedCheck: (Int) -> Boolean
    ): Boolean {
        val questions = getQuestionsForLevel(levelNumber, language)
        if (questions.isEmpty()) return false
        return if (levelNumber == 1) {
            true
        } else {
            isUnlockedCheck(levelNumber)
        }
    }

    /**
     * Checks if the next level exists and has questions.
     */
    fun hasNextLevel(currentLevel: Int, language: String): Boolean {
        val nextLevel = currentLevel + 1
        return nextLevel <= TOTAL_LEVELS && hasQuestionsForLevel(nextLevel, language)
    }

    /**
     * Returns metadata for all 10 reasoning levels.
     */
    fun getAllLevels(
        language: String,
        isUnlockedCheck: (Int) -> Boolean,
        isCompletedCheck: (Int) -> Boolean = { false }
    ): List<LevelInfo> {
        return (1..TOTAL_LEVELS).map { levelNum ->
            val questions = getQuestionsForLevel(levelNum, language)
            val unlocked = isLevelUnlocked(levelNum, language, isUnlockedCheck)
            val completed = isCompletedCheck(levelNum)
            LevelInfo(
                levelNumber = levelNum,
                title = "Level $levelNum",
                isUnlocked = unlocked,
                isCompleted = completed,
                questionsCount = questions.size,
                passConditionThreshold = PASS_MARK_THRESHOLD
            )
        }
    }
}
