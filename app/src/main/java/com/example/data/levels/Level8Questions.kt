package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 8: Critical Reasoning, Seating, and Pattern Deduction.
 */
object Level8Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Five friends sit in a row facing North. A is to the immediate right of B. E is to the left of B but to the right of C. If D is to the right of A, who is in the middle?",
            options = listOf("B", "A", "E", "C"),
            correctOptionIndex = 0,
            explanation = "The order from left to right is C, E, B, A, D. B is right in the middle."
        ),
        Question(
            id = 2,
            questionText = "What comes next in the sequence: 4, 18, 48, 100, ?",
            options = listOf("160", "180", "200", "210"),
            correctOptionIndex = 1,
            explanation = "Formula is n² × (n+1): for n=1: 1×2=2? Or n³ - n²: 2³-2²=4, 3³-3²=18, 4³-4²=48, 5³-5²=100, so 6³-6² = 216 - 36 = 180."
        ),
        Question(
            id = 3,
            questionText = "If FRIEND is coded as HUMJTK, how is CANDLE coded?",
            options = listOf("EDRIRL", "DCQHQK", "ESJFME", "DEQJQM"),
            correctOptionIndex = 0,
            explanation = "Letter shifts increase: F(+2)=H, R(+3)=U, I(+4)=M, E(+5)=J, N(+6)=T, D(+7)=K. For CANDLE: C(+2)=E, A(+3)=D, N(+4)=R, D(+5)=I, L(+6)=R, E(+7)=L → EDRIRL."
        ),
        Question(
            id = 4,
            questionText = "Statements: All cups are books. All books are shirts. Which conclusion follows?",
            options = listOf("All cups are shirts", "Some shirts are not cups", "No cup is a shirt", "None of these"),
            correctOptionIndex = 0,
            explanation = "Since Cups ⊂ Books and Books ⊂ Shirts, it follows that All cups are shirts."
        ),
        Question(
            id = 5,
            questionText = "Find the missing term: 11 : 132 :: 12 : ?",
            options = listOf("144", "150", "156", "168"),
            correctOptionIndex = 2,
            explanation = "Rule is n × (n + 1): 11 × 12 = 132; 12 × 13 = 156."
        ),
        Question(
            id = 6,
            questionText = "A man travels 4 km due North, then 3 km due East. How far is he from his starting point?",
            options = listOf("5 km", "6 km", "7 km", "8 km"),
            correctOptionIndex = 0,
            explanation = "Pythagorean theorem: √(4² + 3²) = √(16 + 9) = √25 = 5 km."
        ),
        Question(
            id = 7,
            questionText = "Choose the odd one out:",
            options = listOf("Mars", "Venus", "Moon", "Jupiter"),
            correctOptionIndex = 2,
            explanation = "Mars, Venus, and Jupiter are planets; Moon is a natural satellite."
        ),
        Question(
            id = 8,
            questionText = "If 'white' is called 'blue', 'blue' is called 'red', and 'red' is called 'yellow', what is the color of human blood?",
            options = listOf("Red", "Blue", "Yellow", "White"),
            correctOptionIndex = 2,
            explanation = "Human blood is red, and in the given code, 'red' is called 'yellow'."
        ),
        Question(
            id = 9,
            questionText = "Find the next letter in the series: A, E, I, M, ?",
            options = listOf("P", "Q", "R", "N"),
            correctOptionIndex = 1,
            explanation = "Letters advance by +4: A(1)+4=E(5), +4=I(9), +4=M(13), +4=Q(17)."
        ),
        Question(
            id = 10,
            questionText = "If the 1st day of a non-leap year is Friday, what will be the last day of that year?",
            options = listOf("Thursday", "Friday", "Saturday", "Sunday"),
            correctOptionIndex = 1,
            explanation = "An ordinary year of 365 days starts and ends on the exact same day of the week (Friday)."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "पाँच मित्र उत्तर की ओर मुख करके एक पंक्ति में बैठे हैं। A, B के ठीक दाएं है। E, B के बाएं परंतु C के दाएं है। यदि D, A के दाएं है, तो मध्य में कौन बैठा है?",
            options = listOf("B", "A", "E", "C"),
            correctOptionIndex = 0,
            explanation = "क्रम C, E, B, A, D बनता है। अतः ठीक बीच में B बैठा है।"
        ),
        Question(
            id = 2,
            questionText = "श्रृंखला में अगला पद क्या होगा: 4, 18, 48, 100, ?",
            options = listOf("160", "180", "200", "210"),
            correctOptionIndex = 1,
            explanation = "सूत्र n³ - n² है: 6³ - 6² = 216 - 36 = 180।"
        ),
        Question(
            id = 3,
            questionText = "यदि FRIEND को HUMJTK लिखा जाता है, तो CANDLE को क्या लिखा जाएगा?",
            options = listOf("EDRIRL", "DCQHQK", "ESJFME", "DEQJQM"),
            correctOptionIndex = 0,
            explanation = "क्रमिक वृद्धि (+2, +3, +4, +5, +6, +7) से EDRIRL प्राप्त होता है।"
        ),
        Question(
            id = 4,
            questionText = "कथन: सभी कप पुस्तकें हैं। सभी पुस्तकें शर्ट हैं। कौन सा निष्कर्ष निकलता है?",
            options = listOf("सभी कप शर्ट हैं", "कुछ शर्ट कप नहीं हैं", "कोई कप शर्ट नहीं है", "इनमें से कोई नहीं"),
            correctOptionIndex = 0,
            explanation = "न्याय वाक्य के नियमानुसार: सभी कप शर्ट हैं।"
        ),
        Question(
            id = 5,
            questionText = "लुप्त पद ज्ञात कीजिए: 11 : 132 :: 12 : ?",
            options = listOf("144", "150", "156", "168"),
            correctOptionIndex = 2,
            explanation = "नियम n × (n + 1) है: 12 × 13 = 156।"
        ),
        Question(
            id = 6,
            questionText = "एक व्यक्ति 4 किमी उत्तर की ओर और फिर 3 किमी पूर्व की ओर जाता है। वह आरंभिक बिंदु से कितनी दूरी पर है?",
            options = listOf("5 किमी", "6 किमी", "7 किमी", "8 किमी"),
            correctOptionIndex = 0,
            explanation = "पाइथागोरस प्रमेय से: √(4² + 3²) = √(16 + 9) = 5 किमी।"
        ),
        Question(
            id = 7,
            questionText = "विषम का चयन कीजिए:",
            options = listOf("मंगल", "शुक्र", "चंद्रमा", "बृहस्पति"),
            correctOptionIndex = 2,
            explanation = "मंगल, शुक्र और बृहस्पति ग्रह हैं, जबकि चंद्रमा एक उपग्रह है।"
        ),
        Question(
            id = 8,
            questionText = "यदि 'सफेद' को 'नीला', 'नीले' को 'लाल', और 'लाल' को 'पीला' कहा जाए, तो मानव रक्त का रंग क्या होगा?",
            options = listOf("लाल", "नीला", "पीला", "सफेद"),
            correctOptionIndex = 2,
            explanation = "रक्त का वास्तविक रंग लाल होता है, और कूट में लाल को पीला कहा गया है।"
        ),
        Question(
            id = 9,
            questionText = "श्रृंखला का अगला अक्षर क्या होगा: A, E, I, M, ?",
            options = listOf("P", "Q", "R", "N"),
            correctOptionIndex = 1,
            explanation = "+4 की वृद्धि: M(13) + 4 = Q(17)।"
        ),
        Question(
            id = 10,
            questionText = "यदि किसी सामान्य (गैर-लीप) वर्ष का पहला दिन शुक्रवार है, तो उस वर्ष का अंतिम दिन कौन सा होगा?",
            options = listOf("गुरुवार", "शुक्रवार", "शनिवार", "रविवार"),
            correctOptionIndex = 1,
            explanation = "सामान्य वर्ष जिस दिन प्रारंभ होता है, उसी दिन समाप्त भी होता है (शुक्रवार)।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
