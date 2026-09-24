package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Reusable 3D tactile pressable card with realistic spring scale physics,
 * elevation drop on press, dynamic layered shadows, and glowing selection border.
 */
@Composable
fun Pressable3DCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isSelected: Boolean = false,
    shape: Shape = RoundedCornerShape(16.dp),
    containerColor: Color = Color.White,
    backgroundBrush: Brush? = null,
    selectedBorderColor: Color = Color(0xFF0066FF),
    unselectedBorderColor: Color = Color(0xFFE2E8F0),
    selectedShadowColor: Color = Color(0x730066FF), // 45% royal blue glow
    unselectedShadowColor: Color = Color(0x24000000), // Soft depth
    defaultElevation: Dp = 6.dp,
    selectedElevation: Dp = 8.dp,
    pressedElevation: Dp = 2.dp,
    pressedScale: Float = 0.965f,
    pressedTranslationY: Dp = 2.5.dp,
    hasRipple: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable BoxScope.(isPressed: Boolean) -> Unit
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    val activePressed = isPressed && enabled

    // Spring scale physics: scales down smoothly to 0.965f on press, springs back with bounciness
    val scale by animateFloatAsState(
        targetValue = if (activePressed) pressedScale else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 350f
        ),
        label = "pressable_3d_scale"
    )

    // Vertical displacement simulating physical depression into the surface
    val translationY by animateDpAsState(
        targetValue = if (activePressed) pressedTranslationY else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 350f
        ),
        label = "pressable_3d_trans_y"
    )

    // Dynamic elevation: drops to 2dp when pressed, rises to 8dp when selected, 6dp resting
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
        label = "pressable_3d_elevation"
    )

    // Animated glowing border
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) selectedBorderColor else unselectedBorderColor,
        animationSpec = spring(stiffness = 300f),
        label = "pressable_3d_border_color"
    )
    val borderWidth by animateDpAsState(
        targetValue = if (isSelected) 2.dp else 1.dp,
        animationSpec = spring(stiffness = 300f),
        label = "pressable_3d_border_width"
    )

    // Animated shadow glow color
    val shadowColor by animateColorAsState(
        targetValue = if (isSelected) selectedShadowColor else unselectedShadowColor,
        animationSpec = spring(stiffness = 300f),
        label = "pressable_3d_shadow_color"
    )

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
        border = BorderStroke(borderWidth, borderColor),
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
        ) {
            content(activePressed)
        }
    }
}

/**
 * 3D tactile circular or pill button with depth bevel, spring compression, and layered shadow.
 */
@Composable
fun Pressable3DButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 36.dp,
    shape: Shape = CircleShape,
    buttonColor: Color = Color(0xFF0066FF),
    depthShadowColor: Color = Color(0x330066FF),
    content: @Composable () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val activePressed = isPressed && enabled

    val scale by animateFloatAsState(
        targetValue = if (activePressed) 0.90f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 400f
        ),
        label = "btn_scale"
    )

    val translationY by animateDpAsState(
        targetValue = if (activePressed) 2.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = 400f
        ),
        label = "btn_trans_y"
    )

    val elevation by animateDpAsState(
        targetValue = if (activePressed) 1.dp else 4.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = 400f
        ),
        label = "btn_elevation"
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
                        indication = ripple(color = Color.White.copy(alpha = 0.3f)),
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
