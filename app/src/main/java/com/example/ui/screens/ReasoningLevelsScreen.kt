package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.LevelInfo
import com.example.ui.components.ExamTopAppBar
import com.example.ui.components.Pressable3DButton
import com.example.ui.components.Pressable3DCard
import com.example.ui.theme.CardBorder
import com.example.ui.theme.NavyPrimary
import com.example.ui.theme.OffWhiteBackground
import com.example.ui.theme.RoyalBlue
import com.example.ui.theme.SurfacePureWhite
import com.example.ui.theme.TextPrimaryNavy
import com.example.ui.theme.TextSecondaryMuted
import com.example.util.AppStrings
import com.example.util.SoundManager
import kotlinx.coroutines.launch

@Composable
fun ReasoningLevelsScreen(
    currentLanguage: String,
    levels: List<LevelInfo>,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onSelectLevel: (Int) -> Unit,
    onBackToHome: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = AppStrings.reasoningTitle(currentLanguage),
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackClick = onBackToHome,
                onSettingsClick = onOpenSettings
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = OffWhiteBackground
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column {
                    Text(
                        text = AppStrings.reasoningTitle(currentLanguage),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 24.sp
                        ),
                        modifier = Modifier.testTag("reasoning_title")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = AppStrings.selectLevelSubtitle(currentLanguage),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondaryMuted,
                            fontSize = 14.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            items(levels, key = { it.levelNumber }) { level ->
                val isUnlocked = level.isUnlocked
                val isCompleted = level.isCompleted
                val levelLabel = if (currentLanguage == "hi") "स्तर ${level.levelNumber}" else "Level ${level.levelNumber}"

                // Level Selection UI states:
                // 1. Completed Level: "Level X" with subtle checkmark badge
                // 2. Unlocked Level: "Level X" with active play button
                // 3. Locked Level: "Level X" with clean lock icon (🔒), free from point requirement text.
                val cardBg = when {
                    isCompleted -> SurfacePureWhite
                    isUnlocked -> SurfacePureWhite
                    else -> Color(0xFFF8FAFC)
                }

                val borderColor = when {
                    isCompleted -> Color(0xFF86EFAC) // Subtle green border
                    isUnlocked -> Color(0xFFCBD5E1)  // Clean slate border
                    else -> Color(0xFFE2E8F0)        // Neutral muted
                }

                val titleColor = when {
                    isCompleted -> TextPrimaryNavy
                    isUnlocked -> TextPrimaryNavy
                    else -> Color(0xFF94A3B8)
                }

                if (isUnlocked) {
                    Pressable3DCard(
                        onClick = {
                            SoundManager.playTap()
                            onSelectLevel(level.levelNumber)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("level_button_${level.levelNumber}"),
                        isSelected = isCompleted,
                        shape = RoundedCornerShape(16.dp),
                        containerColor = cardBg,
                        selectedBorderColor = Color(0xFF22C55E),
                        unselectedBorderColor = borderColor,
                        selectedShadowColor = Color(0x4022C55E),
                        unselectedShadowColor = Color(0x1F000000),
                        defaultElevation = 4.dp,
                        selectedElevation = 6.dp,
                        pressedElevation = 1.5.dp,
                        pressedScale = 0.965f,
                        pressedTranslationY = 2.dp
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = levelLabel,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = titleColor,
                                        fontSize = 17.sp
                                    )
                                )

                                // Subtle Completed checkmark badge
                                if (isCompleted) {
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = Color(0xFFDCFCE7),
                                        border = BorderStroke(1.dp, Color(0xFF86EFAC))
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Completed",
                                                tint = Color(0xFF16A34A),
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Spacer(modifier = Modifier.width(3.dp))
                                            Text(
                                                text = if (currentLanguage == "hi") "पूर्ण" else "Completed",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    color = Color(0xFF15803D),
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 11.sp
                                                )
                                            )
                                        }
                                    }
                                }
                            }

                            // Right-side 3D indicator:
                            if (isCompleted) {
                                Pressable3DButton(
                                    onClick = {
                                        SoundManager.playTap()
                                        onSelectLevel(level.levelNumber)
                                    },
                                    size = 34.dp,
                                    buttonColor = Color(0xFF22C55E),
                                    depthShadowColor = Color(0x4016A34A)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Completed",
                                        tint = Color.White,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            } else {
                                Pressable3DButton(
                                    onClick = {
                                        SoundManager.playTap()
                                        onSelectLevel(level.levelNumber)
                                    },
                                    size = 34.dp,
                                    buttonColor = NavyPrimary,
                                    depthShadowColor = Color(0x400F172A)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "Play",
                                        tint = Color.White,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // Locked Card: Static and non-reactive, debossed inset style
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .clickable {
                                SoundManager.playTap()
                                scope.launch {
                                    val prevLevel = level.levelNumber - 1
                                    val msg = if (currentLanguage == "hi")
                                        "स्तर ${level.levelNumber} बंद है। इसे खोलने के लिए स्तर $prevLevel में कम से कम 5 उत्तर (5/10) सही दें।"
                                    else
                                        "Level ${level.levelNumber} is locked. Score at least 5 out of 10 in Level $prevLevel to unlock."
                                    snackbarHostState.showSnackbar(msg)
                                }
                            }
                            .testTag("level_button_${level.levelNumber}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = cardBg),
                        border = BorderStroke(1.2.dp, borderColor),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 18.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = levelLabel,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = titleColor,
                                    fontSize = 17.sp
                                )
                            )

                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(Color(0xFFE2E8F0), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = Color(0xFF94A3B8),
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
