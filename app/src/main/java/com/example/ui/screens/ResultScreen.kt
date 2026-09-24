package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Question
import com.example.ui.components.CrackersCelebrationEffect
import com.example.ui.components.ExamTopAppBar
import com.example.ui.theme.CardBorder
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.OffWhiteBackground
import com.example.ui.theme.SurfacePureWhite
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.AppStrings
import com.example.util.SoundManager

@Composable
fun ResultScreen(
    currentLanguage: String,
    levelNumber: Int,
    score: Int,
    totalQuestions: Int = 10,
    pointsEarned: Int = score * 3,
    totalAccumulatedPoints: Int = 0,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    isNextLevelUnlocked: Boolean = false,
    isPassed: Boolean = false,
    hasNextLevel: Boolean = true,
    questions: List<Question> = emptyList(),
    userAnswers: Map<Int, Int> = emptyMap(),
    onNextLevel: () -> Unit,
    onRetry: () -> Unit,
    onOpenDetailedReview: () -> Unit,
    onBackToLevels: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Result Modal state: auto-displayed on quiz completion
    var showResultModal by remember { mutableStateOf(true) }

    LaunchedEffect(isPassed) {
        if (isPassed) {
            SoundManager.playCelebration()
        } else {
            SoundManager.playFailure()
        }
    }

    // ==========================================
    // RESULT MODAL WITH DARK SEMI-TRANSPARENT BACKDROP OVERLAY
    // ==========================================
    if (showResultModal) {
        Dialog(
            onDismissRequest = { showResultModal = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                dismissOnBackPress = true,
                dismissOnClickOutside = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.65f)) // Dark semi-transparent backdrop overlay
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                // Festive celebration effects on pass
                if (isPassed) {
                    CrackersCelebrationEffect(modifier = Modifier.fillMaxSize())
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .testTag("result_modal_card"),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                    border = BorderStroke(
                        width = 1.5.dp,
                        color = if (isPassed) Color(0xFFFFD700) else Color(0xFFFCA5A5)
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 26.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Badge Icon
                        if (isPassed) {
                            Box(
                                modifier = Modifier
                                    .size(74.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(Color(0xFFFFD700), Color(0xFFFF9100))
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.EmojiEvents,
                                    contentDescription = "Trophy",
                                    tint = Color.White,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(74.dp)
                                    .background(
                                        brush = Brush.linearGradient(
                                            listOf(Color(0xFFEF5350), Color(0xFFC62828))
                                        ),
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Failed",
                                    tint = Color.White,
                                    modifier = Modifier.size(42.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Title
                        Text(
                            text = if (isPassed) AppStrings.congratulationsTitle(currentLanguage) else AppStrings.levelIncompleteTitle(currentLanguage),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isPassed) TextPrimaryNavy else Color(0xFFC62828),
                                fontSize = 22.sp
                            ),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.testTag("result_modal_title")
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        // Category & Level tag
                        Text(
                            text = "${AppStrings.reasoningText(currentLanguage)} • Level $levelNumber",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary,
                                fontSize = 13.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        // Pass / Fail Status Badge: "Status: PASSED 🎉" or "Status: FAILED"
                        val statusText = if (isPassed) AppStrings.statusPassedLabel(currentLanguage) else AppStrings.statusFailedLabel(currentLanguage)
                        val statusBg = if (isPassed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2)
                        val statusFg = if (isPassed) Color(0xFF15803D) else Color(0xFFB91C1C)

                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = statusBg,
                            modifier = Modifier
                                .padding(vertical = 4.dp)
                                .testTag("result_modal_status_badge")
                        ) {
                            Text(
                                text = statusText,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = statusFg,
                                    fontSize = 14.sp
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        // Description text
                        val descText = if (isPassed) {
                            AppStrings.levelCompletedSuccessfully(currentLanguage, levelNumber)
                        } else {
                            AppStrings.levelIncompleteDesc(currentLanguage, score, totalQuestions, pointsEarned)
                        }
                        Text(
                            text = descText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextSecondaryMuted,
                                fontSize = 14.sp,
                                lineHeight = 20.sp
                            ),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Score & Points Badges Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            // Score Card
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = if (isPassed) Color(0xFFF0FDF4) else Color(0xFFFEF2F2),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, if (isPassed) Color(0xFF86EFAC) else Color(0xFFFECACA))
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "$score / $totalQuestions",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = if (isPassed) Color(0xFF15803D) else Color(0xFFB91C1C),
                                            fontSize = 18.sp
                                        )
                                    )
                                    Text(
                                        text = if (currentLanguage == "hi") "सही उत्तर" else "Correct",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = TextSecondaryMuted,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }

                            // Points Card
                            Surface(
                                modifier = Modifier.weight(1f),
                                color = Color(0xFFFFFBEB),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFFFDE68A))
                            ) {
                                Column(
                                    modifier = Modifier.padding(vertical = 10.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "+$pointsEarned",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFFB45309),
                                            fontSize = 18.sp
                                        )
                                    )
                                    Text(
                                        text = AppStrings.pointsEarnedLabel(currentLanguage),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = Color(0xFFB45309),
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Next Level Unlocked Banner (Only shown if Passed)
                        if (isPassed && isNextLevelUnlocked && hasNextLevel) {
                            Spacer(modifier = Modifier.height(14.dp))
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = Color(0xFFEEF4FF),
                                shape = RoundedCornerShape(12.dp),
                                border = BorderStroke(1.dp, Color(0xFFBFDBFE))
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LockOpen,
                                        contentDescription = "Unlocked",
                                        tint = NavyPrimary,
                                        modifier = Modifier.size(22.dp)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = AppStrings.levelUnlockedSuccess(currentLanguage, levelNumber + 1),
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = NavyPrimary,
                                            fontSize = 13.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        // ACTION BUTTONS:
                        // 1. Play Next Level (if passed and available)
                        if (isPassed && isNextLevelUnlocked && hasNextLevel) {
                            Button(
                                onClick = {
                                    SoundManager.playTap()
                                    showResultModal = false
                                    onNextLevel()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("result_dialog_next_level_button")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = AppStrings.playNextLevel(currentLanguage),
                                        style = MaterialTheme.typography.labelLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 15.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                        contentDescription = null,
                                        modifier = Modifier.size(17.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // If failed, encourage "Try Again" as the primary action
                        if (!isPassed) {
                            Button(
                                onClick = {
                                    SoundManager.playTap()
                                    showResultModal = false
                                    onRetry()
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                                shape = RoundedCornerShape(12.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp)
                                    .testTag("fail_try_again_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = AppStrings.retry(currentLanguage),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 15.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        // Detailed Review Button
                        OutlinedButton(
                            onClick = {
                                SoundManager.playTap()
                                showResultModal = false
                                onOpenDetailedReview()
                            },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp)
                                .testTag("result_dialog_review_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Visibility,
                                contentDescription = null,
                                tint = TextPrimaryNavy,
                                modifier = Modifier.size(17.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = AppStrings.reviewAnswers(currentLanguage),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryNavy,
                                    fontSize = 14.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Back to Levels Button
                        OutlinedButton(
                            onClick = {
                                SoundManager.playTap()
                                showResultModal = false
                                onBackToLevels()
                            },
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("result_dialog_back_button")
                        ) {
                            Text(
                                text = AppStrings.levelsText(currentLanguage),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = TextSecondaryMuted,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // Base Underlying Screen with Summary Card and Actions
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = "${AppStrings.reasoningText(currentLanguage)} - Level $levelNumber",
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackClick = onBackToLevels,
                onSettingsClick = onOpenSettings
            )
        },
        containerColor = OffWhiteBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Result Overview Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("result_card"),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${AppStrings.reasoningText(currentLanguage)} • Level $levelNumber",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary,
                            fontSize = 13.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = AppStrings.completedTitle(currentLanguage),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 22.sp
                        ),
                        modifier = Modifier.testTag("result_completed_title")
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = AppStrings.yourScore(currentLanguage, score, totalQuestions),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TextPrimaryNavy,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.testTag("result_score_display")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = AppStrings.pointsEarnedSummary(currentLanguage, pointsEarned),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A),
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.testTag("result_points_earned")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    val statusLabel = if (isPassed) AppStrings.statusPassedLabel(currentLanguage) else AppStrings.statusFailedLabel(currentLanguage)
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isPassed) Color(0xFFDCFCE7) else Color(0xFFFEE2E2),
                        modifier = Modifier.testTag("result_status_badge")
                    ) {
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isPassed) Color(0xFF15803D) else Color(0xFFB91C1C),
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Action buttons
                    Button(
                        onClick = {
                            SoundManager.playTap()
                            onOpenDetailedReview()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("result_detailed_review_btn"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.reviewAnswers(currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    if (isPassed && isNextLevelUnlocked && hasNextLevel) {
                        Button(
                            onClick = {
                                SoundManager.playTap()
                                onNextLevel()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("result_next_level_btn"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF16A34A))
                        ) {
                            Text(
                                text = AppStrings.playNextLevel(currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                    }

                    OutlinedButton(
                        onClick = {
                            SoundManager.playTap()
                            onRetry()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("result_retry_btn"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, NavyPrimary),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = NavyPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = NavyPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = AppStrings.retry(currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = NavyPrimary,
                                fontSize = 15.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = {
                            SoundManager.playTap()
                            onBackToLevels()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .testTag("result_back_levels_btn"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFCBD5E1))
                    ) {
                        Text(
                            text = AppStrings.levelsText(currentLanguage),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TextPrimaryNavy,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
