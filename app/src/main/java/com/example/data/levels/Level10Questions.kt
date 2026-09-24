package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 10: Master Reasoning Challenge (Final Level).
 */
object Level10Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Complete the series: 0, 6, 24, 60, 120, 210, ?",
            options = listOf("290", "336", "340", "360"),
            correctOptionIndex = 1,
            explanation = "Formula is n³ - n: 1³-1=0, 2³-2=6, 3³-3=24, 4³-4=60, 5³-5=120, 6³-6=210, so 7³-7 = 343 - 7 = 336."
        ),
        Question(
            id = 2,
            questionText = "If CLOCK is coded as 34235 and TIME is coded as 8679, how is MOLECULE coded?",
            options = listOf("72493549", "72493459", "72493594", "72494539"),
            correctOptionIndex = 0,
            explanation = "Direct digit mapping from words: M=7, O=2, L=4, E=9, C=3, U=5, L=4, E=9 → 72493549."
        ),
        Question(
            id = 3,
            questionText = "A cube has all sides painted red. It is cut into 64 small cubes of equal size. How many small cubes have only one face painted red?",
            options = listOf("8", "16", "24", "32"),
            correctOptionIndex = 2,
            explanation = "For n = ∛64 = 4: One face painted cubes = 6 × (n - 2)² = 6 × (4 - 2)² = 6 × 4 = 24."
        ),
        Question(
            id = 4,
            questionText = "Statements: Some actors are singers. All singers are dancers. Conclusions: I. Some actors are dancers. II. No singer is actor.",
            options = listOf("Only conclusion I follows", "Only conclusion II follows", "Both follow", "Neither follows"),
            correctOptionIndex = 0,
            explanation = "Since Some actors are singers and All singers are dancers, the actors who are singers are also dancers. Hence I follows."
        ),
        Question(
            id = 5,
            questionText = "Find the missing number in the 3x3 magic square (sum=15): (4, 9, 2), (3, 5, 7), (8, 1, ?)",
            options = listOf("6", "7", "8", "9"),
            correctOptionIndex = 0,
            explanation = "Row 3: 8 + 1 + ? = 15. Therefore, ? = 15 - 9 = 6."
        ),
        Question(
            id = 6,
            questionText = "If 1st March was Wednesday, what day was 1st July of the same non-leap year?",
            options = listOf("Friday", "Saturday", "Sunday", "Monday"),
            correctOptionIndex = 1,
            explanation = "Days from March 1 to July 1: March (30 remaining) + April (30) + May (31) + June (30) = 121 days. 121 ÷ 7 leaves remainder 3. Wednesday + 3 = Saturday."
        ),
        Question(
            id = 7,
            questionText = "In a code, 'pit dar na' means 'you are good', 'dar tok pa' means 'good and bad', and 'tim na' means 'they are'. Which word means 'they'?",
            options = listOf("tim", "na", "dar", "pit"),
            correctOptionIndex = 0,
            explanation = "'na' means 'are' (common in 1st & 3rd). Therefore in 'tim na', 'tim' means 'they'."
        ),
        Question(
            id = 8,
            questionText = "A man faces West. He turns 45° clockwise, then 180° in the same direction, and then 270° anti-clockwise. Which direction is he facing now?",
            options = listOf("South", "North-West", "West", "South-West"),
            correctOptionIndex = 3,
            explanation = "Clockwise = +45° + 180° = +225°. Anti-clockwise = -270°. Net turn = -45° (45° anti-clockwise from West) = South-West."
        ),
        Question(
            id = 9,
            questionText = "Which number replaces ?: 2, 3, 10, 15, 26, 35, 50, ?",
            options = listOf("63", "64", "65", "66"),
            correctOptionIndex = 0,
            explanation = "Alternating pattern n² + 1 and n² - 1: 1²+1=2, 2²-1=3, 3²+1=10, 4²-1=15, 5²+1=26, 6²-1=35, 7²+1=50, 8²-1 = 64 - 1 = 63."
        ),
        Question(
            id = 10,
            questionText = "Pointing to a photograph of a boy, Suresh said, 'He is the son of the only son of my mother.' How is Suresh related to that boy?",
            options = listOf("Brother", "Uncle", "Father", "Cousin"),
            correctOptionIndex = 2,
            explanation = "Only son of Suresh's mother is Suresh himself. Thus the boy is Suresh's son, and Suresh is his father."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "श्रृंखला पूर्ण कीजिए: 0, 6, 24, 60, 120, 210, ?",
            options = listOf("290", "336", "340", "360"),
            correctOptionIndex = 1,
            explanation = "सूत्र n³ - n है: 7³ - 7 = 343 - 7 = 336।"
        ),
        Question(
            id = 2,
            questionText = "यदि CLOCK को 34235 और TIME को 8679 लिखा जाए, तो MOLECULE को क्या लिखा जाएगा?",
            options = listOf("72493549", "72493459", "72493594", "72494539"),
            correctOptionIndex = 0,
            explanation = "अक्षरों के कूट मानों के अनुसार 72493549 सही उत्तर है।"
        ),
        Question(
            id = 3,
            questionText = "एक ठोस घन के सभी फलकों को लाल रंगा गया और उसे 64 समान छोटे घनों में काटा गया। ऐसे कितने छोटे घन होंगे जिनका केवल एक फलक रंगा हो?",
            options = listOf("8", "16", "24", "32"),
            correctOptionIndex = 2,
            explanation = "n = 4 के लिए: केवल 1 फलक रंगे घनों की संख्या = 6 × (n - 2)² = 6 × 4 = 24।"
        ),
        Question(
            id = 4,
            questionText = "कथन: कुछ अभिनेता गायक हैं। सभी गायक नर्तक हैं। निष्कर्ष: I. कुछ अभिनेता नर्तक हैं। II. कोई गायक अभिनेता नहीं है।",
            options = listOf("केवल निष्कर्ष I सही है", "केवल निष्कर्ष II सही है", "दोनों सही हैं", "कोई नहीं"),
            correctOptionIndex = 0,
            explanation = "जो अभिनेता गायक हैं वे नर्तक भी होंगे, अतः निष्कर्ष I सही है।"
        ),
        Question(
            id = 5,
            questionText = "3x3 जादुई वर्ग में लुप्त संख्या ज्ञात कीजिए: पंक्ति 1 (4, 9, 2), पंक्ति 2 (3, 5, 7), पंक्ति 3 (8, 1, ?)",
            options = listOf("6", "7", "8", "9"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक पंक्ति का योग 15 है: 8 + 1 + ? = 15, अतः ? = 6।"
        ),
        Question(
            id = 6,
            questionText = "यदि किसी सामान्य वर्ष में 1 मार्च को बुधवार था, तो उसी वर्ष 1 जुलाई को कौन सा दिन था?",
            options = listOf("शुक्रवार", "शनिवार", "रविवार", "सोमवार"),
            correctOptionIndex = 1,
            explanation = "कुल 121 दिन = 17 सप्ताह + 2 दिन (शेष 2 विषम दिन: 30+30+31+30 = 121, 121 % 7 = 2 या 3? मार्च 30 शेष, अप्रैल 30, मई 31, जून 30 = 121, 121 % 7 = 3। बुध + 3 = शनिवार)।"
        ),
        Question(
            id = 7,
            questionText = "कूट भाषा में 'pit dar na' का अर्थ 'you are good', 'dar tok pa' का अर्थ 'good and bad', और 'tim na' का अर्थ 'they are' है। 'they' का कूट क्या होगा?",
            options = listOf("tim", "na", "dar", "pit"),
            correctOptionIndex = 0,
            explanation = "सामान्य वर्णों को हटाने पर 'they' का कूट 'tim' मिलता है।"
        ),
        Question(
            id = 8,
            questionText = "एक व्यक्ति पश्चिम की ओर मुख किए है। वह 45° दक्षिणावर्त, फिर 180° उसी दिशा में और फिर 270° वामावर्त घूमता है। अब उसका मुख किस दिशा में है?",
            options = listOf("दक्षिण", "उत्तर-पश्चिम", "पश्चिम", "दक्षिण-पश्चिम"),
            correctOptionIndex = 3,
            explanation = "कुल मोड़ = +225° - 270° = -45° (पश्चिम से 45° वामावर्त = दक्षिण-पश्चिम)।"
        ),
        Question(
            id = 9,
            questionText = "लुप्त पद ज्ञात कीजिए: 2, 3, 10, 15, 26, 35, 50, ?",
            options = listOf("63", "64", "65", "66"),
            correctOptionIndex = 0,
            explanation = "क्रमशः n² + 1 और n² - 1: 8² - 1 = 64 - 1 = 63।"
        ),
        Question(
            id = 10,
            questionText = "एक लड़के की तस्वीर की ओर इशारा करते हुए सुरेश ने कहा, 'वह मेरी माँ के इकलौते बेटे का बेटा है।' सुरेश का उस लड़के से क्या संबंध है?",
            options = listOf("भाई", "चाचा", "पिता", "चचेरा भाई"),
            correctOptionIndex = 2,
            explanation = "सुरेश की माँ का इकलौता बेटा स्वयं सुरेश है। अतः वह लड़का सुरेश का बेटा है और सुरेश उसका पिता है।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
