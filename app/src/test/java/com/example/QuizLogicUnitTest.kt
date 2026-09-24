package com.example

import com.example.data.levels.ReasoningLevelsRepository
import com.example.util.AppStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class QuizLogicUnitTest {

    @Test
    fun verify_all_ten_levels_exist_and_have_ten_questions_each() {
        assertEquals(10, ReasoningLevelsRepository.TOTAL_LEVELS)

        for (lvl in 1..10) {
            val enQuestions = ReasoningLevelsRepository.getQuestionsForLevel(lvl, "en")
            val hiQuestions = ReasoningLevelsRepository.getQuestionsForLevel(lvl, "hi")

            assertEquals("Level $lvl EN question count", 10, enQuestions.size)
            assertEquals("Level $lvl HI question count", 10, hiQuestions.size)

            enQuestions.forEachIndexed { idx, q ->
                assertTrue("Level $lvl EN Q$idx has options", q.options.size >= 4)
                assertTrue("Level $lvl EN Q$idx valid correct index", q.correctOptionIndex in q.options.indices)
                assertTrue("Level $lvl EN Q$idx non-empty text", q.questionText.isNotBlank())
                assertTrue("Level $lvl EN Q$idx non-empty explanation", q.explanation.isNotBlank())
            }

            hiQuestions.forEachIndexed { idx, q ->
                assertTrue("Level $lvl HI Q$idx has options", q.options.size >= 4)
                assertTrue("Level $lvl HI Q$idx valid correct index", q.correctOptionIndex in q.options.indices)
                assertTrue("Level $lvl HI Q$idx non-empty text", q.questionText.isNotBlank())
                assertTrue("Level $lvl HI Q$idx non-empty explanation", q.explanation.isNotBlank())
            }
        }
    }

    @Test
    fun verify_sequential_level_gating_logic() {
        // Level 1 is always unlocked
        val level1Unlocked = ReasoningLevelsRepository.isLevelUnlocked(1, "en") { false }
        assertTrue("Level 1 must be unlocked by default", level1Unlocked)

        // Level 2 should be locked if score is < 5
        val level2LockedWhenLowScore = ReasoningLevelsRepository.isLevelUnlocked(2, "en") { false }
        assertFalse("Level 2 must be locked when Level 1 has not passed", level2LockedWhenLowScore)

        // Level 2 unlocks if Level 1 passed (score >= 5)
        val level2UnlockedWhenPassed = ReasoningLevelsRepository.isLevelUnlocked(2, "en") { true }
        assertTrue("Level 2 must be unlocked when Level 1 passed", level2UnlockedWhenPassed)
    }

    @Test
    fun verify_points_awarded_per_correct_answer() {
        assertEquals(3, ReasoningLevelsRepository.POINTS_PER_CORRECT_ANSWER)
        val score = 7
        val points = score * ReasoningLevelsRepository.POINTS_PER_CORRECT_ANSWER
        assertEquals(21, points)
    }

    @Test
    fun verify_localization_strings_purity() {
        // Check that bilingual strings are strictly separated and no mixed slash strings exist
        val enButton = AppStrings.langButton("en")
        val hiButton = AppStrings.langButton("hi")

        assertEquals("Continue →", enButton)
        assertEquals("आगे बढ़ें →", hiButton)

        assertFalse("English button contains Hindi slash", enButton.contains("/"))
        assertFalse("Hindi button contains English slash", hiButton.contains("/"))

        val enHeading = AppStrings.langHeading("en")
        val hiHeading = AppStrings.langHeading("hi")

        assertEquals("Select Language", enHeading)
        assertEquals("भाषा चुनें", hiHeading)

        // Check review labels
        assertEquals("Your Answer", AppStrings.yourAnswerLabel("en"))
        assertEquals("आपका उत्तर", AppStrings.yourAnswerLabel("hi"))
        assertEquals("Correct Answer", AppStrings.correctAnswerLabel("en"))
        assertEquals("सही उत्तर", AppStrings.correctAnswerLabel("hi"))
        assertEquals("Explanation", AppStrings.explanationLabel("en"))
        assertEquals("व्याख्या", AppStrings.explanationLabel("hi"))
    }

    @Test
    fun verify_calicut_coding_decoding_question_answer_and_explanation() {
        val level5En = ReasoningLevelsRepository.getQuestionsForLevel(5, "en")
        val questionEn = level5En.firstOrNull { it.questionText.contains("CALICUT") }
        assertNotNull(questionEn)
        assertEquals(1, questionEn!!.correctOptionIndex)
        assertEquals("8251896", questionEn.options[questionEn.correctOptionIndex])
        assertEquals(
            "This is a direct letter substitution pattern. From DELHI, we get I = 1. From CALCUTTA, we get C = 8, A = 2, L = 5, U = 9, T = 6. Substituting these into CALICUT gives: C(8) A(2) L(5) I(1) C(8) U(9) T(6) = 8251896.",
            questionEn.explanation
        )

        val level5Hi = ReasoningLevelsRepository.getQuestionsForLevel(5, "hi")
        val questionHi = level5Hi.firstOrNull { it.questionText.contains("CALICUT") }
        assertNotNull(questionHi)
        assertEquals(1, questionHi!!.correctOptionIndex)
        assertEquals("8251896", questionHi.options[questionHi.correctOptionIndex])
        assertEquals("9482015739", questionEn.effectiveUid)
        assertEquals("9482015739", questionHi.effectiveUid)
    }

    @Test
    fun verify_all_questions_have_valid_10_digit_numeric_uid() {
        for (lvl in 1..10) {
            val enList = ReasoningLevelsRepository.getQuestionsForLevel(lvl, "en")
            val hiList = ReasoningLevelsRepository.getQuestionsForLevel(lvl, "hi")

            enList.forEach { q ->
                val uid = q.effectiveUid
                assertEquals("UID must be exactly 10 digits for EN Q${q.id} in level $lvl", 10, uid.length)
                assertTrue("UID must contain only digits: $uid", uid.all { it.isDigit() })
            }

            hiList.forEach { q ->
                val uid = q.effectiveUid
                assertEquals("UID must be exactly 10 digits for HI Q${q.id} in level $lvl", 10, uid.length)
                assertTrue("UID must contain only digits: $uid", uid.all { it.isDigit() })
            }
        }
    }

    @Test
    fun verify_streak_notification_manager_constants() {
        assertEquals("streak_reminder_channel", com.example.util.StreakNotificationManager.CHANNEL_ID)
        assertEquals(19, com.example.util.StreakNotificationManager.REMINDER_HOUR)
        assertEquals(0, com.example.util.StreakNotificationManager.REMINDER_MINUTE)
        assertEquals(15, com.example.util.StreakNotificationManager.DAILY_GOAL_POINTS)
    }
}
