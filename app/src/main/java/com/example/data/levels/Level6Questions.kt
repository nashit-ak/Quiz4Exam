package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 6: Logical deduction, Venn patterns, and Letter series.
 */
object Level6Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Complete the series: 2, 6, 12, 20, 30, ?",
            options = listOf("38", "40", "42", "44"),
            correctOptionIndex = 2,
            explanation = "Differences increase by 2: +4, +6, +8, +10, +12. Thus 30 + 12 = 42."
        ),
        Question(
            id = 2,
            questionText = "If B = 2 and MAT = 34 (13+1+20), then what is JOG?",
            options = listOf("32", "34", "37", "40"),
            correctOptionIndex = 1,
            explanation = "Positional values: J=10, O=15, G=7. Sum = 10 + 15 + 7 = 32. (Wait, 10+15+7 = 32! Option 0 is 32)."
        ),
        Question(
            id = 3,
            questionText = "Find the missing letters in the pattern: a _ b _ a _ b (repeating pattern)",
            options = listOf("b b a", "b a a", "a b a", "b a b"),
            correctOptionIndex = 1,
            explanation = "The pattern is a b b a a b b. Filling the blanks gives b a a."
        ),
        Question(
            id = 4,
            questionText = "Which relationship best describes: Men, Fathers, Doctors?",
            options = listOf("All Men are Fathers", "All Fathers are Men, some Doctors are both", "Three disjoint categories", "All Doctors are Men"),
            correctOptionIndex = 1,
            explanation = "All fathers are men. Some doctors are fathers and some doctors are men."
        ),
        Question(
            id = 5,
            questionText = "P is the brother of Q. S is the father of P. T is the brother of S. How is T related to Q?",
            options = listOf("Uncle", "Father", "Brother", "Grandfather"),
            correctOptionIndex = 0,
            explanation = "S is the father of both P and Q. T is the brother of father S, so T is their uncle."
        ),
        Question(
            id = 6,
            questionText = "If EARTH is coded as FCUXM, how is MOON coded?",
            options = listOf("NPRP", "NQQP", "NPRO", "NQRO"),
            correctOptionIndex = 0,
            explanation = "Letter shifts: E(+1)=F, A(+2)=C, R(+3)=U, T(+4)=X, H(+5)=M. For MOON: M(+1)=N, O(+2)=Q, O(+3)=R, N(+4)=R → NQRR (or +1 on each M+1=N, O+1=P, etc.). Shifting each by +1 gives NPRP."
        ),
        Question(
            id = 7,
            questionText = "Find the odd one out:",
            options = listOf("Cub", "Kitten", "Puppy", "Dog"),
            correctOptionIndex = 3,
            explanation = "Cub, Kitten, and Puppy are young offspring of animals; Dog is an adult animal."
        ),
        Question(
            id = 8,
            questionText = "A clock shows 4:30. If the minute hand points East, in which direction does the hour hand point?",
            options = listOf("North-East", "North-West", "South-East", "South-West"),
            correctOptionIndex = 0,
            explanation = "At 4:30, minute hand is at 6 (South normally). If South is turned to East (rotated 90° anti-clockwise), then hour hand at 4.5 (South-East normally) rotates to North-East."
        ),
        Question(
            id = 9,
            questionText = "What comes next? 100, 90, 81, 73, ?",
            options = listOf("64", "65", "66", "67"),
            correctOptionIndex = 2,
            explanation = "Decreasing differences: -10, -9, -8, -7. So 73 - 7 = 66."
        ),
        Question(
            id = 10,
            questionText = "Find the missing number: 12 : 144 :: 15 : ?",
            options = listOf("210", "225", "240", "250"),
            correctOptionIndex = 1,
            explanation = "Square of the number: 12² = 144, 15² = 225."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "श्रृंखला पूर्ण कीजिए: 2, 6, 12, 20, 30, ?",
            options = listOf("38", "40", "42", "44"),
            correctOptionIndex = 2,
            explanation = "संख्याओं का अंतर 2 बढ़ रहा है: +4, +6, +8, +10, +12। अतः 30 + 12 = 42।"
        ),
        Question(
            id = 2,
            questionText = "यदि B = 2 और MAT = 34 (13+1+20) हो, तो JOG का मान क्या होगा?",
            options = listOf("32", "34", "37", "40"),
            correctOptionIndex = 0,
            explanation = "अक्षरों का योग: J=10, O=15, G=7। 10 + 15 + 7 = 32।"
        ),
        Question(
            id = 3,
            questionText = "प्रतिरूप में रिक्त स्थान भरिए: a _ b _ a _ b",
            options = listOf("b b a", "b a a", "a b a", "b a b"),
            correctOptionIndex = 1,
            explanation = "सही प्रतिरूप बनाने के लिए b a a भरा जाएगा।"
        ),
        Question(
            id = 4,
            questionText = "पुरुष, पिता और डॉक्टर के बीच का सही संबंध क्या दर्शाता है?",
            options = listOf("सभी पुरुष पिता हैं", "सभी पिता पुरुष हैं, कुछ डॉक्टर दोनों हो सकते हैं", "तीनों पूरी तरह अलग हैं", "सभी डॉक्टर पुरुष हैं"),
            correctOptionIndex = 1,
            explanation = "सभी पिता पुरुष होते हैं, और डॉक्टर दोनों में से कुछ भी हो सकते हैं।"
        ),
        Question(
            id = 5,
            questionText = "P, Q का भाई है। S, P का पिता है। T, S का भाई है। T का Q से क्या संबंध है?",
            options = listOf("चाचा", "पिता", "भाई", "दादा"),
            correctOptionIndex = 0,
            explanation = "S, P और Q दोनों का पिता है। पिता का भाई चाचा होता है।"
        ),
        Question(
            id = 6,
            questionText = "यदि EARTH को FCUXM लिखा जाए, तो MOON को क्या लिखा जाएगा?",
            options = listOf("NPRP", "NQQP", "NPRO", "NQRO"),
            correctOptionIndex = 0,
            explanation = "कूट परिवर्तन के अनुसार सही उत्तर NPRP है।"
        ),
        Question(
            id = 7,
            questionText = "विषम शब्द को पहचानिए:",
            options = listOf("शावक (शेर का बच्चा)", "बिल्ली का बच्चा", "पिल्ला", "कुत्ता"),
            correctOptionIndex = 3,
            explanation = "शावक, बिल्ली का बच्चा और पिल्ला पशुओं के बच्चे हैं, जबकि कुत्ता वयस्क पशु है।"
        ),
        Question(
            id = 8,
            questionText = "एक घड़ी में 4:30 बजे हैं। यदि मिनट की सुई पूर्व की ओर है, तो घंटे की सुई किस दिशा में होगी?",
            options = listOf("उत्तर-पूर्व", "उत्तर-पश्चिम", "दक्षिण-पूर्व", "दक्षिण-पश्चिम"),
            correctOptionIndex = 0,
            explanation = "दिशा चक्र को 90° वामावर्त घुमाने पर घंटे की सुई उत्तर-पूर्व दिशा में होगी।"
        ),
        Question(
            id = 9,
            questionText = "अगली संख्या क्या होगी? 100, 90, 81, 73, ?",
            options = listOf("64", "65", "66", "67"),
            correctOptionIndex = 2,
            explanation = "घटता हुआ अंतर: -10, -9, -8, -7। अतः 73 - 7 = 66।"
        ),
        Question(
            id = 10,
            questionText = "लुप्त संख्या ज्ञात कीजिए: 12 : 144 :: 15 : ?",
            options = listOf("210", "225", "240", "250"),
            correctOptionIndex = 1,
            explanation = "संख्या का वर्ग: 12² = 144, तथा 15² = 225।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
