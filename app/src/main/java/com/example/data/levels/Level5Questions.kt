package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 5: Number Puzzles, Ranking, and Logical Sequences.
 */
object Level5Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Which number replaces the question mark?\n3, 5, 9, 17, 33, ?",
            options = listOf("49", "65", "64", "72"),
            correctOptionIndex = 1,
            explanation = "Differences are powers of 2: +2, +4, +8, +16, +32. Thus 33 + 32 = 65."
        ),
        Question(
            id = 2,
            questionText = "If DELHI is coded as 73541 and CALCUTTA is coded as 82589662, how is CALICUT coded?",
            options = listOf("5279431", "8251896", "8251396", "8251369"),
            correctOptionIndex = 1,
            explanation = "This is a direct letter substitution pattern. From DELHI, we get I = 1. From CALCUTTA, we get C = 8, A = 2, L = 5, U = 9, T = 6. Substituting these into CALICUT gives: C(8) A(2) L(5) I(1) C(8) U(9) T(6) = 8251896.",
            uid = "9482015739"
        ),
        Question(
            id = 3,
            questionText = "A is the mother of B. C is the father of A. How is C related to B?",
            options = listOf("Grandfather", "Father", "Uncle", "Brother"),
            correctOptionIndex = 0,
            explanation = "Mother's father is maternal grandfather."
        ),
        Question(
            id = 4,
            questionText = "Find the odd one out among numbers:",
            options = listOf("13", "17", "23", "27"),
            correctOptionIndex = 3,
            explanation = "13, 17, and 23 are prime numbers; 27 is a composite number divisible by 3 and 9."
        ),
        Question(
            id = 5,
            questionText = "Book : Author :: Statue : ?",
            options = listOf("Mason", "Painter", "Sculptor", "Architect"),
            correctOptionIndex = 2,
            explanation = "An author creates a book, and a sculptor sculpts a statue."
        ),
        Question(
            id = 6,
            questionText = "What comes next in the letter series? AZ, BY, CX, DW, ?",
            options = listOf("EV", "EU", "FU", "FW"),
            correctOptionIndex = 0,
            explanation = "Pairs of opposite alphabet letters in increasing order: A-Z, B-Y, C-X, D-W, E-V."
        ),
        Question(
            id = 7,
            questionText = "If today is Monday, what day will it be after 61 days?",
            options = listOf("Tuesday", "Wednesday", "Thursday", "Saturday"),
            correctOptionIndex = 3,
            explanation = "61 divided by 7 leaves remainder 5. 5 days after Monday is Saturday."
        ),
        Question(
            id = 8,
            questionText = "In a row of 25 trees, a pine tree is 8th from the right end. What is its position from the left end?",
            options = listOf("16th", "17th", "18th", "19th"),
            correctOptionIndex = 2,
            explanation = "Position from left = (25 - 8) + 1 = 18th."
        ),
        Question(
            id = 9,
            questionText = "Find the missing number: 6 : 36 :: 7 : ?",
            options = listOf("42", "47", "49", "56"),
            correctOptionIndex = 2,
            explanation = "Rule is n : n²: 6² = 36, so 7² = 49."
        ),
        Question(
            id = 10,
            questionText = "Which number is opposite to 1 in a standard dice?",
            options = listOf("2", "4", "5", "6"),
            correctOptionIndex = 3,
            explanation = "On a standard dice, opposite faces always sum to 7: 1 + 6 = 7, so opposite to 1 is 6."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "प्रश्नवाचक चिह्न के स्थान पर कौन सी संख्या आएगी?\n3, 5, 9, 17, 33, ?",
            options = listOf("49", "65", "64", "72"),
            correctOptionIndex = 1,
            explanation = "अंतर 2 की घातों में बढ़ रहा है: +2, +4, +8, +16, +32। अतः 33 + 32 = 65।"
        ),
        Question(
            id = 2,
            questionText = "यदि DELHI को 73541 और CALCUTTA को 82589662 लिखा जाए, तो CALICUT का कोड क्या होगा?",
            options = listOf("5279431", "8251896", "8251396", "8251369"),
            correctOptionIndex = 1,
            explanation = "यह एक सीधा अक्षर प्रतिस्थापन पैटर्न है। DELHI से हमें I = 1 मिलता है। CALCUTTA से हमें C = 8, A = 2, L = 5, U = 9, T = 6 मिलता है। CALICUT में मान रखने पर: C(8) A(2) L(5) I(1) C(8) U(9) T(6) = 8251896 प्राप्त होता है।",
            uid = "9482015739"
        ),
        Question(
            id = 3,
            questionText = "A, B की माता है और C, A का पिता है। C का B से क्या संबंध है?",
            options = listOf("नाना (दादा)", "पिता", "चाचा", "भाई"),
            correctOptionIndex = 0,
            explanation = "माता का पिता नाना (Grandfather) कहलाता है।"
        ),
        Question(
            id = 4,
            questionText = "इनमें से विषम संख्या का चयन कीजिए:",
            options = listOf("13", "17", "23", "27"),
            correctOptionIndex = 3,
            explanation = "13, 17 और 23 अभाज्य संख्याएँ हैं, जबकि 27 एक भाज्य संख्या (3 × 9) है।"
        ),
        Question(
            id = 5,
            questionText = "पुस्तक : लेखक :: मूर्ति : ?",
            options = listOf("मिस्त्री", "चित्रकार", "मूर्तिकार", "वास्तुकार"),
            correctOptionIndex = 2,
            explanation = "पुस्तक का निर्माण लेखक करता है, और मूर्ति का निर्माण मूर्तिकार करता है।"
        ),
        Question(
            id = 6,
            questionText = "अक्षर श्रृंखला में अगला पद क्या होगा? AZ, BY, CX, DW, ?",
            options = listOf("EV", "EU", "FU", "FW"),
            correctOptionIndex = 0,
            explanation = "वर्णमाला के विपरीत अक्षरों का युग्म है: A-Z, B-Y, C-X, D-W, E-V।"
        ),
        Question(
            id = 7,
            questionText = "यदि आज सोमवार है, तो 61 दिन बाद कौन सा दिन होगा?",
            options = listOf("मंगलवार", "बुधवार", "गुरुवार", "शनिवार"),
            correctOptionIndex = 3,
            explanation = "61 को 7 से भाग देने पर शेषफल 5 आता है। सोमवार में 5 दिन जोड़ने पर शनिवार आता है।"
        ),
        Question(
            id = 8,
            questionText = "25 पेड़ों की एक पंक्ति में एक पेड़ दाएं छोर से 8वां है। बाएं छोर से उसका स्थान क्या होगा?",
            options = listOf("16वां", "17वां", "18वां", "19वां"),
            correctOptionIndex = 2,
            explanation = "बाएं से स्थान = (25 - 8) + 1 = 18वां।"
        ),
        Question(
            id = 9,
            questionText = "समानुपात हल कीजिए: 6 : 36 :: 7 : ?",
            options = listOf("42", "47", "49", "56"),
            correctOptionIndex = 2,
            explanation = "वर्ग नियम: 6² = 36, अतः 7² = 49।"
        ),
        Question(
            id = 10,
            questionText = "एक मानक पासे में 1 के विपरीत कौन सा अंक होता है?",
            options = listOf("2", "4", "5", "6"),
            correctOptionIndex = 3,
            explanation = "मानक पासे में विपरीत फलकों का योग 7 होता है: 1 + 6 = 7, अतः 1 के विपरीत 6 होता है।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
