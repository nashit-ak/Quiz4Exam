package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Next-Gen Futuristic Frosted Glassmorphism Card (Glass3DCard):
 * - Semi-translucent milky frosted glass container
 * - Hair-thin specular reflection glass rim
 * - Multi-stage diffuse ambient depth shadow
 * - Dynamic diagonal light sheen sweep (sheen sweep animation)
 * - Pulsing neon glow aura when selected
 * - 3D spring press physics (scale: 0.97f, elevation drop, translationY)
 */
@Composable
fun Glass3DCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    enableSheenSweep: Boolean = false,
    shape: Shape = RoundedCornerShape(22.dp),
    containerColor: Color = Color.White.copy(alpha = 0.88f),
    backgroundBrush: Brush? = null,
    glassRimColor: Color = Color.White.copy(alpha = 0.75f),
    neonGlowColor: Color = Color(0xFF0066FF),
    ambientShadowColor: Color = Color(0x290A2540),
    defaultElevation: Dp = 8.dp,
    selectedElevation: Dp = 10.dp,
    pressedElevation: Dp = 2.5.dp,
    pressedScale: Float = 0.97f,
    pressedTranslationY: Dp = 2.5.dp,
    hasRipple: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.(isPressed: Boolean) -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val activePressed = isPressed && enabled

    // Spring scale physics: 0.97f on press, springs back to 1.0f with bounciness
    val scale by animateFloatAsState(
        targetValue = if (activePressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 380f
        ),
        label = "glass_card_scale"
    )

    // Vertical displacement simulating physical depression into the surface
    val translationY by animateDpAsState(
        targetValue = if (activePressed) pressedTranslationY else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 380f
        ),
        label = "glass_card_trans_y"
    )

    // Dynamic elevation: drops to 2.5dp on press, rises to 10dp when selected, 8dp resting
    val elevation by animateDpAsState(
        targetValue = when {
            activePressed -> pressedElevation
            isSelected -> selectedElevation
            else -> defaultElevation
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 400f
        ),
        label = "glass_card_elevation"
    )

    // Neon Breathing Glow Animation (loops shadow opacity between 0.35 and 0.65 when selected)
    val infiniteTransition = rememberInfiniteTransition(label = "glass_card_infinite")
    val neonGlowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.38f,
        targetValue = 0.68f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "neon_glow_pulse"
    )

    // Animated light sheen sweep progress (0f to 1f continuously every 3.2 seconds)
    val sheenProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "sheen_sweep_progress"
    )

    // Animated rim border color: neon blue when active/selected, frosted specular white when resting
    val activeBorderColor by animateColorAsState(
        targetValue = if (isSelected) neonGlowColor else glassRimColor,
        animationSpec = spring(stiffness = 300f),
        label = "glass_border_color"
    )
    val activeBorderWidth by animateDpAsState(
        targetValue = if (isSelected) 2.dp else 1.5.dp,
        animationSpec = spring(stiffness = 300f),
        label = "glass_border_width"
    )

    // Shadow color: vivid neon glow aura when selected, deep multi-stage ambient shadow when resting
    val shadowColor = if (isSelected) {
        neonGlowColor.copy(alpha = neonGlowAlpha)
    } else {
        ambientShadowColor
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationY = translationY.toPx()
            }
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = shadowColor,
                spotColor = shadowColor
            )
            .clip(shape)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = if (hasRipple) ripple() else null,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            ),
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(activeBorderWidth, activeBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Box(
            modifier = Modifier
                .then(
                    if (backgroundBrush != null) {
                        Modifier.background(backgroundBrush)
                    } else {
                        Modifier
                    }
                )
                .then(
                    if (enableSheenSweep && enabled) {
                        Modifier.drawWithContent {
                            drawContent()

                            // Only render sheen during the active sweep window (0.0 to 0.45 of the cycle)
                            if (sheenProgress <= 0.45f) {
                                val normalizedProgress = sheenProgress / 0.45f
                                val sweepWidth = size.width * 0.40f
                                val totalDistance = size.width + size.height + sweepWidth * 2
                                val currentOffset = -sweepWidth + totalDistance * normalizedProgress

                                // Diagonal sweep at approximately -30 degrees
                                val startX = currentOffset - sweepWidth
                                val startY = 0f
                                val endX = currentOffset + sweepWidth
                                val endY = size.height

                                val sheenBrush = Brush.linearGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.White.copy(alpha = 0.42f),
                                        Color.Transparent
                                    ),
                                    start = Offset(startX, startY),
                                    end = Offset(endX, endY)
                                )

                                drawRect(brush = sheenBrush)
                            }
                        }
                    } else {
                        Modifier
                    }
                )
        ) {
            content(activePressed)
        }
    }
}

/**
 * Frosted Glass Pill Badge with translucent fill and crisp light reflection rim.
 */
@Composable
fun GlassPillBadge(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color(0xFF0066FF),
    containerColor: Color = Color.White.copy(alpha = 0.22f),
    borderColor: Color = Color.White.copy(alpha = 0.45f),
    leadingIcon: (@Composable () -> Unit)? = null
) {
    androidx.compose.material3.Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = BorderStroke(1.dp, borderColor)
    ) {
        androidx.compose.foundation.layout.Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        ) {
            if (leadingIcon != null) {
                leadingIcon()
                androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(5.dp))
            }
            androidx.compose.material3.Text(
                text = text,
                style = androidx.compose.material3.MaterialTheme.typography.labelSmall.copy(
                    color = textColor,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                    fontSize = 11.sp,
                    letterSpacing = 0.5.sp
                )
            )
        }
    }
}

/**
 * 3D Tactile Glass Circular/Pill Button with specular depth and spring compression.
 */
@Composable
fun Glass3DButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 38.dp,
    shape: Shape = CircleShape,
    buttonColor: Color = Color(0xFF0066FF),
    depthShadowColor: Color = Color(0x4D0066FF),
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val activePressed = isPressed && enabled

    val scale by animateFloatAsState(
        targetValue = if (activePressed) 0.88f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 400f
        ),
        label = "glass_btn_scale"
    )

    val translationY by animateDpAsState(
        targetValue = if (activePressed) 2.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 400f
        ),
        label = "glass_btn_trans_y"
    )

    val elevation by animateDpAsState(
        targetValue = if (activePressed) 1.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 400f
        ),
        label = "glass_btn_elevation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.translationY = translationY.toPx()
            }
            .shadow(
                elevation = elevation,
                shape = shape,
                ambientColor = depthShadowColor,
                spotColor = depthShadowColor
            )
            .clip(shape)
            .background(buttonColor)
            .then(
                if (enabled) {
                    Modifier.clickable(
                        interactionSource = interactionSource,
                        indication = ripple(color = Color.White.copy(alpha = 0.35f)),
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            ),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
