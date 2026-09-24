package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Question
import com.example.ui.components.ExamTopAppBar
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.OffWhiteBackground
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SurfacePureWhite
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.AppStrings

/**
 * Dedicated Detailed Review Screen.
 * Displays questions, user's answer vs correct answer, and explanations
 * with green accent for correct, red accent for wrong/unanswered.
 * Localized terms strictly applied:
 * English: "Your Answer", "Correct Answer", "Explanation"
 * Hindi: "आपका उत्तर", "सही उत्तर", "व्याख्या"
 */
@Composable
fun DetailedReviewScreen(
    currentLanguage: String,
    levelNumber: Int,
    questions: List<Question>,
    userAnswers: Map<Int, Int>,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onBackToResult: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var reportingQuestion by remember { mutableStateOf<Question?>(null) }
    var selectedReportOption by remember { mutableStateOf<String?>(null) }
    var reportCommentText by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = "${AppStrings.reasoningText(currentLanguage)} - Level $levelNumber",
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackClick = onBackToResult,
                onSettingsClick = onOpenSettings
            )
        },
        containerColor = OffWhiteBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 4.dp, height = 24.dp)
                            .background(NavyPrimary, RoundedCornerShape(2.dp))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "${AppStrings.reasoningText(currentLanguage)}: ${AppStrings.detailedReviewTitle(currentLanguage)}",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = NavyPrimary,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.testTag("review_header_title")
                    )
                }
            }

            itemsIndexed(questions, key = { index, _ -> index }) { index, q ->
                val userAnswerIndex = userAnswers[index]
                val isCorrect = userAnswerIndex == q.correctOptionIndex
                val isSkipped = userAnswerIndex == null

                val cardBorderColor = when {
                    isCorrect -> Color(0xFF86EFAC) // Green accent border
                    isSkipped -> Color(0xFFCBD5E1) // Slate border
                    else -> Color(0xFFFCA5A5)      // Red accent border
                }
                val badgeBgColor = when {
                    isCorrect -> Color(0xFFDCFCE7)
                    isSkipped -> Color(0xFFF1F5F9)
                    else -> Color(0xFFFEE2E2)
                }
                val badgeTextColor = when {
                    isCorrect -> Color(0xFF15803D)
                    isSkipped -> Color(0xFF475569)
                    else -> Color(0xFFB91C1C)
                }
                val statusBadgeText = when {
                    isCorrect -> AppStrings.badgeCorrect(currentLanguage)
                    isSkipped -> AppStrings.badgeSkipped(currentLanguage)
                    else -> AppStrings.badgeWrong(currentLanguage)
                }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("review_item_$index"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                    border = BorderStroke(1.5.dp, cardBorderColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        // Subtle UID Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "UID: ${q.effectiveUid}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color(0xFF94A3B8),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 0.5.sp
                                )
                            )
                        }

                        // Question Header & Badge
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            val qNumPrefix = if (currentLanguage == "hi") "प्रश्न ${index + 1}: " else "Q${index + 1}: "
                            Text(
                                text = "$qNumPrefix${q.questionText}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryNavy,
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp
                                ),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = badgeBgColor,
                                border = BorderStroke(1.dp, cardBorderColor)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    if (isCorrect) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            tint = badgeTextColor,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    } else if (!isSkipped) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = null,
                                            tint = badgeTextColor,
                                            modifier = Modifier.size(15.dp)
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                    Text(
                                        text = statusBadgeText,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = badgeTextColor,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Visual badge for "Your Answer" / "आपका उत्तर"
                        val userAnswerText = if (userAnswerIndex != null) {
                            val letter = q.getOptionLetter(userAnswerIndex)
                            val optText = q.options.getOrElse(userAnswerIndex) { "" }
                            "$letter. $optText"
                        } else {
                            AppStrings.skippedNotAttempted(currentLanguage)
                        }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = when {
                                isCorrect -> Color(0xFFF0FDF4)
                                isSkipped -> Color(0xFFF8FAFC)
                                else -> Color(0xFFFEF2F2)
                            },
                            border = BorderStroke(
                                1.dp,
                                when {
                                    isCorrect -> Color(0xFFBBF7D0)
                                    isSkipped -> Color(0xFFE2E8F0)
                                    else -> Color(0xFFFECACA)
                                }
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(
                                            color = when {
                                                isCorrect -> Color(0xFF22C55E)
                                                isSkipped -> Color(0xFF94A3B8)
                                                else -> Color(0xFFEF4444)
                                            },
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = when {
                                            isCorrect -> Icons.Default.Check
                                            isSkipped -> Icons.Default.HourglassEmpty
                                            else -> Icons.Default.Close
                                        },
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = AppStrings.yourAnswerLabel(currentLanguage),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = when {
                                                isCorrect -> Color(0xFF166534)
                                                isSkipped -> Color(0xFF64748B)
                                                else -> Color(0xFF991B1B)
                                            },
                                            fontSize = 11.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = userAnswerText,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = when {
                                                isCorrect -> Color(0xFF14532D)
                                                isSkipped -> Color(0xFF334155)
                                                else -> Color(0xFF7F1D1D)
                                            },
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Visual badge for "Correct Answer" / "सही उत्तर"
                        val correctLetter = q.getOptionLetter(q.correctOptionIndex)
                        val correctOptText = q.options.getOrElse(q.correctOptionIndex) { "" }

                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF0FDF4),
                            border = BorderStroke(1.dp, Color(0xFFBBF7D0))
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .background(color = Color(0xFF16A34A), shape = CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.width(10.dp))

                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = AppStrings.correctAnswerLabel(currentLanguage),
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF166534),
                                            fontSize = 11.sp
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = "$correctLetter. $correctOptText",
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = Color(0xFF14532D),
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }

                        // Explanation container styled with a soft gray/light primary background:
                        // "Explanation" / "व्याख्या" with compact Report / Flag button
                        val explanationText = q.getEffectiveExplanation(currentLanguage)
                        Spacer(modifier = Modifier.height(12.dp))
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFFF1F5F9), // Soft gray / light primary tint
                            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(width = 3.dp, height = 14.dp)
                                                .background(NavyPrimary, RoundedCornerShape(1.dp))
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = AppStrings.explanationLabel(currentLanguage),
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontWeight = FontWeight.Bold,
                                                color = NavyPrimary,
                                                fontSize = 13.sp
                                            )
                                        )
                                    }

                                    // Compact Report / Flag button in the top corner of the Explanation box
                                    Surface(
                                        onClick = {
                                            reportingQuestion = q
                                            selectedReportOption = null
                                            reportCommentText = ""
                                        },
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color.White,
                                        border = BorderStroke(1.dp, Color(0xFFCBD5E1)),
                                        modifier = Modifier.testTag("review_report_button_${q.id}")
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Flag,
                                                contentDescription = AppStrings.reportQuestion(currentLanguage),
                                                tint = Color(0xFF64748B),
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(
                                                text = AppStrings.reportIssueBtn(currentLanguage),
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Color(0xFF475569),
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.SemiBold
                                                )
                                            )
                                        }
                                    }
                                }

                                if (explanationText.isNotBlank()) {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = explanationText,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = Color(0xFF334155),
                                            fontSize = 13.sp,
                                            lineHeight = 20.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // Report Question Dialog (Modal)
    val questionToReport = reportingQuestion
    if (questionToReport != null) {
        val reportOptions = listOf(
            "Wrong Answer / Options",
            "Typographical / Spelling Error",
            "Incorrect Explanation",
            "Other"
        )
        val isOtherSelected = selectedReportOption == "Other"
        val canSubmit = selectedReportOption != null && (!isOtherSelected || reportCommentText.trim().isNotEmpty())

        AlertDialog(
            onDismissRequest = {
                reportingQuestion = null
                selectedReportOption = null
                reportCommentText = ""
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = null,
                        tint = NavyPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = AppStrings.reportIssueTitle(currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 17.sp
                        )
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "UID: ${questionToReport.effectiveUid}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF64748B),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium
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
                                .testTag("review_report_comment_input"),
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
                        val questionUid = questionToReport.effectiveUid
                        val categoryOrLevel = "${questionToReport.category} - Level $levelNumber"

                        val subject = "Question Report [UID: $questionUid]"
                        val body = """
----------------------------------------
QUESTION ISSUE REPORT (FROM REVIEW SCREEN)
----------------------------------------
Question UID: $questionUid
Issue Type: $issueType
User Remarks: $userRemarks
Quiz Category / Level: $categoryOrLevel
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

                        // Close modal immediately and return to review list at current scroll position
                        reportingQuestion = null
                        selectedReportOption = null
                        reportCommentText = ""
                    },
                    enabled = canSubmit,
                    colors = ButtonDefaults.buttonColors(containerColor = NavyPrimary),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("review_report_submit_button")
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
                        reportingQuestion = null
                        selectedReportOption = null
                        reportCommentText = ""
                    },
                    modifier = Modifier.testTag("review_report_cancel_button")
                ) {
                    Text(
                        text = AppStrings.reportCancel(currentLanguage),
                        color = Color(0xFF64748B),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            },
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        )
    }
}
