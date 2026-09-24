package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.SoundManager

/**
 * Top App Bar with full Safe Area Insets support.
 * Background color strictly set to #0066FF.
 * Features the official graduation cap logo on a #0066FF rounded square placed
 * directly to the left of the title for a cohesive brand identity globally.
 */
@Composable
fun ExamTopAppBar(
    title: String = "Quiz4Exam",
    subtitle: String? = null,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onBackClick: (() -> Unit)? = null,
    onSettingsClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    var showStreakDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color(0xFF0066FF), // Strictly #0066FF Vibrant Blue
        shadowElevation = 3.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.safeDrawing.only(
                        WindowInsetsSides.Horizontal + WindowInsetsSides.Top
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 58.dp)
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Optional Back Navigation Button
                if (onBackClick != null) {
                    IconButton(
                        onClick = {
                            SoundManager.playTap()
                            onBackClick()
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .testTag("top_bar_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                } else {
                    Spacer(modifier = Modifier.width(6.dp))
                }

                // Official App Logo: Unified Quiz4Exam brand logo
                AppLogo(
                    size = 32.dp,
                    elevation = 2.dp,
                    modifier = Modifier.testTag("top_bar_logo")
                )

                Spacer(modifier = Modifier.width(10.dp))

                // Screen Title and optional Subtitle: directly to the right of the logo
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 6.dp)
                ) {
                    Text(
                        text = formatQuiz4ExamHeading(
                            text = title,
                            baseColor = Color.White,
                            goldColor = GoldenFourColor
                        ),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = if (subtitle != null) 17.sp else 18.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.testTag("top_bar_title")
                    )
                    if (!subtitle.isNullOrBlank()) {
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.testTag("top_bar_subtitle")
                        )
                    }
                }

                // Dynamic Streak Widget: Golden/yellow rounded pill with fire icon "🔥 0 Days (0/15 pts)"
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFF0066FF),
                    border = BorderStroke(1.2.dp, Color(0xFFFFD700)), // Premium golden yellow border
                    shadowElevation = 2.dp,
                    modifier = Modifier
                        .padding(vertical = 4.dp, horizontal = 2.dp)
                        .clickable {
                            SoundManager.playTap()
                            showStreakDialog = true
                        }
                        .testTag("top_bar_streak_widget")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 9.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocalFireDepartment,
                            contentDescription = "Streak",
                            tint = Color(0xFFFFD700), // Vibrant gold flame icon
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = "$currentStreak ${if (currentStreak == 1) "Day" else "Days"} ($todayPoints/$dailyTarget pts)",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Settings Icon Button
                if (onSettingsClick != null) {
                    IconButton(
                        onClick = {
                            SoundManager.playTap()
                            onSettingsClick()
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("top_bar_settings_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }

    // Premium Streak Dashboard Modal
    if (showStreakDialog) {
        StreakDashboardModal(
            currentStreak = currentStreak,
            todayPoints = todayPoints,
            dailyTarget = dailyTarget,
            onDismiss = {
                SoundManager.playTap()
                showStreakDialog = false
            }
        )
    }
}
