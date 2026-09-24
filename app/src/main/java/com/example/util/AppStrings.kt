package com.example.util

/**
 * Centralized bilingual strings dictionary for English ("en") and Hindi ("hi").
 * Provides strict separation without mixed Hinglish or slash characters.
 */
object AppStrings {
    fun appTitle(lang: String): String = "Quiz4Exam"

    fun selectTitle(lang: String): String =
        if (lang == "hi") "श्रेणी चुनें" else "Select One"

    fun chooseText(lang: String): String =
        if (lang == "hi") "अभ्यास के लिए अपनी श्रेणी चुनें" else "Choose your practice category"

    fun centralText(lang: String): String =
        if (lang == "hi") "केंद्रीय प्रतियोगी परीक्षा" else "Central Competitive Exam"

    fun stateText(lang: String): String =
        if (lang == "hi") "राज्य प्रतियोगी परीक्षा" else "State Competitive Exam"

    fun reasoningText(lang: String): String = "Brain-Matrix"

    fun reasoningTitle(lang: String): String =
        if (lang == "hi") "Brain-Matrix स्तर" else "Brain-Matrix Levels"

    fun selectLevelSubtitle(lang: String): String =
        if (lang == "hi")
            "अगला स्तर खोलने के लिए कम से कम 5 उत्तर सही दें (5/10)"
        else
            "Score at least 5 out of 10 to unlock the next level"

    fun back(lang: String): String =
        if (lang == "hi") "वापस" else "Back"

    fun levelsText(lang: String): String =
        if (lang == "hi") "स्तरों पर जाएं" else "Back to Levels"

    fun timeLeft(lang: String): String =
        if (lang == "hi") "समय शेष" else "Time Left"

    fun timeUpAlert(lang: String): String =
        if (lang == "hi") "समय समाप्त हो गया है! क्विज़ सबमिट की जा रही है।" else "Time is up! Submitting quiz."

    fun questionProgress(lang: String, current: Int, total: Int): String =
        if (lang == "hi") "प्रश्न $current/$total" else "Question $current/$total"

    fun nextButtonLabel(lang: String): String =
        if (lang == "hi") "आगे →" else "Next →"

    fun prevButtonLabel(lang: String): String =
        if (lang == "hi") "← पिछला" else "← Prev"

    fun skip(lang: String): String =
        if (lang == "hi") "छोड़ें" else "Skip"

    fun submit(lang: String): String =
        if (lang == "hi") "सबमिट करें" else "Submit"

    fun selectOptionAlert(lang: String): String =
        if (lang == "hi") "कृपया पहले एक विकल्प चुनें।" else "Please select an option first."

    fun completedTitle(lang: String): String =
        if (lang == "hi") "क्विज़ पूर्ण हुई" else "Quiz Completed"

    fun detailedReviewTitle(lang: String): String =
        if (lang == "hi") "विस्तृत समीक्षा" else "Detailed Review"

    // Localized terms required strictly:
    fun yourAnswerLabel(lang: String): String =
        if (lang == "hi") "आपका उत्तर" else "Your Answer"

    fun correctAnswerLabel(lang: String): String =
        if (lang == "hi") "सही उत्तर" else "Correct Answer"

    fun explanationLabel(lang: String): String =
        if (lang == "hi") "व्याख्या" else "Explanation"

    fun skippedNotAttempted(lang: String): String =
        if (lang == "hi") "छोड़ा गया (उत्तर नहीं दिया)" else "Skipped (Not attempted)"

    fun badgeCorrect(lang: String): String =
        if (lang == "hi") "सही" else "Correct"

    fun badgeWrong(lang: String): String =
        if (lang == "hi") "गलत" else "Wrong"

    fun badgeSkipped(lang: String): String =
        if (lang == "hi") "छोड़ा गया" else "Skipped"

    fun yourScore(lang: String, score: Int, total: Int): String =
        if (lang == "hi") "आपका स्कोर: $score / $total" else "Your Score: $score / $total"

    fun pointsEarnedSummary(lang: String, points: Int): String =
        if (lang == "hi") "+$points अंक प्राप्त हुए (+3 अंक प्रति सही उत्तर)" else "+$points Points Earned (+3 pts per correct)"

    fun statusPassedLabel(lang: String): String =
        if (lang == "hi") "स्थिति: उत्तीर्ण 🎉" else "Status: PASSED 🎉"

    fun statusFailedLabel(lang: String): String =
        if (lang == "hi") "स्थिति: अनुत्तीर्ण" else "Status: FAILED"

    fun congratulationsTitle(lang: String): String =
        if (lang == "hi") "बधाई हो! 🎉" else "Congratulations! 🎉"

    fun levelCompletedSuccessfully(lang: String, level: Int): String =
        if (lang == "hi") "स्तर $level सफलतापूर्वक उत्तीर्ण हुआ!" else "Level $level Passed Successfully!"

    fun playNextLevel(lang: String): String =
        if (lang == "hi") "अगला स्तर खेलें →" else "Play Next Level →"

    fun reviewAnswers(lang: String): String =
        if (lang == "hi") "उत्तरों की समीक्षा करें" else "Detailed Review"

    fun levelIncompleteTitle(lang: String): String =
        if (lang == "hi") "स्तर अपूर्ण रहा" else "Level Incomplete"

    fun levelIncompleteDesc(lang: String, score: Int, total: Int, points: Int): String =
        if (lang == "hi")
            "अगला स्तर खोलने के लिए 10 में से कम से कम 5 प्रश्नों के सही उत्तर आवश्यक हैं। आपका स्कोर $score/$total (+$points अंक) रहा।"
        else
            "A minimum score of 5 correct answers out of 10 is required to unlock the next level. You scored $score out of $total (+$points Points)."

    fun retry(lang: String): String =
        if (lang == "hi") "पुनः प्रयास करें" else "Try Again"

    fun settingsTitle(lang: String): String =
        if (lang == "hi") "सेटिंग्स" else "Settings"

    fun languageTitle(lang: String): String =
        if (lang == "hi") "भाषा चुनें" else "Select Language"

    fun comingSoonMessage(lang: String): String =
        if (lang == "hi") "यह अनुभाग जल्द ही उपलब्ध होगा।" else "This category will be available soon."

    fun pointsEarnedLabel(lang: String): String =
        if (lang == "hi") "प्राप्त अंक" else "Points Earned"

    fun totalPointsLabel(lang: String): String =
        if (lang == "hi") "कुल संचित अंक" else "Total Points"

    fun levelUnlockedSuccess(lang: String, level: Int): String =
        if (lang == "hi") "शानदार! स्तर $level अब खुल गया है!" else "Great job! Level $level is now Unlocked!"

    fun soundEffectsTitle(lang: String): String =
        if (lang == "hi") "ध्वनि प्रभाव" else "Sound Effects"

    fun soundOn(lang: String): String =
        if (lang == "hi") "चालू" else "ON"

    fun soundOff(lang: String): String =
        if (lang == "hi") "बंद" else "OFF"

    // Report Question Feature
    fun reportQuestion(lang: String): String =
        if (lang == "hi") "प्रश्न रिपोर्ट करें" else "Report Question"

    fun reportIssueBtn(lang: String): String =
        if (lang == "hi") "समस्या रिपोर्ट करें" else "Report Issue"

    fun reportIssueTitle(lang: String): String =
        if (lang == "hi") "प्रश्न की समस्या रिपोर्ट करें" else "Report Question Issue"

    fun reportOptionWrongAnswer(lang: String): String =
        if (lang == "hi") "गलत उत्तर या विकल्प" else "Wrong Answer / Options"

    fun reportOptionTypo(lang: String): String =
        if (lang == "hi") "टाइपिंग या वर्तनी की त्रुटि" else "Typographical / Spelling Error"

    fun reportOptionIncorrectExplanation(lang: String): String =
        if (lang == "hi") "गलत व्याख्या" else "Incorrect Explanation"

    fun reportOptionOther(lang: String): String =
        if (lang == "hi") "अन्य" else "Other"

    fun reportCommentPlaceholder(lang: String): String =
        if (lang == "hi") "कृपया समस्या का विवरण लिखें..." else "Please describe the issue in detail..."

    fun reportCancel(lang: String): String =
        if (lang == "hi") "रद्द करें" else "Cancel"

    fun reportSend(lang: String): String =
        if (lang == "hi") "रिपोर्ट भेजें" else "Send Report"

    // Language screen specifics:
    fun brandSubtitle(lang: String): String =
        if (lang == "hi") "प्रतियोगी परीक्षाओं में सफलता की तैयारी" else "Master your competitive exams"

    fun preferredLanguageTitle(lang: String): String =
        if (lang == "hi") "अपनी पसंदीदा भाषा चुनें" else "Select Your Preferred Language"

    fun changeLanguageLaterNote(lang: String): String =
        if (lang == "hi") "आप इसे बाद में कभी भी सेटिंग्स में बदल सकते हैं" else "You can change this anytime later in Settings"

    fun langHeading(lang: String): String =
        if (lang == "hi") "भाषा चुनें" else "Select Language"

    fun langSubtitle(lang: String): String =
        if (lang == "hi") "अपनी पसंदीदा भाषा का चयन करें" else "Choose your preferred language"

    fun langButton(lang: String): String =
        if (lang == "hi") "आगे बढ़ें →" else "Continue →"

    fun termsAndConditions(lang: String): String =
        if (lang == "hi") "नियम और शर्तें" else "Terms and Conditions"

    fun privacyPolicy(lang: String): String =
        if (lang == "hi") "गोपनीयता नीति" else "Privacy Policy"
}
