package com.example.data.levels

import com.example.data.model.Question

/**
 * Level 4: Blood Relations, Coding, and Word Formation.
 */
object Level4Questions {
    val en: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "Pointing to a man, Rahul says, 'He is the son of my grandfather's only son.' How is Rahul related to the man?",
            options = listOf("Brother", "Father", "Uncle", "Cousin"),
            correctOptionIndex = 0,
            explanation = "Grandfather's only son is Rahul's father. The father's son is Rahul's brother (or Rahul himself; here Brother is the option)."
        ),
        Question(
            id = 2,
            questionText = "If WATER is coded as 12345 and STEAM is coded as 63478, how is MASTER coded?",
            options = listOf("876345", "873451", "876342", "873452"),
            correctOptionIndex = 0,
            explanation = "Direct letter substitution: M=8, A=7, S=6, T=3, E=4, R=5, giving 876345."
        ),
        Question(
            id = 3,
            questionText = "Find the missing number: 1, 8, 27, 64, ?",
            options = listOf("100", "121", "125", "144"),
            correctOptionIndex = 2,
            explanation = "The sequence is cubes of integers: 1³=1, 2³=8, 3³=27, 4³=64, so 5³=125."
        ),
        Question(
            id = 4,
            questionText = "Which word cannot be made from the word 'TEACHER'?",
            options = listOf("CHEATER", "REACH", "CHART", "HEAR"),
            correctOptionIndex = 2,
            explanation = "The word 'CHART' requires the letter 'T' and an extra letter not matching 'TEACHER'."
        ),
        Question(
            id = 5,
            questionText = "Car : Road :: Ship : ?",
            options = listOf("Air", "Sea", "Port", "Rail"),
            correctOptionIndex = 1,
            explanation = "A car travels on a road, and a ship sails on the sea."
        ),
        Question(
            id = 6,
            questionText = "If '+' means multiply and '×' means minus, what is the value of 5 + 4 × 3?",
            options = listOf("17", "20", "23", "35"),
            correctOptionIndex = 0,
            explanation = "Replace symbols: 5 × 4 - 3 = 20 - 3 = 17."
        ),
        Question(
            id = 7,
            questionText = "In a class of 30 students, Amit is 11th from the top. What is his rank from the bottom?",
            options = listOf("19th", "20th", "21st", "22nd"),
            correctOptionIndex = 1,
            explanation = "Rank from bottom = (Total students - Rank from top) + 1 = (30 - 11) + 1 = 20th."
        ),
        Question(
            id = 8,
            questionText = "Choose the odd word out:",
            options = listOf("Triangle", "Square", "Rectangle", "Circle"),
            correctOptionIndex = 3,
            explanation = "Triangle, Square, and Rectangle are polygons with straight edges; a Circle is curved."
        ),
        Question(
            id = 9,
            questionText = "Complete the series: B2, D4, F6, H8, ?",
            options = listOf("J10", "I9", "K11", "J12"),
            correctOptionIndex = 0,
            explanation = "Letters advance by 2 (B→D→F→H→J) and numbers increase by 2 (2, 4, 6, 8, 10)."
        ),
        Question(
            id = 10,
            questionText = "If SOUTH becomes NORTH-EAST, then NORTH will become:",
            options = listOf("South-West", "South-East", "North-West", "West"),
            correctOptionIndex = 0,
            explanation = "South rotates 135° anti-clockwise to become North-East. North rotated 135° anti-clockwise becomes South-West."
        )
    )

    val hi: List<Question> = listOf(
        Question(
            id = 1,
            questionText = "एक व्यक्ति की ओर इशारा करते हुए राहुल कहता है, 'वह मेरे दादाजी के इकलौते पुत्र का बेटा है।' राहुल का उस व्यक्ति से क्या संबंध है?",
            options = listOf("भाई", "पिता", "चाचा", "चचेरा भाई"),
            correctOptionIndex = 0,
            explanation = "दादाजी का इकलौता पुत्र राहुल का पिता है। पिता का बेटा राहुल का भाई होगा।"
        ),
        Question(
            id = 2,
            questionText = "यदि WATER को 12345 और STEAM को 63478 लिखा जाए, तो MASTER का कोड क्या होगा?",
            options = listOf("876345", "873451", "876342", "873452"),
            correctOptionIndex = 0,
            explanation = "सीधा वर्ण प्रतिस्थापन: M=8, A=7, S=6, T=3, E=4, R=5, अतः 876345।"
        ),
        Question(
            id = 3,
            questionText = "लुप्त संख्या ज्ञात कीजिए: 1, 8, 27, 64, ?",
            options = listOf("100", "121", "125", "144"),
            correctOptionIndex = 2,
            explanation = "यह पूर्ण घन संख्याओं की श्रृंखला है: 1³=1, 2³=8, 3³=27, 4³=64, अतः 5³=125।"
        ),
        Question(
            id = 4,
            questionText = "'TEACHER' शब्द के अक्षरों का उपयोग करके कौन सा शब्द नहीं बनाया जा सकता है?",
            options = listOf("CHEATER", "REACH", "CHART", "HEAR"),
            correctOptionIndex = 2,
            explanation = "'CHART' शब्द में अक्षर नहीं मिलते, अतः यह शब्द नहीं बनाया जा सकता।"
        ),
        Question(
            id = 5,
            questionText = "कार : सड़क :: जहाज : ?",
            options = listOf("हवा", "समुद्र", "बंदरगाह", "रेल"),
            correctOptionIndex = 1,
            explanation = "कार सड़क पर चलती है, और जहाज समुद्र में चलता है।"
        ),
        Question(
            id = 6,
            questionText = "यदि '+' का अर्थ गुणा और '×' का अर्थ घटाव है, तो 5 + 4 × 3 का मान क्या होगा?",
            options = listOf("17", "20", "23", "35"),
            correctOptionIndex = 0,
            explanation = "चिह्न बदलने पर: 5 × 4 - 3 = 20 - 3 = 17।"
        ),
        Question(
            id = 7,
            questionText = "30 छात्रों की एक कक्षा में अमित ऊपर से 11वें स्थान पर है। नीचे से उसका स्थान क्या होगा?",
            options = listOf("19वाँ", "20वाँ", "21वाँ", "22वाँ"),
            correctOptionIndex = 1,
            explanation = "नीचे से स्थान = (कुल छात्र - ऊपर से स्थान) + 1 = (30 - 11) + 1 = 20वाँ।"
        ),
        Question(
            id = 8,
            questionText = "इनमें से विषम शब्द का चयन कीजिए:",
            options = listOf("त्रिभुज", "वर्ग", "आयत", "वृत्त"),
            correctOptionIndex = 3,
            explanation = "त्रिभुज, वर्ग और आयत सीधी भुजाओं वाले बहुभुज हैं, जबकि वृत्त एक वक्राकार आकृति है।"
        ),
        Question(
            id = 9,
            questionText = "श्रृंखला पूर्ण कीजिए: B2, D4, F6, H8, ?",
            options = listOf("J10", "I9", "K11", "J12"),
            correctOptionIndex = 0,
            explanation = "वर्ण 2 आगे बढ़ रहे हैं (B→D→F→H→J) और संख्याएँ 2 बढ़ रही हैं, अतः J10।"
        ),
        Question(
            id = 10,
            questionText = "यदि दक्षिण उत्तर-पूर्व बन जाता है, तो उत्तर क्या बन जाएगा?",
            options = listOf("दक्षिण-पश्चिम", "दक्षिण-पूर्व", "उत्तर-पश्चिम", "पश्चिम"),
            correctOptionIndex = 0,
            explanation = "दक्षिण 135° वामावर्त घूमकर उत्तर-पूर्व बनता है। इसी प्रकार उत्तर 135° वामावर्त घूमकर दक्षिण-पश्चिम बनेगा।"
        )
    )

    fun getQuestions(language: String): List<Question> =
        if (language == "hi") hi else en
}
