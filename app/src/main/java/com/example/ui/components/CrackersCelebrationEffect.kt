package com.example.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

private data class CrackerSpark(
    val burstIndex: Int,
    val angle: Float,
    val speed: Float,
    val color: Color,
    val radius: Float
)

private data class ConfettiPiece(
    val xRatio: Float,
    val speed: Float,
    val size: Float,
    val color: Color,
    val isCircle: Boolean,
    val initialPhase: Float
)

private val CelebrationPalette = listOf(
    Color(0xFFFFD700), // Gold
    Color(0xFFFF3366), // Crimson Coral
    Color(0xFF00E5FF), // Bright Cyan
    Color(0xFF00E676), // Emerald Green
    Color(0xFFFF9100), // Vibrant Amber
    Color(0xFFD500F9), // Purple Neon
    Color(0xFFFFFFFF), // Sparkle White
    Color(0xFFFF5252)  // Vermillion
)

/**
 * Animated festive fireworks/crackers & confetti burst celebration canvas.
 */
@Composable
fun CrackersCelebrationEffect(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "crackers_transition")

    val animationTime by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "crackers_time"
    )

    val sparks = remember {
        val list = mutableListOf<CrackerSpark>()
        val rnd = Random(42)
        for (burst in 0..3) {
            val sparkCount = 28
            for (i in 0 until sparkCount) {
                val angle = (rnd.nextFloat() * 2f * PI).toFloat()
                val speed = 80f + rnd.nextFloat() * 180f
                val color = CelebrationPalette[rnd.nextInt(CelebrationPalette.size)]
                val radius = 3.5f + rnd.nextFloat() * 4.5f
                list.add(CrackerSpark(burst, angle, speed, color, radius))
            }
        }
        list
    }

    val confetti = remember {
        val list = mutableListOf<ConfettiPiece>()
        val rnd = Random(101)
        for (i in 0..45) {
            list.add(
                ConfettiPiece(
                    xRatio = rnd.nextFloat(),
                    speed = 0.7f + rnd.nextFloat() * 0.8f,
                    size = 7f + rnd.nextFloat() * 7f,
                    color = CelebrationPalette[rnd.nextInt(CelebrationPalette.size)],
                    isCircle = rnd.nextBoolean(),
                    initialPhase = rnd.nextFloat()
                )
            )
        }
        list
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val burstCenters = listOf(
            Offset(canvasWidth * 0.22f, canvasHeight * 0.25f),
            Offset(canvasWidth * 0.78f, canvasHeight * 0.28f),
            Offset(canvasWidth * 0.32f, canvasHeight * 0.58f),
            Offset(canvasWidth * 0.70f, canvasHeight * 0.65f)
        )

        for (spark in sparks) {
            val center = burstCenters[spark.burstIndex % burstCenters.size]
            val burstPhaseOffset = (spark.burstIndex * 0.25f)
            val rawPhase = (animationTime + burstPhaseOffset) % 1f
            val progress = rawPhase
            val distance = spark.speed * progress * 1.5f
            val alpha = (1f - progress).coerceIn(0f, 1f)

            if (alpha > 0.05f) {
                val sparkX = center.x + cos(spark.angle) * distance
                val sparkY = center.y + sin(spark.angle) * distance + (progress * progress * 40f)

                drawCircle(
                    color = spark.color.copy(alpha = alpha * 0.35f),
                    radius = spark.radius * 2.2f,
                    center = Offset(sparkX, sparkY)
                )
                drawCircle(
                    color = spark.color.copy(alpha = alpha),
                    radius = spark.radius * (1f - progress * 0.3f),
                    center = Offset(sparkX, sparkY)
                )
            }
        }

        for (piece in confetti) {
            val progress = (animationTime * piece.speed + piece.initialPhase) % 1f
            val x = piece.xRatio * canvasWidth + sin(progress * 2f * PI.toFloat() * 2f) * 20f
            val y = progress * (canvasHeight + 40f) - 20f
            val rotation = progress * 360f * 2f

            if (piece.isCircle) {
                drawCircle(
                    color = piece.color.copy(alpha = 0.85f),
                    radius = piece.size / 2f,
                    center = Offset(x, y)
                )
            } else {
                rotate(degrees = rotation, pivot = Offset(x, y)) {
                    drawRect(
                        color = piece.color.copy(alpha = 0.85f),
                        topLeft = Offset(x - piece.size / 2f, y - piece.size / 3f),
                        size = Size(piece.size, piece.size * 0.65f)
                    )
                }
            }
        }
    }
}
