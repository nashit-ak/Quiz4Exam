package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Spa
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.R
import com.example.ui.components.ExamTopAppBar
import com.example.ui.components.Glass3DButton
import com.example.ui.components.Glass3DCard
import com.example.ui.components.GlassPillBadge
import com.example.ui.components.Pressable3DButton
import com.example.ui.components.Pressable3DCard
import com.example.ui.components.StreakDashboardModal
import com.example.util.AppStrings
import com.example.util.SoundManager
import com.example.util.StreakNotificationManager
import kotlinx.coroutines.launch

/**
 * Pixel-perfect Home Screen implementation matching the exact provided UI design:
 * - Greetings Header: "Welcome Back, Champion! 🚀" and "What would you like to learn today?"
 * - Card 1: Brain-Matrix (Glowing active card with 2dp royal blue border, glowing brain icon,
 *   "Let's Go!" blue pill, active circular forward button,
 *   meta tags: [📄 100 Questions] [📊 Mixed Difficulty] [⚡ Instant Results])
 * - Card 2: State Competitive Exam (India Map, "Soon" badge, disabled arrow,
 *   meta tags: [📖 State-wise Content] [📊 Bilingual Support] [🔒 Coming Soon])
 * - Card 3: Central Competitive Exam (Building silhouette, "Soon" badge, disabled arrow,
 *   meta tags: [📖 Multiple Subjects] [📊 Level-wise] [🔒 Coming Soon])
 * - Motivational Banner: Quote card with "Small steps every day lead to big results."
 *   and "Better You, Brighter Future" graphic badge.
 */
@Composable
fun HomeScreen(
    currentLanguage: String,
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onOpenSettings: () -> Unit,
    onSelectCentralExam: () -> Unit,
    onSelectStateExam: () -> Unit,
    onSelectReasoning: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showStreakProgressModal by remember { mutableStateOf(false) }
    var selectedCard by remember { mutableStateOf<String?>(null) }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            StreakNotificationManager.scheduleDailyStreakReminder(context)
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissionCheck = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                StreakNotificationManager.scheduleDailyStreakReminder(context)
            }
        } else {
            StreakNotificationManager.scheduleDailyStreakReminder(context)
        }
    }

    if (showStreakProgressModal) {
        StreakDashboardModal(
            currentStreak = currentStreak,
            todayPoints = todayPoints,
            dailyTarget = dailyTarget,
            onDismiss = { showStreakProgressModal = false }
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = AppStrings.appTitle(currentLanguage),
                subtitle = "Practice Today | Better Tomorrow",
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onSettingsClick = onOpenSettings
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFF4F7FB))
                    .navigationBarsPadding()
                    .padding(horizontal = 18.dp, vertical = 10.dp)
            ) {
                MotivationalQuoteBanner()
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF4F7FB) // Very clean soft grayish-blue background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
                .padding(top = 18.dp, bottom = 16.dp)
        ) {
            // Greetings Header
            Text(
                text = "Welcome Back, Champion! 🚀",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color(0xFF0D1B3E),
                    fontSize = 25.sp,
                    letterSpacing = (-0.5).sp
                ),
                modifier = Modifier.testTag("greetings_heading")
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "What would you like to learn today?",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF64748B),
                    fontSize = 15.5.sp,
                    fontWeight = FontWeight.Normal
                ),
                modifier = Modifier.testTag("greetings_subtitle")
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Card 1: Brain-Matrix (Hero Active Card with Futuristic Sheen Sweep & Pulsing Neon Glow)
            ExamCategoryCard(
                title = "Brain-Matrix",
                description = "100 Questions • Mixed Topics",
                iconPainter = painterResource(R.drawable.ic_quiz4exam_logo),
                iconBackgroundColor = Color.Transparent,
                iconSize = 64.dp,
                badgeText = "Let's Go!",
                badgeContainerColor = Color(0xFFE0EFFF),
                badgeTextColor = Color(0xFF0066FF),
                actionButtonColor = Color(0xFF0066FF),
                actionContentDescription = "Start Brain-Matrix Test",
                metaItem1 = MetaItem(Icons.Default.Description, "100 Questions"),
                metaItem2 = MetaItem(Icons.Default.BarChart, "Mixed Difficulty"),
                metaItem3 = MetaItem(Icons.Default.Bolt, "Instant Results"),
                isSelected = selectedCard == "brain_matrix",
                enableSheenSweep = true,
                onClick = {
                    selectedCard = "brain_matrix"
                    SoundManager.playTap()
                    onSelectReasoning()
                },
                testTag = "category_reasoning"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card 2: State Competitive Exam - Fully active and clickable
            ExamCategoryCard(
                title = "State Competitive Exam",
                description = "Prepare for State PSC, Police,\nTeaching, State Level Exams and more.",
                iconPainter = painterResource(R.drawable.ic_india_3d_states),
                iconBackgroundColor = Color(0xFFF3F4F6),
                iconSize = 52.dp,
                badgeText = "Explore",
                badgeContainerColor = Color(0xFFE0EFFF),
                badgeTextColor = Color(0xFF0066FF),
                actionButtonColor = Color(0xFF0066FF),
                actionContentDescription = "Explore State Competitive Exams",
                metaItem1 = MetaItem(Icons.AutoMirrored.Filled.MenuBook, "State-wise Content"),
                metaItem2 = MetaItem(Icons.Default.BarChart, "Bilingual Support"),
                metaItem3 = MetaItem(Icons.Default.Explore, "Explore Now"),
                isSelected = selectedCard == "state_exam",
                enableSheenSweep = selectedCard == "state_exam",
                onClick = {
                    selectedCard = "state_exam"
                    SoundManager.playTap()
                    onSelectStateExam()
                },
                testTag = "category_state_exam"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Card 3: Central Competitive Exam
            ExamCategoryCard(
                title = "Central Competitive Exam",
                description = "Prepare for UPSC, SSC, Banking,\nRailways, Defence and more.",
                iconPainter = painterResource(R.drawable.ic_central_exam_building),
                iconBackgroundColor = Color(0xFFF3F4F6),
                iconSize = 48.dp,
                badgeText = "Soon",
                badgeContainerColor = Color(0xFFF1F5F9),
                badgeTextColor = Color(0xFF64748B),
                actionButtonColor = Color(0xFFCBD5E1).copy(alpha = 0.6f),
                actionContentDescription = "Coming Soon",
                metaItem1 = MetaItem(Icons.AutoMirrored.Filled.MenuBook, "Multiple Subjects"),
                metaItem2 = MetaItem(Icons.Default.BarChart, "Level-wise"),
                metaItem3 = MetaItem(Icons.Default.Lock, "Coming Soon"),
                isSelected = selectedCard == "central_exam",
                enableSheenSweep = false,
                onClick = {
                    selectedCard = "central_exam"
                    SoundManager.playTap()
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Central Competitive Exam modules are coming soon!"
                        )
                    }
                    onSelectCentralExam()
                },
                testTag = "category_central_exam"
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

data class MetaItem(val icon: ImageVector, val label: String)

/**
 * Standard Category Card for Brain-Matrix, Central and State Exams:
 * - Pure white card, 16dp rounded corner, 3D elevation (6dp default, 8dp + glow when selected).
 * - Left rounded container with module icon.
 * - Standardized Title followed by Description (SemiBold 600, #374151, 13.5sp, 18sp line height).
 * - Right Badge pill and circular action button.
 * - Bottom row of inline [Icon + Label] elements.
 */
@Composable
private fun ExamCategoryCard(
    title: String,
    description: String,
    iconPainter: Painter,
    metaItem1: MetaItem,
    metaItem2: MetaItem,
    metaItem3: MetaItem,
    isSelected: Boolean = false,
    enableSheenSweep: Boolean = false,
    onClick: () -> Unit,
    testTag: String,
    iconBackgroundColor: Color = Color(0xFFF3F4F6),
    iconSize: Dp = 48.dp,
    badgeText: String = "Soon",
    badgeContainerColor: Color = Color(0xFFF1F5F9),
    badgeTextColor: Color = Color(0xFF64748B),
    actionButtonColor: Color = Color(0xFFCBD5E1).copy(alpha = 0.6f),
    actionContentDescription: String = "Coming Soon"
) {
    // Subtle frosted mesh/vertical glass gradient
    val glassCardGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFAFFFFFF),
            Color(0xF0F8FAFC)
        )
    )

    Glass3DCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag),
        isSelected = isSelected,
        enableSheenSweep = enableSheenSweep,
        shape = RoundedCornerShape(22.dp),
        containerColor = Color.White.copy(alpha = 0.88f),
        backgroundBrush = glassCardGradient,
        glassRimColor = Color.White.copy(alpha = 0.85f),
        neonGlowColor = Color(0xFF0066FF),
        ambientShadowColor = Color(0x290A2540),
        defaultElevation = 8.dp,
        selectedElevation = 10.dp,
        pressedElevation = 2.5.dp,
        pressedScale = 0.97f,
        pressedTranslationY = 2.5.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // Main Top/Center Section
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Left Icon & Titles
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = iconBackgroundColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clip(RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = iconPainter,
                            contentDescription = title,
                            modifier = Modifier.size(iconSize),
                            contentScale = ContentScale.Fit
                        )
                    }

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF0F172A),
                                fontSize = 17.sp,
                                letterSpacing = (-0.2).sp
                            )
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = Color(0xFF374151),
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.5.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Right Badge & Action
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    // Glass Pill Badge (Explore / Soon / Let's Go!)
                    GlassPillBadge(
                        text = badgeText,
                        textColor = badgeTextColor,
                        containerColor = badgeContainerColor,
                        borderColor = if (isSelected) Color(0xFF93C5FD) else Color.White.copy(alpha = 0.65f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // 3D tactile glass circular arrow button
                    Glass3DButton(
                        onClick = onClick,
                        size = 38.dp,
                        buttonColor = actionButtonColor,
                        depthShadowColor = if (isSelected) Color(0x660066FF) else Color(0x330A2540)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = actionContentDescription,
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Bottom Meta Row: Inline Icon + Label
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                InlineMetaTag(metaItem1.icon, metaItem1.label)
                InlineMetaTag(metaItem2.icon, metaItem2.label)
                InlineMetaTag(metaItem3.icon, metaItem3.label)
            }
        }
    }
}

/**
 * Inline Meta Tag matching the screenshot:
 * Icon directly beside the text (without boxed chip borders).
 */
@Composable
private fun InlineMetaTag(
    icon: ImageVector,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF475569),
            modifier = Modifier.size(15.dp)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = Color(0xFF334155),
                fontWeight = FontWeight.SemiBold,
                fontSize = 11.5.sp
            )
        )
    }
}

/**
 * Motivational Quote Banner matching the bottom card of the screenshot:
 * - Pale grayish-blue pill card.
 * - Left quotation marks “ with italic quote: "Small steps every day lead to big results."
 * - Thin line divider.
 * - Right leaves icon with "Better You / Brighter Future".
 */
@Composable
private fun MotivationalQuoteBanner() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("motivational_banner"),
        shape = RoundedCornerShape(18.dp),
        color = Color(0xFFEEF5FC),
        border = BorderStroke(1.dp, Color(0xFFE2EBF5))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Quote Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.FormatQuote,
                    contentDescription = "Quote",
                    tint = Color(0xFF7FA8D6),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column {
                    Text(
                        text = "Small steps every day",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF2B4764),
                            fontSize = 13.5.sp
                        )
                    )
                    Text(
                        text = "lead to big results.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Italic,
                            color = Color(0xFF2B4764),
                            fontSize = 13.5.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(10.dp))

            // Center subtle divider line
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .height(1.dp)
                    .background(Color(0xFFCBD5E1))
            )

            Spacer(modifier = Modifier.width(10.dp))

            // Right Leaves & Brand Motto
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Spa,
                    contentDescription = "Growth",
                    tint = Color(0xFF7FA8D6),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "Better You",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 12.sp
                        )
                    )
                    Text(
                        text = "Brighter Future",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Color(0xFF64748B),
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            lineHeight = 12.sp
                        )
                    )
                }
            }
        }
    }
}
