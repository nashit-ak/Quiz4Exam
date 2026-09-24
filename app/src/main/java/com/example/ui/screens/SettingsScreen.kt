package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.ui.components.GoldenFourDark
import com.example.ui.components.formatQuiz4ExamHeading
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import com.example.ui.components.AppLogo
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
fun SettingsScreen(
    currentLanguage: String,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    soundEnabled: Boolean = true,
    onSoundToggle: (Boolean) -> Unit = {},
    onLanguageChange: (String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = AppStrings.settingsTitle(currentLanguage),
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackClick = onBack
            )
        },
        containerColor = OffWhiteBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {
            Text(
                text = AppStrings.settingsTitle(currentLanguage),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryNavy,
                    fontSize = 26.sp
                ),
                modifier = Modifier.testTag("settings_heading")
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Language Selection Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("language_setting_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = AppStrings.languageTitle(currentLanguage),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimaryNavy,
                            fontSize = 17.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    LanguageOptionButton(
                        label = "हिंदी",
                        isSelected = currentLanguage == "hi",
                        onClick = {
                            SoundManager.playTap()
                            onLanguageChange("hi")
                        },
                        testTag = "lang_btn_hi"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    LanguageOptionButton(
                        label = "English",
                        isSelected = currentLanguage == "en",
                        onClick = {
                            SoundManager.playTap()
                            onLanguageChange("en")
                        },
                        testTag = "lang_btn_en"
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Sound Effects Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("sound_setting_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .background(
                                    color = if (soundEnabled) Color(0xFFEFF6FF) else Color(0xFFF1F5F9),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (soundEnabled) Icons.Default.Notifications else Icons.Default.NotificationsOff,
                                contentDescription = if (soundEnabled) "Sound ON" else "Sound OFF",
                                tint = if (soundEnabled) NavyPrimary else TextSecondaryMuted,
                                modifier = Modifier.size(24.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = AppStrings.soundEffectsTitle(currentLanguage),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimaryNavy,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = if (soundEnabled) AppStrings.soundOn(currentLanguage) else AppStrings.soundOff(currentLanguage),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (soundEnabled) Color(0xFF16A34A) else TextSecondaryMuted,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }

                    Switch(
                        checked = soundEnabled,
                        onCheckedChange = { onSoundToggle(it) },
                        modifier = Modifier.testTag("sound_effects_toggle"),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = NavyPrimary,
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color(0xFFCBD5E1)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Rules & Scoring Info Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scoring_rules_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = NavyPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (currentLanguage == "hi") "क्विज़ नियम व स्कोरिंग" else "Quiz Rules & Scoring",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextPrimaryNavy,
                                fontSize = 16.sp
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = if (currentLanguage == "hi")
                            "• प्रत्येक सही उत्तर पर +3 अंक मिलते हैं।\n• दैनिक लक्ष्य: 15 अंक (कम से कम 5 सही उत्तर) प्रतिदिन।\n• दैनिक स्ट्राइक: 15 अंक पूर्ण करने पर स्ट्राइक बढ़ती है।\n• स्तर अनलॉक: स्तर N-1 में कम से कम 5/10 सही होने पर ही स्तर N खुलता है। (अंकों का स्तर खोलने से कोई संबंध नहीं है)।"
                        else
                            "• +3 Points awarded per correct answer.\n• Daily Goal: 15 points (at least 5 correct answers) per calendar day.\n• Daily Streak: Increments by 1 day when achieving 15 points in a day.\n• Pure Skill Progression: Level N unlocks ONLY if you score at least 5 out of 10 in Level (N-1). Points do not unlock levels.",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextSecondaryMuted,
                            lineHeight = 22.sp,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // About & Legal Card with Official App Logo
            val context = LocalContext.current
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("settings_about_legal_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = SurfacePureWhite),
                border = BorderStroke(1.dp, CardBorder),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AppLogo(size = 44.dp, elevation = 4.dp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column {
                            Text(
                                text = formatQuiz4ExamHeading(
                                    text = "Quiz4Exam",
                                    baseColor = TextPrimaryNavy,
                                    goldColor = GoldenFourDark
                                ),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                            Text(
                                text = AppStrings.brandSubtitle(currentLanguage),
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextSecondaryMuted,
                                    fontSize = 12.sp
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Terms & Conditions link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                try {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://docs.google.com/document/d/1omZEC5mJLX0th8Jb0etljQ91G0zmrf39/edit?usp=drivesdk&ouid=115733834770864901467&rtpof=true&sd=true")
                                    )
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not open browser", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = AppStrings.termsAndConditions(currentLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = NavyPrimary,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "↗",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Privacy Policy link
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                try {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://docs.google.com/document/d/1JwjDOi-BvLBeFODMWqQE_Zg618Aq7iL5/edit?usp=drivesdk&ouid=115733834770864901467&rtpof=true&sd=true")
                                    )
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    Toast.makeText(context, "Could not open browser", Toast.LENGTH_SHORT).show()
                                }
                            }
                            .padding(vertical = 8.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = AppStrings.privacyPolicy(currentLanguage),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = NavyPrimary,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline,
                                fontSize = 14.sp
                            )
                        )
                        Text(
                            text = "↗",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = NavyPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageOptionButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) Color(0xFFEEF4FF) else SurfacePureWhite
        ),
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.2.dp,
            color = if (isSelected) NavyPrimary else Color(0xFFCBD5E1)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 1.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (isSelected) NavyPrimary else TextPrimaryNavy,
                    fontSize = 16.sp
                )
            )

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(
                        color = if (isSelected) NavyPrimary else Color(0xFFF1F5F9),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}
