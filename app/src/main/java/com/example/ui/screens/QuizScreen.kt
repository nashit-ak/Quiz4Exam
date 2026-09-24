package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.Question
import com.example.ui.components.ExamTopAppBar
import com.example.ui.theme.CardBorder
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.OffWhiteBackground
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SurfacePureWhite
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.AppStrings
import com.example.util.SoundManager

@Composable
fun QuizScreen(
    currentLanguage: String,
    levelNumber: Int,
    question: Question,
    currentIndex: Int,
    totalCount: Int,
    remainingSeconds: Int,
    selectedAnswerIndex: Int?,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onSelectAnswer: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onSkipQuestion: () -> Unit,
    onPreviousQuestion: () -> Unit = {},
    onBackToLevels: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val isLastQuestion = currentIndex == totalCount - 1
    val isFirstQuestion = currentIndex == 0

    // Offline Question Report System State
    var showReportDialog by remember { mutableStateOf(false) }
    var selectedReportOption by remember { mutableStateOf<String?>(null) }
    var reportCommentText by remember { mutableStateOf("") }

    val reportOptions = listOf(
        "Wrong Answer / Options",
        "Typographical / Spelling Error",
        "Incorrect Explanation",
        "Other"
    )

    // Offline Report Dialog
    if (showReportDialog) {
        val isOtherSelected = selectedReportOption == "Other"
        val canSubmit = when {
            selectedReportOption == null -> false
            isOtherSelected -> reportCommentText.trim().isNotEmpty()
            else -> true
        }

        AlertDialog(
            onDismissRequest = {
                showReportDialog = false
                selectedReportOption = null
                reportCommentText = ""
            },
            properties = DialogProperties(usePlatformDefaultWidth = false),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .testTag("report_question_dialog"),
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = null,
                        tint = Color(0xFFDC2626),
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = AppStrings.reportIssueTitle(currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary,
                            fontSize = 18.sp
                        )
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = if (currentLanguage == "hi") "समस्या का प्रकार चुनें:" else "Select the type of issue:",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondaryMuted,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    reportOptions.forEach { option ->
                        val isSelected = selectedReportOption == option
                        val displayOptionLabel = when (option) {
                            "Wrong Answer / Options" -> AppStrings.reportOptionWrongAnswer(currentLanguage)
                            "Typographical / Spelling Error" -> AppStrings.reportOptionTypo(currentLanguage)
                            "Incorrect Explanation" -> AppStrings.reportOptionIncorrectExplanation(currentLanguage)
                            "Other" -> AppStrings.reportOptionOther(currentLanguage)
                            else -> option
                        }

                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(10.dp))
                                .clickable {
                                    selectedReportOption = option
                                    if (option != "Other") {
                                        reportCommentText = ""
                                    }
                                },
                            color = if (isSelected) Color(0xFFEFF6FF) else Color(0xFFF8FAFC),
                            shape = RoundedCornerShape(10.dp),
                            border = BorderStroke(
                                1.dp,
                                if (isSelected) RoyalBlue else Color(0xFFE2E8F0)
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = {
                                        selectedReportOption = option
                                        if (option != "Other") {
                                            reportCommentText = ""
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = RoyalBlue,
                                        unselectedColor = Color(0xFF94A3B8)
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = displayOptionLabel,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = if (isSelected) NavyPrimary else TextPrimaryNavy,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }

                    // Conditional Textarea ONLY when "Other" is selected
                    if (isOtherSelected) {
                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedTextField(
                            value = reportCommentText,
                            onValueChange = { reportCommentText = it },
                            placeholder = {
                                Text(
                                    text = AppStrings.reportCommentPlaceholder(currentLanguage),
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF94A3B8))
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .testTag("report_comment_input"),
                            shape = RoundedCornerShape(10.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = RoyalBlue,
                                unfocusedBorderColor = Color(0xFFCBD5E1),
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),
                            maxLines = 4
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val issueType = selectedReportOption ?: "Other"
                        val userRemarks = if (issueType == "Other") {
                            reportCommentText.trim().ifEmpty { "N/A" }
                        } else {
                            "N/A"
                        }
                        val questionUid = question.effectiveUid
                        val categoryOrLevel = "${question.category} - Level $levelNumber"

                        val subject = "Question Report [UID: $questionUid]"
                        val body = """
----------------------------------------
QUESTION ISSUE REPORT
----------------------------------------
Question UID: $questionUid
Issue Type: $issueType
User Remarks: $userRemarks
Current Category / Level: $categoryOrLevel
----------------------------------------
(Sent from Quiz4Exam App)
                        """.trimIndent()

                        try {
                            val encodedSubject = Uri.encode(subject)
                            val encodedBody = Uri.encode(body)
                            val mailtoUri = Uri.parse("mailto:theofficialsmart01@gmail.com?subject=$encodedSubject&body=$encodedBody")
                            val emailIntent = Intent(Intent.ACTION_SENDTO, mailtoUri)
                            context.startActivity(emailIntent)
                        } catch (e: Exception) {
                            Toast.makeText(
                                context,
                                if (currentLanguage == "hi") "कोई ईमेल ऐप नहीं मिला" else "No email client found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // Close modal immediately without resetting quiz timer or answers
                        showReportDialog = false
                        selectedReportOption = null
                        reportCommentText = ""
                    },
                    enabled = canSubmit,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("report_submit_button")
                ) {
                    Text(
                        text = AppStrings.reportSend(currentLanguage),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showReportDialog = false
                        selectedReportOption = null
                        reportCommentText = ""
                    },
                    modifier = Modifier.testTag("report_cancel_button")
                ) {
                    Text(
                        text = AppStrings.reportCancel(currentLanguage),
                        color = TextSecondaryMuted,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            containerColor = SurfacePureWhite,
            shape = RoundedCornerShape(18.dp)
        )
    }

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
        bottomBar = {
            // Standard Bottom controls: "Skip" button (outline) and "Next →" button (solid primary, single arrow)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = SurfacePureWhite,
                shadowElevation = 8.dp,
                border = BorderStroke(1.dp, Color(0xFFE2E8F0))
            ) {
                val primaryButtonText = if (isLastQuestion) {
                    AppStrings.submit(currentLanguage)
                } else {
                    AppStrings.nextButtonLabel(currentLanguage) // "Next →" or "आगे →"
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Optional Previous button if past first question
                    if (!isFirstQuestion) {
                        OutlinedButton(
                            onClick = onPreviousQuestion,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .testTag("quiz_prev_button"),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFF334155)
                            )
                        ) {
                            Text(
                                text = AppStrings.prevButtonLabel(currentLanguage),
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                            )
                        }
                    }

                    // Skip button (outline)
                    OutlinedButton(
                        onClick = onSkipQuestion,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("quiz_skip_button"),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.5.dp, Color(0xFFCBD5E1)),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF334155)
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = AppStrings.skip(currentLanguage),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        )
                    }

                    // Next → button (solid primary, single arrow only)
                    Button(
                        onClick = onNextQuestion,
                        modifier = Modifier
                            .weight(1.3f)
                            .height(50.dp)
                            .testTag("quiz_next_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = primaryButtonText,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        },
        containerColor = OffWhiteBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            // Top Status Bar: Question Progress Badge + Continuous Countdown Timer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Progress Badge: "Question X/10"
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF1F5F9),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Text(
                        text = AppStrings.questionProgress(currentLanguage, currentIndex + 1, totalCount),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("quiz_progress_text")
                    )
                }

                // Countdown Timer Badge
                val minutes = remainingSeconds / 60
                val seconds = remainingSeconds % 60
                val timeFormatted = "%02d:%02d".format(minutes, seconds)
                val isUrgent = remainingSeconds <= 60

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isUrgent) Color(0xFFFEE2E2) else Color(0xFFEEF4FF),
                    border = BorderStroke(1.dp, if (isUrgent) Color(0xFFFCA5A5) else Color(0xFFBFDBFE)),
                    modifier = Modifier.testTag("quiz_timer_badge")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Timer,
                            contentDescription = "Timer",
                            tint = if (isUrgent) Color(0xFFDC2626) else RoyalBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${AppStrings.timeLeft(currentLanguage)}: $timeFormatted",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isUrgent) Color(0xFFDC2626) else RoyalBlue,
                                fontSize = 14.sp
                            ),
                            modifier = Modifier.testTag("quiz_timer_text")
                        )
                    }
                }
            }

            // Linear Progress Bar
            val rawProgress = if (totalCount > 0) (currentIndex + 1).toFloat() / totalCount.toFloat() else 0f
            val animatedProgress by animateFloatAsState(
                targetValue = rawProgress.coerceIn(0f, 1f),
                animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
                label = "quiz_progress_bar"
            )
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .testTag("quiz_linear_progress_bar"),
                color = RoyalBlue,
                trackColor = Color(0xFFE2E8F0),
                strokeCap = StrokeCap.Round
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Question Card with Clear Typography, subtle 10-digit UID, and Top-Right Report (🏳️) Icon
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("quiz_question_box"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    // Subtle Top Area: Question UID on left, Report Flag Icon on right
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "UID: ${question.effectiveUid}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color(0xFF94A3B8), // #94A3B8 clean low-contrast gray
                                fontSize = 11.5.sp,
                                fontWeight = FontWeight.Medium,
                                letterSpacing = 0.5.sp
                            ),
                            modifier = Modifier.testTag("quiz_question_uid")
                        )

                        // Report (🏳️) Icon button
                        IconButton(
                            onClick = {
                                SoundManager.playTap()
                                showReportDialog = true
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .background(Color(0xFFF8FAFC), RoundedCornerShape(8.dp))
                                .border(BorderStroke(1.dp, Color(0xFFE2E8F0)), RoundedCornerShape(8.dp))
                                .testTag("quiz_report_question_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Flag,
                                contentDescription = AppStrings.reportQuestion(currentLanguage),
                                tint = Color(0xFF94A3B8),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }

                    Text(
                        text = question.questionText,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 18.sp,
                            lineHeight = 26.sp
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Radio Option choices: generous vertical touch spacing (minimum 12px gap),
            // responsive tap targets (min 48dp), active border/background highlight when selected.
            // NO AUTO-ADVANCE on tap: selection only highlights; user must tap "Next →" to proceed.
            val optionLetters = listOf("A", "B", "C", "D")
            question.options.forEachIndexed { index, optionText ->
                val letter = optionLetters.getOrElse(index) { "" }
                val isSelected = selectedAnswerIndex == index
                val optionBg = if (isSelected) Color(0xFFEEF4FF) else SurfacePureWhite
                val optionBorder = if (isSelected) RoyalBlue else Color(0xFFE2E8F0)
                val borderWidth = if (isSelected) 2.dp else 1.dp
                val textColor = if (isSelected) NavyPrimary else TextPrimaryNavy

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp) // Minimum 12px gap
                        .clip(RoundedCornerShape(14.dp))
                        .clickable {
                            // Highlights selection; does NOT auto-advance
                            onSelectAnswer(index)
                        }
                        .testTag("quiz_option_$index"),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = optionBg),
                    border = BorderStroke(borderWidth, optionBorder),
                    elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 2.dp else 1.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 18.dp, vertical = 18.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "$letter. $optionText",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = textColor,
                                fontSize = 16.sp,
                                lineHeight = 22.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )

                        Spacer(modifier = Modifier.width(12.dp))

                        // Radio style selection indicator
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(if (isSelected) RoyalBlue else Color.Transparent)
                                .then(
                                    if (!isSelected) {
                                        Modifier.border(BorderStroke(1.5.dp, Color(0xFF94A3B8)), CircleShape)
                                    } else Modifier
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.White)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
