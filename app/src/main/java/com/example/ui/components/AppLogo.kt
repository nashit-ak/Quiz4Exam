package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.R

/**
 * Reusable single official brand App Logo for Quiz4Exam featuring the unified
 * royal-blue squircle with the crisp white graduation cap matching the app icon everywhere.
 */
@Composable
fun AppLogo(
    modifier: Modifier = Modifier,
    size: Dp = 44.dp,
    elevation: Dp = 4.dp
) {
    val cornerRadius = size * 0.28f

    Box(
        modifier = modifier
            .size(size)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(cornerRadius),
                ambientColor = Color(0xFF0066FF).copy(alpha = 0.25f),
                spotColor = Color(0xFF0066FF).copy(alpha = 0.35f)
            )
            .clip(RoundedCornerShape(cornerRadius)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_quiz4exam_logo),
            contentDescription = "Quiz4Exam Official Logo",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(size)
        )
    }
}
