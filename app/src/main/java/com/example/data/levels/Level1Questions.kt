package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 1: Beginner-friendly Reasoning Questions in English and Hindi.
 */
object Level1Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "If CAT is coded as DBU, how is DOG coded?",
            options = listOf("EPH", "EOG", "FPH", "DOH"),
            correctOptionIndex = 0,
            explanation = "Each letter is shifted by +1 in the alphabet: C+1=D, A+1=B, T+1=U. Similarly, D+1=E, O+1=P, G+1=H, so DOG becomes EPH."
        ),
        Question(
            id = 2,
            questionText = "Find the odd one out.",
            options = listOf("Apple", "Mango", "Banana", "Carrot"),
            correctOptionIndex = 3,
            explanation = "Carrot is a root vegetable, whereas Apple, Mango, and Banana are fruits."
        ),
        Question(
            id = 3,
            questionText = "What comes next in the number series? 2, 4, 8, 16, ?",
            options = listOf("24", "32", "30", "36"),
            correctOptionIndex = 1,
            explanation = "Each number is multiplied by 2: 2×2=4, 4×2=8, 8×2=16, therefore 16×2=32."
        ),
        Question(
            id = 4,
            questionText = "If A = 1, B = 2, C = 3, then what is the value of CAB?",
            options = listOf("5", "6", "7", "8"),
            correctOptionIndex = 1,
            explanation = "Substitute positional values of letters: C=3, A=1, B=2. Sum = 3 + 1 + 2 = 6."
        ),
        Question(
            id = 5,
            questionText = "Which word cannot be formed from the letters of BANKING?",
            options = listOf("KING", "BANK", "GAIN", "BOOK"),
            correctOptionIndex = 3,
            explanation = "The word 'BOOK' contains the letter 'O', which is not present in 'BANKING'."
        ),
        Question(
            id = 6,
            questionText = "Ravi walks 5 km North and then 5 km East. In which direction is he from his starting point?",
            options = listOf("North", "East", "North-East", "South-East"),
            correctOptionIndex = 2,
            explanation = "Walking north and then east places him in the North-East direction relative to the start."
        ),
        Question(
            id = 7,
            questionText = "Find the missing number: 3, 6, 12, 24, ?",
            options = listOf("36", "42", "48", "54"),
            correctOptionIndex = 2,
            explanation = "Each number is multiplied by 2: 3×2=6, 6×2=12, 12×2=24, so 24×2=48."
        ),
        Question(
            id = 8,
            questionText = "Which number is different from the others?",
            options = listOf("9", "16", "25", "30"),
            correctOptionIndex = 3,
            explanation = "9 (3²), 16 (4²), and 25 (5²) are perfect square numbers, whereas 30 is not a perfect square."
        ),
        Question(
            id = 9,
            questionText = "If MONDAY is coded as NPOEBZ, how is SUNDAY coded?",
            options = listOf("TVOEBZ", "TVODBZ", "TUNEBZ", "TVOFCZ"),
            correctOptionIndex = 0,
            explanation = "Each letter is shifted forward by +1: S→T, U→V, N→O, D→E, A→B, Y→Z, which yields TVOEBZ."
        ),
        Question(
            id = 10,
            questionText = "A is taller than B. B is taller than C. Who is the shortest?",
            options = listOf("A", "B", "C", "Cannot be determined"),
            correctOptionIndex = 2,
            explanation = "Comparing heights gives A > B > C. Therefore, C is the shortest among them."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "यदि CAT को DBU लिखा जाता है, तो DOG को क्या लिखा जाएगा?",
            options = listOf("EPH", "EOG", "FPH", "DOH"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक वर्ण में +1 जोड़ा गया है: C+1=D, A+1=B, T+1=U। अतः D+1=E, O+1=P, G+1=H, जिससे DOG का कोड EPH बनता है।"
        ),
        Question(
            id = 2,
            questionText = "इनमें से विषम (अलग) विकल्प को चुनिए:",
            options = listOf("सेब", "आम", "केला", "गाजर"),
            correctOptionIndex = 3,
            explanation = "गाजर एक जड़ वाली सब्जी है, जबकि सेब, आम और केला फल हैं।"
        ),
        Question(
            id = 3,
            questionText = "संख्या श्रृंखला में अगला पद क्या होगा? 2, 4, 8, 16, ?",
            options = listOf("24", "32", "30", "36"),
            correctOptionIndex = 1,
            explanation = "प्रत्येक संख्या में 2 से गुणा किया गया है: 2×2=4, 4×2=8, 8×2=16, अतः 16×2=32।"
        ),
        Question(
            id = 4,
            questionText = "यदि A = 1, B = 2, C = 3 हो, तो CAB का कुल मान क्या होगा?",
            options = listOf("5", "6", "7", "8"),
            correctOptionIndex = 1,
            explanation = "वर्णों के संख्यात्मक मान: C=3, A=1, B=2। कुल योग = 3 + 1 + 2 = 6।"
        ),
        Question(
            id = 5,
            questionText = "BANKING शब्द के अक्षरों से कौन सा शब्द नहीं बनाया जा सकता है?",
            options = listOf("KING", "BANK", "GAIN", "BOOK"),
            correctOptionIndex = 3,
            explanation = "'BOOK' शब्द में 'O' अक्षर आता है, जो BANKING में उपस्थित नहीं है।"
        ),
        Question(
            id = 6,
            questionText = "रवि 5 किमी उत्तर की ओर चलता है और फिर 5 किमी पूर्व की ओर। वह अपने प्रारंभिक बिंदु से किस दिशा में है?",
            options = listOf("उत्तर", "पूर्व", "उत्तर-पूर्व", "दक्षिण-पूर्व"),
            correctOptionIndex = 2,
            explanation = "उत्तर दिशा और फिर पूर्व दिशा में चलने पर प्रारंभिक बिंदु से उसकी दिशा उत्तर-पूर्व बनती है।"
        ),
        Question(
            id = 7,
            questionText = "लुप्त संख्या ज्ञात कीजिए: 3, 6, 12, 24, ?",
            options = listOf("36", "42", "48", "54"),
            correctOptionIndex = 2,
            explanation = "प्रत्येक संख्या को 2 से गुणा किया गया है: 3×2=6, 6×2=12, 12×2=24, इसलिए 24×2=48।"
        ),
        Question(
            id = 8,
            questionText = "इनमें से कौन सी संख्या अन्य तीनों से भिन्न है?",
            options = listOf("9", "16", "25", "30"),
            correctOptionIndex = 3,
            explanation = "9 (3²), 16 (4²) और 25 (5²) पूर्ण वर्ग संख्याएँ हैं, जबकि 30 पूर्ण वर्ग नहीं है।"
        ),
        Question(
            id = 9,
            questionText = "यदि MONDAY को NPOEBZ लिखा जाए, तो SUNDAY को क्या लिखा जाएगा?",
            options = listOf("TVOEBZ", "TVODBZ", "TUNEBZ", "TVOFCZ"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक अक्षर में +1 की वृद्धि की गई है: S→T, U→V, N→O, D→E, A→B, Y→Z, जिससे TVOEBZ प्राप्त होता है।"
        ),
        Question(
            id = 10,
            questionText = "A, B से लंबा है और B, C से लंबा है। इनमें सबसे छोटा कौन है?",
            options = listOf("A", "B", "C", "निर्धारित नहीं किया जा सकता"),
            correctOptionIndex = 2,
            explanation = "क्रम A > B > C बनता है, अतः C सबसे छोटा है।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
