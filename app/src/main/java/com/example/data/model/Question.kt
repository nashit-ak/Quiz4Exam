package com.example.data.model

/**
 * Generates a persistent, deterministic 10-digit numeric UID for a question if not explicitly provided.
 * Guaranteed format: 10-digit numeric string (1000000000..9999999999).
 */
fun generateDeterministic10DigitUid(id: Int, category: String, text: String): String {
    var hash = 1125899906842597L
    val cleanKey = "$category:$id:${text.trim().take(40)}"
    for (ch in cleanKey) {
        hash = 31L * hash + ch.code.toLong()
    }
    val positive = hash and 0x7FFFFFFFFFFFFFFFL
    val tenDigitNum = 1_000_000_000L + (positive % 9_000_000_000L)
    return tenDigitNum.toString()
}

/**
 * Data model for a single multiple-choice reasoning question.
 */
data class Question(
    val id: Int,
    val questionText: String,
    val options: List<String>,
    val correctOptionIndex: Int, // 0 for A, 1 for B, 2 for C, 3 for D
    val category: String = "Brain-Matrix",
    val explanation: String = "",
    val uid: String = ""
) {
    /**
     * Always returns a valid 10-digit numeric UID.
     * If [uid] is a 10-digit numeric string, returns it; otherwise generates a persistent deterministic 10-digit UID.
     */
    val effectiveUid: String
        get() = if (uid.isNotBlank() && uid.length == 10 && uid.all { it.isDigit() }) {
            uid
        } else {
            generateDeterministic10DigitUid(id, category, questionText)
        }

    val optionA: String get() = options.getOrElse(0) { "" }
    val optionB: String get() = options.getOrElse(1) { "" }
    val optionC: String get() = options.getOrElse(2) { "" }
    val optionD: String get() = options.getOrElse(3) { "" }

    fun getOptionLetter(index: Int): String {
        return when (index) {
            0 -> "A"
            1 -> "B"
            2 -> "C"
            3 -> "D"
            else -> ""
        }
    }

    fun getFormattedOption(index: Int?): String {
        if (index == null || index !in options.indices) return ""
        val letter = getOptionLetter(index)
        return "$letter. ${options[index]}"
    }

    fun getEffectiveExplanation(lang: String): String {
        if (explanation.isNotBlank()) return explanation
        val correctOpt = options.getOrElse(correctOptionIndex) { "" }
        val letter = getOptionLetter(correctOptionIndex)
        return if (lang == "hi") {
            "सही उत्तर विकल्प ($letter) $correctOpt है।"
        } else {
            "The correct answer is option ($letter) $correctOpt."
        }
    }
}
