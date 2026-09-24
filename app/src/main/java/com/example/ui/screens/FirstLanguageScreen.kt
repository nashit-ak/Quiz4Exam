package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import com.example.ui.components.AppLogo
import com.example.ui.components.GoldenFourDark
import com.example.ui.components.formatQuiz4ExamHeading
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TextPrimaryNavy
import com.example.util.SoundManager

private const val TERMS_URL =
    "https://docs.google.com/document/d/1omZEC5mJLX0th8Jb0etljQ91G0zmrf39/edit?usp=drivesdk&ouid=115733834770864901467&rtpof=true&sd=true"

private const val PRIVACY_URL =
    "https://docs.google.com/document/d/1JwjDOi-BvLBeFODMWqQE_Zg618Aq7iL5/edit?usp=drivesdk&ouid=115733834770864901467&rtpof=true&sd=true"

// Exact Brand Colors
private val PrimaryBlue = Color(0xFF0066FF)
private val LightBlueTint = Color(0x0D0066FF) // rgba(0, 102, 255, 0.05)
private val SlateSubtext = Color(0xFF64748B)

@Composable
fun FirstLanguageScreen(
    onSelectLanguage: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    // Default selection is English ("en")
    var selectedLanguage by remember { mutableStateOf("en") }
    var isAgreed by remember { mutableStateOf(false) }

    val isHindi = selectedLanguage == "hi"

    val brandSubtitle = if (isHindi) "प्रतियोगी परीक्षाओं में सफलता की तैयारी" else "Master your competitive exams"
    val sectionTitle = if (isHindi) "अपनी पसंदीदा भाषा चुनें" else "Select Your Preferred Language"
    val helperText = if (isHindi) "आप इसे बाद में कभी भी सेटिंग्स में बदल सकते हैं" else "You can change this anytime later in Settings"
    val continueButtonText = if (isHindi) "आगे बढ़ें →" else "Continue →"

    // Full screen background with clean centered container
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAFC))
            .statusBarsPadding()
            .navigationBarsPadding(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 400.dp)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 1. Logo & Branding: Official Unified Quiz4Exam Logo
            AppLogo(
                size = 76.dp,
                elevation = 6.dp,
                modifier = Modifier.testTag("app_branding_logo")
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = formatQuiz4ExamHeading(
                    text = "Quiz4Exam",
                    baseColor = TextPrimaryNavy,
                    goldColor = GoldenFourDark
                ),
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    letterSpacing = (-0.5).sp
                )
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = brandSubtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SlateSubtext,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(28.dp))

            // 2. Section Header & Helper Text
            Text(
                text = sectionTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextPrimaryNavy,
                    fontSize = 17.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.testTag("first_welcome_title")
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = helperText,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SlateSubtext,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.testTag("first_welcome_subtitle")
            )

            Spacer(modifier = Modifier.height(22.dp))

            // 3. Language Options (Cards)
            // Card 1: ONLY displays "हिंदी" (no extra subtitles)
            LanguageSelectionCard(
                text = "हिंदी",
                isSelected = selectedLanguage == "hi",
                onClick = {
                    SoundManager.playTap()
                    selectedLanguage = "hi"
                },
                testTag = "select_first_language_hi"
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Card 2: ONLY displays "English" (no extra subtitles)
            LanguageSelectionCard(
                text = "English",
                isSelected = selectedLanguage == "en",
                onClick = {
                    SoundManager.playTap()
                    selectedLanguage = "en"
                },
                testTag = "select_first_language_en"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // 4. Mandatory Terms & Conditions Checkbox with clickable links
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isAgreed = !isAgreed }
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isAgreed,
                    onCheckedChange = { isAgreed = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = PrimaryBlue,
                        uncheckedColor = Color(0xFF94A3B8),
                        checkmarkColor = Color.White
                    ),
                    modifier = Modifier.testTag("terms_checkbox")
                )

                Spacer(modifier = Modifier.width(6.dp))

                val termsAnnotatedString = if (isHindi) {
                    buildAnnotatedString {
                        append("मैं ")
                        pushStringAnnotation(tag = "TERMS", annotation = TERMS_URL)
                        pushStyle(
                            SpanStyle(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append("नियम एवं शर्तें")
                        pop()
                        pop()

                        append(" और ")

                        pushStringAnnotation(tag = "PRIVACY", annotation = PRIVACY_URL)
                        pushStyle(
                            SpanStyle(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append("गोपनीयता नीति")
                        pop()
                        pop()

                        append(" से सहमत हूँ")
                    }
                } else {
                    buildAnnotatedString {
                        append("I agree to the ")
                        pushStringAnnotation(tag = "TERMS", annotation = TERMS_URL)
                        pushStyle(
                            SpanStyle(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append("Terms & Conditions")
                        pop()
                        pop()

                        append(" and ")

                        pushStringAnnotation(tag = "PRIVACY", annotation = PRIVACY_URL)
                        pushStyle(
                            SpanStyle(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold,
                                textDecoration = TextDecoration.Underline
                            )
                        )
                        append("Privacy Policy")
                        pop()
                        pop()
                    }
                }

                ClickableText(
                    text = termsAnnotatedString,
                    style = TextStyle(
                        fontSize = 12.5.sp,
                        color = Color(0xFF334155),
                        fontWeight = FontWeight.Normal,
                        lineHeight = 17.sp
                    ),
                    onClick = { offset ->
                        termsAnnotatedString.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                context.startActivity(intent)
                                return@ClickableText
                            }

                        termsAnnotatedString.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(annotation.item))
                                context.startActivity(intent)
                                return@ClickableText
                            }

                        // Tapping elsewhere on the text toggles checkbox
                        isAgreed = !isAgreed
                    }
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            // 5. Action Button ("Continue →")
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()
            val buttonScale by animateFloatAsState(
                targetValue = if (isPressed && isAgreed) 0.97f else 1.0f,
                animationSpec = spring(stiffness = 400f),
                label = "button_scale"
            )

            Button(
                onClick = {
                    if (isAgreed) {
                        SoundManager.playSuccess()
                        onSelectLanguage(selectedLanguage)
                    }
                },
                enabled = isAgreed,
                interactionSource = interactionSource,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .scale(buttonScale)
                    .alpha(if (isAgreed) 1.0f else 0.5f)
                    .shadow(
                        elevation = if (isAgreed) 6.dp else 0.dp,
                        shape = RoundedCornerShape(16.dp),
                        ambientColor = PrimaryBlue.copy(alpha = 0.35f),
                        spotColor = PrimaryBlue.copy(alpha = 0.35f)
                    )
                    .testTag("proceed_button"),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PrimaryBlue,
                    contentColor = Color.White,
                    disabledContainerColor = PrimaryBlue,
                    disabledContentColor = Color.White.copy(alpha = 0.7f)
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 2.dp,
                    pressedElevation = 6.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(
                    text = continueButtonText,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.5.sp,
                        letterSpacing = 0.3.sp
                    )
                )
            }
        }
    }
}

/**
 * Modern interactive language card:
 * - Completely removes any dark box shadow (shadowElevation = 0.dp)
 * - Selected state: Solid border 2px #0066FF, subtle background tint rgba(0, 102, 255, 0.05)
 * - Displays ONLY the specified single language name (no extra subtitles)
 */
@Composable
private fun LanguageSelectionCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1.0f,
        animationSpec = spring(stiffness = 500f),
        label = "card_scale"
    )

    val animatedBgColor by animateColorAsState(
        targetValue = if (isSelected) LightBlueTint else Color.White,
        animationSpec = tween(durationMillis = 180),
        label = "card_bg"
    )

    val animatedBorderColor by animateColorAsState(
        targetValue = if (isSelected) PrimaryBlue else Color(0xFFE2E8F0),
        animationSpec = tween(durationMillis = 180),
        label = "card_border"
    )

    Surface(
        onClick = onClick,
        interactionSource = interactionSource,
        shape = RoundedCornerShape(14.dp),
        color = animatedBgColor,
        border = BorderStroke(
            width = if (isSelected) 2.dp else 1.dp,
            color = animatedBorderColor
        ),
        shadowElevation = 0.dp, // STRICT FIX: completely remove dark gray-black box-shadow
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // ONLY the primary text ("हिंदी" or "English") — NO duplicate subtitles
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                    color = if (isSelected) PrimaryBlue else TextPrimaryNavy,
                    fontSize = 18.sp
                )
            )

            // Visual Selection State Indicator Badge
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) PrimaryBlue else Color.Transparent)
                    .then(
                        if (!isSelected) {
                            Modifier.background(
                                color = Color(0xFFF1F5F9),
                                shape = CircleShape
                            )
                        } else {
                            Modifier
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected",
                        tint = Color.White,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }
        }
    }
}
