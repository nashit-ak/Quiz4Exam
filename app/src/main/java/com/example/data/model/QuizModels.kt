package com.example.data.model

/**
 * Representation of a Quiz Level metadata.
 */
data class LevelInfo(
    val levelNumber: Int,
    val title: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean = false,
    val questionsCount: Int = 10,
    val passConditionThreshold: Int = 5 // >= 5/10 needed to unlock next level
)

/**
 * Persisted progress for a level.
 */
data class LevelProgress(
    val levelNumber: Int,
    val isCompleted: Boolean = false,
    val highestScore: Int = 0,
    val lastAttemptScore: Int = 0,
    val hasAttempted: Boolean = false
)

/**
 * Daily target progress representation.
 */
data class DayTargetProgress(
    val dayName: String,     // e.g. "Mon", "Tue"
    val dateString: String,  // e.g. "2026-09-23"
    val dayOfMonth: Int,     // e.g. 23
    val pointsEarned: Int,
    val targetPoints: Int,
    val isToday: Boolean,
    val isAchieved: Boolean
)
