package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import java.util.Calendar

/**
 * Premium "Streak Dashboard" Modal Component.
 *
 * Features:
 * - Clean modal header with "Your Daily Streak" title and top-right close (X) button.
 * - Large visual highlight featuring "🔥 [X] Days" in bold typography and a radiant golden flame.
 * - Custom progress bar showing today's points (e.g. "12 / 15 points") filled with #0066FF.
 * - 7-Day Visual Tracker (M, T, W, T, F, S, S) highlighting completed days with #0066FF and checkmarks.
 * - Soft shadows, rounded corners, and clean white & #0066FF brand aesthetics.
 */
@Composable
fun StreakDashboardModal(
    currentStreak: Int,
    todayPoints: Int,
    dailyTarget: Int = 15,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.92f)
                .widthIn(max = 440.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(24.dp),
                    ambientColor = Color.Black.copy(alpha = 0.15f),
                    spotColor = Color.Black.copy(alpha = 0.25f)
                )
                .testTag("streak_info_dialog"),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            border = BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // 1. Header: "Your Daily Streak" with top-right Close (X) button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Your Daily Streak",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.testTag("streak_modal_title")
                    )

                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("streak_dashboard_close_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = TextSecondaryMuted,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 2. Large Highlight: Golden flame icon with "🔥 [X] Days"
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(
                            color = Color(0xFFFFFBEB), // Soft warm golden halo
                            shape = CircleShape
                        )
                        .border(
                            border = BorderStroke(2.dp, Color(0xFFFFD700)),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = "Streak Flame",
                        tint = Color(0xFFFFD700), // Rich Golden Yellow #FFD700
                        modifier = Modifier.size(46.dp)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "$currentStreak ${if (currentStreak == 1) "Day" else "Days"}",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = TextPrimaryNavy,
                        fontSize = 32.sp
                    ),
                    modifier = Modifier.testTag("streak_modal_count")
                )

                Text(
                    text = if (currentStreak > 0)
                        "Consistency is key! You are actively building mastery."
                    else
                        "Complete quizzes today to earn 15 points and ignite your streak!",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextSecondaryMuted,
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Progress Bar: Today's Points filled with #0066FF
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFF8FAFC),
                    border = BorderStroke(1.dp, Color(0xFFE2E8F0))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Today's Target",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryNavy,
                                    fontSize = 13.sp
                                )
                            )
                            Text(
                                text = "$todayPoints / $dailyTarget points",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = NavyPrimary, // #0066FF
                                    fontSize = 13.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        val progressRatio = (todayPoints.toFloat() / dailyTarget.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
                        LinearProgressIndicator(
                            progress = { progressRatio },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(4.dp)),
                            color = NavyPrimary, // Filled with primary #0066FF
                            trackColor = Color(0xFFE2E8F0),
                            strokeCap = StrokeCap.Round
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = if (todayPoints >= dailyTarget)
                                "🎯 Daily goal achieved! Streak safe for today."
                            else
                                "Earn ${(dailyTarget - todayPoints).coerceAtLeast(0)} more points today to preserve your streak.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (todayPoints >= dailyTarget) Color(0xFF16A34A) else TextSecondaryMuted,
                                fontSize = 11.5.sp,
                                fontWeight = if (todayPoints >= dailyTarget) FontWeight.SemiBold else FontWeight.Normal
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // 4. 7-Day Visual Tracker (M, T, W, T, F, S, S)
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "7-Day Streak Tracker",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 13.sp
                        ),
                        modifier = Modifier.padding(bottom = 10.dp)
                    )

                    val dayLabels = listOf("M", "T", "W", "T", "F", "S", "S")
                    // Compute current day of week: Calendar returns Sunday=1, Monday=2 ... Saturday=7
                    val calendar = Calendar.getInstance()
                    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                    // Convert to 0-indexed where Monday=0 ... Sunday=6
                    val currentDayIndex = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 2

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        dayLabels.forEachIndexed { index, dayLetter ->
                            // Determine status for this day
                            val isCompleted = when {
                                index < currentDayIndex -> (currentDayIndex - index) < currentStreak
                                index == currentDayIndex -> todayPoints >= dailyTarget
                                else -> false
                            }
                            val isToday = index == currentDayIndex

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = dayLetter,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = if (isToday) FontWeight.ExtraBold else FontWeight.Medium,
                                        color = if (isToday) NavyPrimary else TextSecondaryMuted,
                                        fontSize = 11.sp
                                    )
                                )

                                Spacer(modifier = Modifier.height(5.dp))

                                Box(
                                    modifier = Modifier
                                        .size(34.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isCompleted -> NavyPrimary // #0066FF for completed days
                                                isToday -> Color(0xFFEFF6FF) // Soft blue highlight for today
                                                else -> Color(0xFFF1F5F9) // Light gray for other days
                                            }
                                        )
                                        .border(
                                            border = BorderStroke(
                                                width = if (isToday) 1.5.dp else 1.dp,
                                                color = when {
                                                    isCompleted -> NavyPrimary
                                                    isToday -> NavyPrimary
                                                    else -> Color(0xFFE2E8F0)
                                                }
                                            ),
                                            shape = CircleShape
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    when {
                                        isCompleted -> {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Completed",
                                                tint = Color.White,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        isToday -> {
                                            Icon(
                                                imageVector = Icons.Default.LocalFireDepartment,
                                                contentDescription = "Today",
                                                tint = Color(0xFFFFD700), // Flame for today in progress
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                        else -> {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .background(Color(0xFFCBD5E1), CircleShape)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                // 5. Action Button: "Keep It Up!"
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("streak_dialog_dismiss_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = NavyPrimary // #0066FF
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = "Keep It Up!",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
}
