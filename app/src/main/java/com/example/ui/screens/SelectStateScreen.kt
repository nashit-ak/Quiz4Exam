package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.components.ExamTopAppBar
import com.example.ui.components.Glass3DButton
import com.example.ui.components.Glass3DCard
import com.example.ui.components.GlassPillBadge
import com.example.ui.components.Pressable3DButton
import com.example.ui.components.Pressable3DCard
import com.example.util.SoundManager

/**
 * Data representation of a state in the selection grid.
 */
data class StateCardItem(
    val id: String,
    val name: String,
    val hindiName: String,
    val isLocked: Boolean,
    val examCount: String? = null
)

/**
 * SelectStateScreen:
 * - Header: Title: "State Exams", Back button.
 * - 2-Column Grid Layout (numColumns=2).
 * - Jharkhand: Active Hero Card in Vibrant Royal Blue (#0066FF),
 *   white text, "AVAILABLE" badge, active arrow, and Jharkhand Vector Map watermark (opacity: 0.25).
 * - All other states (Bihar, UP, Rajasthan, MP, WB, Haryana, Punjab): Completely disabled and greyed out
 *   with soft muted slate grey background (#E5E7EB / #F3F4F6), muted dark grey text (#9CA3AF),
 *   subtle top-right lock icon, and disabled click.
 */
@Composable
fun SelectStateScreen(
    currentLanguage: String = "en",
    currentStreak: Int = 0,
    todayPoints: Int = 0,
    dailyTarget: Int = 15,
    onBack: () -> Unit,
    onOpenSettings: () -> Unit,
    onSelectState: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    // Required States: Jharkhand (strictly first and active), Bihar, Uttar Pradesh, Rajasthan, Madhya Pradesh, West Bengal, Haryana, Punjab.
    val stateList = remember {
        listOf(
            StateCardItem(
                id = "jharkhand",
                name = "Jharkhand",
                hindiName = "झारखंड",
                isLocked = false,
                examCount = "Active • 10+ Exams"
            ),
            StateCardItem(
                id = "bihar",
                name = "Bihar",
                hindiName = "बिहार",
                isLocked = true
            ),
            StateCardItem(
                id = "uttar_pradesh",
                name = "Uttar Pradesh",
                hindiName = "उत्तर प्रदेश",
                isLocked = true
            ),
            StateCardItem(
                id = "rajasthan",
                name = "Rajasthan",
                hindiName = "राजस्थान",
                isLocked = true
            ),
            StateCardItem(
                id = "madhya_pradesh",
                name = "Madhya Pradesh",
                hindiName = "मध्य प्रदेश",
                isLocked = true
            ),
            StateCardItem(
                id = "west_bengal",
                name = "West Bengal",
                hindiName = "पश्चिम बंगाल",
                isLocked = true
            ),
            StateCardItem(
                id = "haryana",
                name = "Haryana",
                hindiName = "हरियाणा",
                isLocked = true
            ),
            StateCardItem(
                id = "punjab",
                name = "Punjab",
                hindiName = "पंजाब",
                isLocked = true
            )
        )
    }

    val filteredList = remember(searchQuery, stateList) {
        if (searchQuery.isBlank()) {
            stateList
        } else {
            stateList.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                    it.hindiName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ExamTopAppBar(
                title = if (currentLanguage == "hi") "राज्य प्रतियोगी परीक्षा" else "State Exams",
                subtitle = if (currentLanguage == "hi") "अपना राज्य चुनें" else "Select Your Target State",
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackClick = {
                    SoundManager.playTap()
                    onBack()
                },
                onSettingsClick = {
                    SoundManager.playTap()
                    onOpenSettings()
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color(0xFFF8FAFC)
    ) { innerPadding ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
                bottom = 40.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("state_selection_grid")
        ) {
            // Header Section: Title & Search Filter
            item(span = { GridItemSpan(2) }) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = if (currentLanguage == "hi") "लक्ष्य राज्य चुनें" else "Choose Your State",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF0F172A),
                            fontSize = 22.sp,
                            letterSpacing = (-0.3).sp
                        ),
                        modifier = Modifier.testTag("select_state_heading")
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = if (currentLanguage == "hi")
                            "राज्य स्तरीय परीक्षाओं के लिए विशेष प्रश्न व टेस्ट सीरीज"
                        else
                            "State-wise specialized syllabus, previous papers & mock tests",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF64748B),
                            fontSize = 14.sp
                        ),
                        modifier = Modifier.testTag("select_state_subheading")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Search input
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("state_search_input"),
                        placeholder = {
                            Text(
                                text = if (currentLanguage == "hi") "राज्य खोजें..." else "Search state...",
                                color = Color(0xFF94A3B8),
                                fontSize = 14.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color(0xFF64748B)
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(14.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedBorderColor = Color(0xFF0066FF),
                            unfocusedBorderColor = Color(0xFFE2E8F0)
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            // 2-Column Grid Items
            items(filteredList, key = { it.id }) { stateItem ->
                if (!stateItem.isLocked) {
                    // Active Hero Card for Jharkhand
                    JharkhandActiveCard(
                        item = stateItem,
                        currentLanguage = currentLanguage,
                        onClick = {
                            SoundManager.playTap()
                            onSelectState(stateItem.name)
                        }
                    )
                } else {
                    // Completely Greyed-out Disabled Card
                    LockedStateCard(
                        item = stateItem,
                        currentLanguage = currentLanguage
                    )
                }
            }
        }
    }
}

/**
 * Jharkhand Active Hero Card:
 * - Background: Vibrant Royal Blue (#0066FF)
 * - Custom Vector Map Watermark with opacity: 0.18, positioned absolutely (right: -10dp, bottom: -10dp, width 90%, height 90%)
 * - "AVAILABLE" pill badge at top-left
 * - Active white arrow button at top-right
 * - State name text in crisp white font at bottom-left
 * - "Active • 10+ Exams" subtitle
 * - Clickable with elevation and smooth ripple
 */
@Composable
private fun JharkhandActiveCard(
    item: StateCardItem,
    currentLanguage: String,
    onClick: () -> Unit
) {
    // Deep Royal Gradient Glass (LinearGradient with ['#0062FF', '#0047BA'])
    val royalGlassGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF0062FF),
            Color(0xFF0047BA)
        )
    )

    Glass3DCard(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .testTag("state_card_${item.id}"),
        isSelected = true,
        enableSheenSweep = true,
        shape = RoundedCornerShape(18.dp),
        containerColor = Color(0xFF0062FF),
        backgroundBrush = royalGlassGradient,
        glassRimColor = Color.White.copy(alpha = 0.75f),
        neonGlowColor = Color(0xFF007BFF),
        ambientShadowColor = Color(0x3D0047BA),
        defaultElevation = 8.dp,
        selectedElevation = 10.dp,
        pressedElevation = 2.5.dp,
        pressedScale = 0.97f,
        pressedTranslationY = 2.5.dp
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Jharkhand State Map Graphic Vector Watermark:
            // Absolutely positioned on the right 55% width, centered, aspect ratio preserved via ContentScale.Fit
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.55f)
                    .align(Alignment.CenterEnd),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_jharkhand_watermark),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 4.dp)
                )
            }

            // Content Overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top Row: Translucent Glass Pill Badge at top-left, and Active White Glass Arrow at top-right
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Glass Pill Badge with translucent fill and crisp light reflection rim
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White.copy(alpha = 0.20f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.35f))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 9.dp, vertical = 4.5.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color(0xFF4ADE80), CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (currentLanguage == "hi") "उपलब्ध" else "AVAILABLE",
                                color = Color.White,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 10.sp,
                                letterSpacing = 0.6.sp
                            )
                        }
                    }

                    // 3D tactile glass circular arrow button at top-right
                    Glass3DButton(
                        onClick = onClick,
                        size = 32.dp,
                        buttonColor = Color.White.copy(alpha = 0.25f),
                        depthShadowColor = Color(0x33000000)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "Open Jharkhand Exams",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Bottom-Left Section: State Name Text in Crisp White
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = item.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            letterSpacing = (-0.2).sp
                        ),
                        maxLines = 1,
                        modifier = Modifier.testTag("state_name_${item.id}")
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = if (currentLanguage == "hi") "सक्रिय • 10+ परीक्षाएं" else (item.examCount ?: "Active • 10+ Exams"),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.92f),
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

/**
 * Locked State Card:
 * - Clearly visible solid physical container:
 *   - Background: #F3F4F6 (solid soft light-grey)
 *   - Border: 1.5dp solid #E5E7EB
 *   - Corner Radius: 18dp
 *   - Height: 128dp
 *   - Internal Padding: 14dp
 * - Card Content:
 *   - State Title: #374151 (bold, clear font)
 *   - Subtext: "Coming Soon" in #9CA3AF
 *   - Lock Icon: Top-right corner in #9CA3AF
 * - Disabled / Non-reactive: static container
 */
@Composable
private fun LockedStateCard(
    item: StateCardItem,
    currentLanguage: String
) {
    val cardBgColor = Color(0xFFF3F4F6)
    val cardBorderColor = Color(0xFFE5E7EB)
    val titleTextColor = Color(0xFF374151)
    val subtextColor = Color(0xFF9CA3AF)
    val lockIconColor = Color(0xFF9CA3AF)
    val lockCircleBg = Color(0xFFE5E7EB)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(128.dp)
            .clip(RoundedCornerShape(18.dp))
            .testTag("state_card_${item.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = cardBgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = BorderStroke(1.5.dp, cardBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(cardBgColor)
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top Row: Subtle Lock Icon on top-right
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .background(lockCircleBg, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = lockIconColor,
                        modifier = Modifier.size(15.dp)
                    )
                }
            }

            // Bottom-Left Section: State Name in #374151 and Subtext in #9CA3AF
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = titleTextColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        letterSpacing = (-0.2).sp
                    ),
                    maxLines = 1,
                    modifier = Modifier.testTag("state_name_${item.id}")
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = if (currentLanguage == "hi") "जल्द उपलब्ध होगा" else "Coming Soon",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = subtextColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}
