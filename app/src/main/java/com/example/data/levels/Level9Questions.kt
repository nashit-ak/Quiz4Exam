package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 9: Advanced Series, Missing Operations, and Analytical Reasoning.
 */
object Level9Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Find the missing number in the series: 6, 13, 28, 59, ?",
            options = listOf("118", "122", "120", "124"),
            correctOptionIndex = 1,
            explanation = "Rule is (n × 2) + k where k increases: 6×2+1=13, 13×2+2=28, 28×2+3=59, 59×2+4 = 118+4 = 122."
        ),
        Question(
            id = 2,
            questionText = "If TEACHER is coded as VGCEJGT, what code represents CHILDREN?",
            options = listOf("EJKNFTGP", "EJKTEFGP", "EJKNFGTO", "EJKNFHTP"),
            correctOptionIndex = 0,
            explanation = "Each letter shifts forward by +2: C+2=E, H+2=J, I+2=K, L+2=N, D+2=F, R+2=T, E+2=G, N+2=P → EJKNFTGP."
        ),
        Question(
            id = 3,
            questionText = "Statements: All cars are cats. All fans are cats. Conclusions: I. All cars are fans. II. Some cats are cars.",
            options = listOf("Only conclusion I follows", "Only conclusion II follows", "Both I and II follow", "Neither follows"),
            correctOptionIndex = 1,
            explanation = "Since All cars are cats, it directly implies that Some cats are cars. Conclusion I does not necessarily follow."
        ),
        Question(
            id = 4,
            questionText = "Which number is odd in this cube list: 125, 216, 343, 512, 729, 1000, 1331, 1728? (Which is not odd?)",
            options = listOf("All are perfect cubes", "None", "512", "1000"),
            correctOptionIndex = 0,
            explanation = "All numbers listed (5³, 6³, 7³, 8³, 9³, 10³, 11³, 12³) are perfect cubes."
        ),
        Question(
            id = 5,
            questionText = "Anand is heavier than Bala but lighter than Chandu. Dinesh is heavier than Anand. Who is definitely the lightest?",
            options = listOf("Bala", "Anand", "Chandu", "Dinesh"),
            correctOptionIndex = 0,
            explanation = "Anand > Bala, Chandu > Anand, Dinesh > Anand. Bala is lighter than Anand, Chandu, and Dinesh, so Bala is the lightest."
        ),
        Question(
            id = 6,
            questionText = "If 14 # 13 = 9 and 27 # 36 = 18, then 46 # 31 = ?",
            options = listOf("14", "15", "16", "17"),
            correctOptionIndex = 0,
            explanation = "Sum the individual digits: (1+4) + (1+3) = 5 + 4 = 9. (2+7) + (3+6) = 9 + 9 = 18. (4+6) + (3+1) = 10 + 4 = 14."
        ),
        Question(
            id = 7,
            questionText = "Find the missing letters in the sequence: AZ, CX, EV, ?",
            options = listOf("GT", "GS", "HS", "HT"),
            correctOptionIndex = 0,
            explanation = "First letters advance by 2 (A→C→E→G), second letters move back by 2 (Z→X→V→T). Result is GT."
        ),
        Question(
            id = 8,
            questionText = "A is the sister of B. B is married to C. C is the son of D. How is B related to D?",
            options = listOf("Daughter-in-law or Son-in-law", "Daughter", "Son", "Sister"),
            correctOptionIndex = 0,
            explanation = "B is the spouse of D's son C. Hence B is either daughter-in-law (if female) or son-in-law."
        ),
        Question(
            id = 9,
            questionText = "How many times do the hands of a clock coincide in 24 hours?",
            options = listOf("20", "22", "24", "44"),
            correctOptionIndex = 1,
            explanation = "The hands of a clock coincide 11 times in 12 hours, which equals 22 times in 24 hours."
        ),
        Question(
            id = 10,
            questionText = "Find the odd pair of words:",
            options = listOf("Mason : Wall", "Cobbler : Shoe", "Farmer : Crop", "Chef : Food"),
            correctOptionIndex = 0,
            explanation = "Cobbler makes shoe, farmer produces crop, chef prepares food, while a mason constructs a wall (all are creator-creation pairs, with Mason:Wall differing in scale/industry)."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "श्रृंखला में लुप्त संख्या ज्ञात कीजिए: 6, 13, 28, 59, ?",
            options = listOf("118", "122", "120", "124"),
            correctOptionIndex = 1,
            explanation = "नियम (n × 2) + k: 6×2+1=13, 13×2+2=28, 28×2+3=59, 59×2+4 = 122।"
        ),
        Question(
            id = 2,
            questionText = "यदि TEACHER को VGCEJGT लिखा जाता है, तो CHILDREN को क्या लिखा जाएगा?",
            options = listOf("EJKNFTGP", "EJKTEFGP", "EJKNFGTO", "EJKNFHTP"),
            correctOptionIndex = 0,
            explanation = "प्रत्येक वर्ण में +2 की वृद्धि है, जिससे EJKNFTGP बनता है।"
        ),
        Question(
            id = 3,
            questionText = "कथन: सभी कारें बिल्ली हैं। सभी पंखे बिल्ली हैं। निष्कर्ष: I. सभी कारें पंखे हैं। II. कुछ बिल्ली कारें हैं।",
            options = listOf("केवल निष्कर्ष I सही है", "केवल निष्कर्ष II सही है", "दोनों सही हैं", "कोई नहीं"),
            correctOptionIndex = 1,
            explanation = "सभी कारें बिल्ली हैं, इससे निश्चित रूप से निकलता है कि कुछ बिल्ली कारें हैं।"
        ),
        Question(
            id = 4,
            questionText = "संख्याओं 125, 216, 343, 512, 729, 1000 में क्या समानता है?",
            options = listOf("सभी पूर्ण घन संख्याएँ हैं", "कोई नहीं", "सभी अभाज्य हैं", "सभी सम हैं"),
            correctOptionIndex = 0,
            explanation = "सभी दी गई संख्याएँ पूर्ण घन (Cubes) हैं।"
        ),
        Question(
            id = 5,
            questionText = "आनंद बाला से भारी है परंतु चंदू से हल्का है। दिनेश आनंद से भारी है। सबसे हल्का कौन है?",
            options = listOf("बाला", "आनंद", "चंदू", "दिनेश"),
            correctOptionIndex = 0,
            explanation = "बाला सबसे नीचे आता है, अतः बाला सबसे हल्का है।"
        ),
        Question(
            id = 6,
            questionText = "यदि 14 # 13 = 9 और 27 # 36 = 18 हो, तो 46 # 31 = ?",
            options = listOf("14", "15", "16", "17"),
            correctOptionIndex = 0,
            explanation = "अंकों का योग: (4+6) + (3+1) = 10 + 4 = 14।"
        ),
        Question(
            id = 7,
            questionText = "अनुक्रम में लुप्त अक्षर ज्ञात कीजिए: AZ, CX, EV, ?",
            options = listOf("GT", "GS", "HS", "HT"),
            correctOptionIndex = 0,
            explanation = "पहला अक्षर +2 (E→G), दूसरा अक्षर -2 (V→T), अतः GT।"
        ),
        Question(
            id = 8,
            questionText = "A, B की बहन है। B का विवाह C से हुआ है। C, D का पुत्र है। B का D से क्या संबंध है?",
            options = listOf("बहू अथवा दामाद", "बेटी", "बेटा", "बहन"),
            correctOptionIndex = 0,
            explanation = "B, D के पुत्र C का जीवनसाथी है, अतः बहू या दामाद का संबंध है।"
        ),
        Question(
            id = 9,
            questionText = "24 घंटे में घड़ी की दोनों सुइयां कितनी बार एक साथ (संपाती) होती हैं?",
            options = listOf("20", "22", "24", "44"),
            correctOptionIndex = 1,
            explanation = "12 घंटे में 11 बार और 24 घंटे में कुल 22 बार सुइयां संपाती होती हैं।"
        ),
        Question(
            id = 10,
            questionText = "विषम शब्द युग्म का चयन कीजिए:",
            options = listOf("राजगीर : दीवार", "मोची : जूता", "किसान : फसल", "रसोइया : भोजन"),
            correctOptionIndex = 0,
            explanation = "राजगीर और दीवार का संबंध शेष निर्माण युग्मों से भिन्न श्रेणी का है।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
