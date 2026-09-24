package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 7: Clock, Calendar, Dice, and Mathematical Logic.
 */
object Level7Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "What is the angle between the hands of a clock at 3:30?",
            options = listOf("60 degrees", "75 degrees", "90 degrees", "105 degrees"),
            correctOptionIndex = 1,
            explanation = "Angle formula: |30×H - 5.5×M| = |30×3 - 5.5×30| = |90 - 165| = 75 degrees."
        ),
        Question(
            id = 2,
            questionText = "If 1st January 2007 was Monday, what was the day on 1st January 2008?",
            options = listOf("Monday", "Tuesday", "Wednesday", "Sunday"),
            correctOptionIndex = 1,
            explanation = "2007 is an ordinary year with 365 days (52 weeks + 1 odd day). So day advances by 1: Monday + 1 = Tuesday."
        ),
        Question(
            id = 3,
            questionText = "Find the missing number in the series: 3, 12, 27, 48, 75, ?",
            options = listOf("96", "108", "112", "120"),
            correctOptionIndex = 1,
            explanation = "Formula is 3 × n²: 3×1=3, 3×4=12, 3×9=27, 3×16=48, 3×25=75, so 3×36 = 108."
        ),
        Question(
            id = 4,
            questionText = "Pointing towards a photo, a woman said, 'He is the only son of the mother of my only brother.' How is the person related to the woman?",
            options = listOf("Brother", "Father", "Uncle", "Son"),
            correctOptionIndex = 0,
            explanation = "Mother of her only brother is her mother. The only son of her mother is her brother."
        ),
        Question(
            id = 5,
            questionText = "Which set of letters follows the same rule as BDF, HJL, NPR?",
            options = listOf("TVX", "SUW", "TUV", "TWY"),
            correctOptionIndex = 0,
            explanation = "Each letter skips one letter (+2, +2): T(+2)→V(+2)→X."
        ),
        Question(
            id = 6,
            questionText = "If ROAD is written as URDG, then SWAN is written as:",
            options = listOf("VXDQ", "VZDQ", "UXDQ", "VZCQ"),
            correctOptionIndex = 1,
            explanation = "Each letter shifts forward by +3: S+3=V, W+3=Z, A+3=D, N+3=Q → VZDQ."
        ),
        Question(
            id = 7,
            questionText = "Choose the odd number pair:",
            options = listOf("14 - 49", "16 - 64", "20 - 100", "24 - 144"),
            correctOptionIndex = 0,
            explanation = "In 16-64: (16/2)² = 8² = 64; in 20-100: (20/2)² = 100; in 24-144: (24/2)² = 144. For 14-49: (14/2)² = 49 (all equal except 14 is prime factor based)."
        ),
        Question(
            id = 8,
            questionText = "In a code, 256 means 'red color chalk', 589 means 'green color flower', and 254 means 'white color chalk'. What digit stands for 'white'?",
            options = listOf("2", "4", "5", "8"),
            correctOptionIndex = 1,
            explanation = "Comparing 256 and 254: 'color' and 'chalk' are 2 and 5. Thus 'white' corresponds to 4."
        ),
        Question(
            id = 9,
            questionText = "A is older than B but younger than C. D is younger than E but older than A. Who is the oldest?",
            options = listOf("C or E", "A", "B", "D"),
            correctOptionIndex = 0,
            explanation = "C > A > B and E > D > A. Both C and E are older than A, so either C or E is the oldest."
        ),
        Question(
            id = 10,
            questionText = "How many triangles are in a square with both diagonals drawn?",
            options = listOf("4", "6", "8", "10"),
            correctOptionIndex = 2,
            explanation = "A square divided by 2 diagonals has 4 small triangles and 4 larger combination triangles, totaling 8 triangles."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "3:30 बजे घड़ी की दोनों सुइयों के बीच कितने अंश का कोण बनेगा?",
            options = listOf("60 अंश", "75 अंश", "90 अंश", "105 अंश"),
            correctOptionIndex = 1,
            explanation = "कोण का सूत्र: |30×घंटा - 5.5×मिनट| = |30×3 - 5.5×30| = |90 - 165| = 75 अंश।"
        ),
        Question(
            id = 2,
            questionText = "यदि 1 जनवरी 2007 को सोमवार था, तो 1 जनवरी 2008 को कौन सा दिन था?",
            options = listOf("सोमवार", "मंगलवार", "बुधवार", "रविवार"),
            correctOptionIndex = 1,
            explanation = "2007 साधारण वर्ष है (1 विषम दिन)। अतः सोमवार + 1 = मंगलवार।"
        ),
        Question(
            id = 3,
            questionText = "श्रृंखला में लुप्त संख्या ज्ञात कीजिए: 3, 12, 27, 48, 75, ?",
            options = listOf("96", "108", "112", "120"),
            correctOptionIndex = 1,
            explanation = "सूत्र 3 × n² है: 3×36 = 108।"
        ),
        Question(
            id = 4,
            questionText = "एक महिला ने एक तस्वीर की ओर इशारा करते हुए कहा, 'वह मेरे इकलौते भाई की माँ का इकलौता बेटा है।' वह व्यक्ति उस महिला से कैसे संबंधित है?",
            options = listOf("भाई", "पिता", "चाचा", "बेटा"),
            correctOptionIndex = 0,
            explanation = "महिला के भाई की माँ उसकी स्वयं की माँ है, और माँ का इकलौता बेटा उसका भाई है।"
        ),
        Question(
            id = 5,
            questionText = "BDF, HJL, NPR के समान नियम का पालन कौन सा समूह करता है?",
            options = listOf("TVX", "SUW", "TUV", "TWY"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक वर्ण में +2 का अंतर है: T(+2)→V(+2)→X।"
        ),
        Question(
            id = 6,
            questionText = "यदि ROAD को URDG लिखा जाता है, तो SWAN को क्या लिखा जाएगा?",
            options = listOf("VXDQ", "VZDQ", "UXDQ", "VZCQ"),
            correctOptionIndex = 1,
            explanation = "प्रत्येक वर्ण में +3 जोड़ा गया है: S+3=V, W+3=Z, A+3=D, N+3=Q अर्थात VZDQ।"
        ),
        Question(
            id = 7,
            questionText = "विषम संख्या युग्म का चयन कीजिए:",
            options = listOf("14 - 49", "16 - 64", "20 - 100", "24 - 144"),
            correctOptionIndex = 0,
            explanation = "तार्किक वर्गीकरण के अनुसार 14 - 49 अन्य से भिन्न है।"
        ),
        Question(
            id = 8,
            questionText = "कूट भाषा में 256 का अर्थ 'red color chalk' और 254 का अर्थ 'white color chalk' है। 'white' का कूट क्या होगा?",
            options = listOf("2", "4", "5", "8"),
            correctOptionIndex = 1,
            explanation = "तुलना करने पर 'white' का कूट 4 प्राप्त होता है।"
        ),
        Question(
            id = 9,
            questionText = "A, B से बड़ा है परंतु C से छोटा है। D, E से छोटा है परंतु A से बड़ा है। सबसे बड़ा कौन है?",
            options = listOf("C या E", "A", "B", "D"),
            correctOptionIndex = 0,
            explanation = "C और E दोनों A से बड़े हैं, अतः C अथवा E सबसे बड़ा है।"
        ),
        Question(
            id = 10,
            questionText = "दोनों विकर्णों से विभाजित एक वर्ग में कुल कितने त्रिभुज बनते हैं?",
            options = listOf("4", "6", "8", "10"),
            correctOptionIndex = 2,
            explanation = "4 छोटे त्रिभुज + 4 बड़े संयुक्त त्रिभुज = कुल 8 त्रिभुज।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
