package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 3: Analogies, Number Series, and Direction Sense Questions.
 */
object Level3Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Doctor : Hospital :: Teacher : ?",
            options = listOf("School", "Office", "Field", "Court"),
            correctOptionIndex = 0,
            explanation = "A doctor works in a hospital, and a teacher works in a school."
        ),
        Question(
            id = 2,
            questionText = "Find the missing number in the series: 7, 14, 28, 56, ?",
            options = listOf("84", "96", "112", "120"),
            correctOptionIndex = 2,
            explanation = "Each term is multiplied by 2: 7×2=14, 14×2=28, 28×2=56, so 56×2=112."
        ),
        Question(
            id = 3,
            questionText = "Pen is to Write as Knife is to:",
            options = listOf("Eat", "Cut", "Sharp", "Steel"),
            correctOptionIndex = 1,
            explanation = "A pen is used for writing, and a knife is used for cutting."
        ),
        Question(
            id = 4,
            questionText = "If SOUTH-EAST becomes NORTH, then what will WEST become?",
            options = listOf("North-East", "South-East", "North-West", "South-West"),
            correctOptionIndex = 1,
            explanation = "Directions rotate 135° anti-clockwise (South-East to North). Rotating West by 135° anti-clockwise gives South-East."
        ),
        Question(
            id = 5,
            questionText = "Which one is different from the other three?",
            options = listOf("Copper", "Iron", "Gold", "Mercury"),
            correctOptionIndex = 3,
            explanation = "Mercury is the only metal that is liquid at room temperature; others are solid metals."
        ),
        Question(
            id = 6,
            questionText = "Find the next term in the series: Z, X, V, T, ?",
            options = listOf("R", "S", "Q", "P"),
            correctOptionIndex = 0,
            explanation = "Letters move backward by 2: Z(26) → X(24) → V(22) → T(20) → R(18)."
        ),
        Question(
            id = 7,
            questionText = "If 2 * 3 = 13 and 3 * 4 = 25, then 4 * 5 = ?",
            options = listOf("36", "41", "45", "50"),
            correctOptionIndex = 1,
            explanation = "Rule is a² + b²: 2² + 3² = 4 + 9 = 13; 3² + 4² = 9 + 16 = 25; 4² + 5² = 16 + 25 = 41."
        ),
        Question(
            id = 8,
            questionText = "Sunita walks 10 km towards South, turns left and walks 5 km. Which direction is she facing?",
            options = listOf("East", "West", "North", "South"),
            correctOptionIndex = 0,
            explanation = "Facing South, turning left makes one face towards East."
        ),
        Question(
            id = 9,
            questionText = "Complete the analogy: 8 : 64 :: 9 : ?",
            options = listOf("72", "81", "90", "99"),
            correctOptionIndex = 1,
            explanation = "Relationship is n : n²: 8² = 64, so 9² = 81."
        ),
        Question(
            id = 10,
            questionText = "Find the odd pair of numbers:",
            options = listOf("2 - 4", "3 - 9", "4 - 16", "5 - 30"),
            correctOptionIndex = 3,
            explanation = "In all pairs except the last, the second number is the square of the first: 2²=4, 3²=9, 4²=16, but 5²=25 (not 30)."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "चिकित्सक : अस्पताल :: शिक्षक : ?",
            options = listOf("विद्यालय", "कार्यालय", "मैदान", "न्यायालय"),
            correctOptionIndex = 0,
            explanation = "चिकित्सक का कार्यस्थल अस्पताल है, और शिक्षक का कार्यस्थल विद्यालय है।"
        ),
        Question(
            id = 2,
            questionText = "श्रृंखला में लुप्त संख्या ज्ञात कीजिए: 7, 14, 28, 56, ?",
            options = listOf("84", "96", "112", "120"),
            correctOptionIndex = 2,
            explanation = "प्रत्येक पद में 2 का गुणा है: 7×2=14, 14×2=28, 28×2=56, अतः 56×2=112।"
        ),
        Question(
            id = 3,
            questionText = "कलम का संबंध लिखने से है, तो चाकू का संबंध किससे है?",
            options = listOf("खाना", "काटना", "तेज", "इस्पात"),
            correctOptionIndex = 1,
            explanation = "कलम का उपयोग लिखने के लिए होता है, और चाकू का उपयोग काटने के लिए होता है।"
        ),
        Question(
            id = 4,
            questionText = "यदि दक्षिण-पूर्व उत्तर बन जाता है, तो पश्चिम क्या बन जाएगा?",
            options = listOf("उत्तर-पूर्व", "दक्षिण-पूर्व", "उत्तर-पश्चिम", "दक्षिण-पश्चिम"),
            correctOptionIndex = 1,
            explanation = "दिशाएँ वामावर्त 135° घूम रही हैं। पश्चिम को वामावर्त 135° घुमाने पर दक्षिण-पूर्व बनता है।"
        ),
        Question(
            id = 5,
            questionText = "इनमें से कौन सा अन्य तीनों से भिन्न है?",
            options = listOf("तांबा", "लोहा", "सोना", "पारा"),
            correctOptionIndex = 3,
            explanation = "पारा (मर्करी) कमरे के तापमान पर एकमात्र तरल धातु है, शेष सभी ठोस धातुएँ हैं।"
        ),
        Question(
            id = 6,
            questionText = "श्रृंखला का अगला पद क्या होगा: Z, X, V, T, ?",
            options = listOf("R", "S", "Q", "P"),
            correctOptionIndex = 0,
            explanation = "अक्षर 2 स्थान पीछे जा रहे हैं: Z(26) → X(24) → V(22) → T(20) → R(18)।"
        ),
        Question(
            id = 7,
            questionText = "यदि 2 * 3 = 13 और 3 * 4 = 25 हो, तो 4 * 5 का मान क्या होगा?",
            options = listOf("36", "41", "45", "50"),
            correctOptionIndex = 1,
            explanation = "नियम a² + b² है: 2² + 3² = 4 + 9 = 13; 4² + 5² = 16 + 25 = 41।"
        ),
        Question(
            id = 8,
            questionText = "सुनीता दक्षिण की ओर 10 किमी चलती है, फिर बाएं मुड़कर 5 किमी चलती है। अब उसका मुख किस दिशा में है?",
            options = listOf("पूर्व", "पश्चिम", "उत्तर", "दक्षिण"),
            correctOptionIndex = 0,
            explanation = "दक्षिण की ओर चलते समय बाएं मुड़ने पर मुख पूर्व दिशा की ओर हो जाता है।"
        ),
        Question(
            id = 9,
            questionText = "समानुपात पूरा कीजिए: 8 : 64 :: 9 : ?",
            options = listOf("72", "81", "90", "99"),
            correctOptionIndex = 1,
            explanation = "संबंध वर्ग संख्या का है: 8² = 64, अतः 9² = 81।"
        ),
        Question(
            id = 10,
            questionText = "विषम संख्या युग्म को पहचानिए:",
            options = listOf("2 - 4", "3 - 9", "4 - 16", "5 - 30"),
            correctOptionIndex = 3,
            explanation = "सभी युग्मों में दूसरी संख्या पहली का वर्ग है: 2²=4, 3²=9, 4²=16, परंतु 5 का वर्ग 25 होता है न कि 30।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
