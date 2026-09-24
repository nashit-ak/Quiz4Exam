package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 2: Competitive Exam Reasoning Questions in English and Hindi.
 */
object Level2Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Find the next number: 5, 10, 20, 40, ?",
            options = listOf("60", "70", "80", "90"),
            correctOptionIndex = 2,
            explanation = "Each number is multiplied by 2: 5×2=10, 10×2=20, 20×2=40, so 40×2=80."
        ),
        Question(
            id = 2,
            questionText = "If BOOK is coded as CPPL, how is PEN coded?",
            options = listOf("QFO", "QEN", "PFN", "QFM"),
            correctOptionIndex = 0,
            explanation = "Each letter is shifted forward by +1: P+1=Q, E+1=F, N+1=O, resulting in QFO."
        ),
        Question(
            id = 3,
            questionText = "A person faces North. He turns right, then right again. Which direction is he facing?",
            options = listOf("North", "South", "East", "West"),
            correctOptionIndex = 1,
            explanation = "Starting North, a first right turn faces East, and a second right turn faces South."
        ),
        Question(
            id = 4,
            questionText = "Find the odd one out.",
            options = listOf("12", "18", "24", "31"),
            correctOptionIndex = 3,
            explanation = "12, 18, and 24 are composite even numbers divisible by 6, whereas 31 is a prime number."
        ),
        Question(
            id = 5,
            questionText = "If all roses are flowers and some flowers are red, which statement is definitely true?",
            options = listOf("All roses are red", "All red things are roses", "All roses are flowers", "No rose is red"),
            correctOptionIndex = 2,
            explanation = "The premise directly states 'all roses are flowers', so that statement is definitely true."
        ),
        Question(
            id = 6,
            questionText = "Complete the series: A, C, F, J, ?",
            options = listOf("M", "N", "O", "P"),
            correctOptionIndex = 2,
            explanation = "Difference between letter positions increases by 1: A(+2)→C(+3)→F(+4)→J(+5)→O (position 15)."
        ),
        Question(
            id = 7,
            questionText = "If 3² + 4² = 25 and 5² + 2² = 29, then 4² + 3² = ?",
            options = listOf("25", "26", "27", "28"),
            correctOptionIndex = 0,
            explanation = "The formula is a² + b²: 4² + 3² = 16 + 9 = 25."
        ),
        Question(
            id = 8,
            questionText = "Ravi is 7th from the left and 9th from the right in a row. How many people are there?",
            options = listOf("15", "16", "17", "18"),
            correctOptionIndex = 0,
            explanation = "Total persons = (Left position + Right position) - 1 = (7 + 9) - 1 = 15."
        ),
        Question(
            id = 9,
            questionText = "Which number replaces ?: 4, 9, 19, 39, ?",
            options = listOf("69", "79", "89", "99"),
            correctOptionIndex = 1,
            explanation = "Pattern is (number × 2) + 1: 4×2+1=9, 9×2+1=19, 19×2+1=39, 39×2+1=79."
        ),
        Question(
            id = 10,
            questionText = "P is brother of Q. Q is sister of R. How is P related to R?",
            options = listOf("Brother", "Sister", "Father", "Uncle"),
            correctOptionIndex = 0,
            explanation = "P, Q, and R are siblings. Since P is male, P is the brother of R."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "अगली संख्या ज्ञात कीजिए: 5, 10, 20, 40, ?",
            options = listOf("60", "70", "80", "90"),
            correctOptionIndex = 2,
            explanation = "प्रत्येक संख्या में 2 का गुणा है: 5×2=10, 10×2=20, 20×2=40, अतः 40×2=80।"
        ),
        Question(
            id = 2,
            questionText = "यदि BOOK को CPPL लिखा जाता है, तो PEN को क्या लिखा जाएगा?",
            options = listOf("QFO", "QEN", "PFN", "QFM"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक वर्ण में +1 की वृद्धि है: P+1=Q, E+1=F, N+1=O, जिससे QFO बनता है।"
        ),
        Question(
            id = 3,
            questionText = "एक व्यक्ति उत्तर की ओर देख रहा है। वह दाईं ओर मुड़ता है, फिर दोबारा दाईं ओर मुड़ता है। अब उसका मुख किस दिशा में है?",
            options = listOf("उत्तर", "दक्षिण", "पूर्व", "पश्चिम"),
            correctOptionIndex = 1,
            explanation = "उत्तर से एक बार दाएं मुड़ने पर पूर्व और फिर दाएं मुड़ने पर दक्षिण दिशा होती है।"
        ),
        Question(
            id = 4,
            questionText = "इनमें से विषम संख्या कौन सी है?",
            options = listOf("12", "18", "24", "31"),
            correctOptionIndex = 3,
            explanation = "12, 18 और 24 सम संख्याएँ हैं, जबकि 31 एक अभाज्य (प्राइम) संख्या है।"
        ),
        Question(
            id = 5,
            questionText = "यदि सभी गुलाब फूल हैं और कुछ फूल लाल हैं, तो कौन सा कथन निश्चित रूप से सत्य है?",
            options = listOf("सभी गुलाब लाल हैं", "सभी लाल वस्तुएँ गुलाब हैं", "सभी गुलाब फूल हैं", "कोई गुलाब लाल नहीं है"),
            correctOptionIndex = 2,
            explanation = "कथन में सीधे दिया गया है कि 'सभी गुलाब फूल हैं', अतः यह पूर्णतः सत्य है।"
        ),
        Question(
            id = 6,
            questionText = "श्रृंखला पूर्ण कीजिए: A, C, F, J, ?",
            options = listOf("M", "N", "O", "P"),
            correctOptionIndex = 2,
            explanation = "अक्षरों के अंतराल में 1 की वृद्धि: A(+2)→C(+3)→F(+4)→J(+5)→O (15वाँ अक्षर)।"
        ),
        Question(
            id = 7,
            questionText = "यदि 3² + 4² = 25 और 5² + 2² = 29 हो, तो 4² + 3² = ?",
            options = listOf("25", "26", "27", "28"),
            correctOptionIndex = 0,
            explanation = "a² + b² का मान: 4² + 3² = 16 + 9 = 25।"
        ),
        Question(
            id = 8,
            questionText = "एक पंक्ति में रवि बाएं से 7वें और दाएं से 9वें स्थान पर है। पंक्ति में कुल कितने व्यक्ति हैं?",
            options = listOf("15", "16", "17", "18"),
            correctOptionIndex = 0,
            explanation = "कुल संख्या = (बायां स्थान + दायां स्थान) - 1 = (7 + 9) - 1 = 15।"
        ),
        Question(
            id = 9,
            questionText = "लुप्त पद ज्ञात कीजिए: 4, 9, 19, 39, ?",
            options = listOf("69", "79", "89", "99"),
            correctOptionIndex = 1,
            explanation = "नियम (संख्या × 2) + 1 है: 4×2+1=9, 9×2+1=19, 19×2+1=39, 39×2+1=79।"
        ),
        Question(
            id = 10,
            questionText = "P, Q का भाई है। Q, R की बहन है। P का R से क्या संबंध है?",
            options = listOf("भाई", "बहन", "पिता", "चाचा"),
            correctOptionIndex = 0,
            explanation = "P, Q और R सहोदर हैं। चूंकि P पुरुष है, अतः P, R का भाई है।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
