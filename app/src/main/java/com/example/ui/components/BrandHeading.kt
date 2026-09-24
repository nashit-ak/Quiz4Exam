package com.example.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight

// Vibrant Golden Colors for "Quiz4Exam" brand styling
val GoldenFourColor = Color(0xFFFFD700)      // Pure radiant gold (for dark/blue backgrounds)
val GoldenFourDark = Color(0xFFF59E0B)       // Rich amber gold (for white/light backgrounds)

/**
 * Builds an AnnotatedString for any heading containing "Quiz4Exam",
 * rendering "Quiz" and "Exam" in [baseColor], and "4" in [goldColor].
 */
fun formatQuiz4ExamHeading(
    text: String,
    baseColor: Color,
    goldColor: Color = if (baseColor == Color.White) GoldenFourColor else GoldenFourDark,
    boldFour: Boolean = true
): AnnotatedString {
    val target = "Quiz4Exam"
    if (!text.contains(target)) {
        return buildAnnotatedString {
            pushStyle(SpanStyle(color = baseColor))
            append(text)
            pop()
        }
    }

    return buildAnnotatedString {
        var currentIndex = 0
        while (currentIndex < text.length) {
            val foundIndex = text.indexOf(target, currentIndex)
            if (foundIndex == -1) {
                pushStyle(SpanStyle(color = baseColor))
                append(text.substring(currentIndex))
                pop()
                break
            }
            if (foundIndex > currentIndex) {
                pushStyle(SpanStyle(color = baseColor))
                append(text.substring(currentIndex, foundIndex))
                pop()
            }

            // "Quiz" in baseColor
            pushStyle(SpanStyle(color = baseColor))
            append("Quiz")
            pop()

            // "4" in vibrant golden color
            pushStyle(
                SpanStyle(
                    color = goldColor,
                    fontWeight = if (boldFour) FontWeight.ExtraBold else FontWeight.Bold
                )
            )
            append("4")
            pop()

            // "Exam" in baseColor
            pushStyle(SpanStyle(color = baseColor))
            append("Exam")
            pop()

            currentIndex = foundIndex + target.length
        }
    }
}
