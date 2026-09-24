package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.DetailedReviewScreen
import com.example.ui.screens.FirstLanguageScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.QuizScreen
import com.example.ui.screens.ReasoningLevelsScreen
import com.example.ui.screens.ResultScreen
import com.example.ui.screens.SelectStateScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.OffWhiteBackground
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.ScreenState

class MainActivity : ComponentActivity() {
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = OffWhiteBackground
                ) {
                    QuizAppContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun QuizAppContent(viewModel: QuizViewModel) {
    val context = LocalContext.current
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val currentLanguage by viewModel.currentLanguage.collectAsStateWithLifecycle()
    val levels by viewModel.levels.collectAsStateWithLifecycle()
    val currentStreak by viewModel.currentStreak.collectAsStateWithLifecycle()
    val todayPoints by viewModel.todayPoints.collectAsStateWithLifecycle()
    val remainingTimeSeconds by viewModel.remainingTimeSeconds.collectAsStateWithLifecycle()
    val soundEnabled by viewModel.soundEnabled.collectAsStateWithLifecycle()
    val dailyTarget = viewModel.dailyTarget

    // Toast listener for alerts like time up or select option
    LaunchedEffect(Unit) {
        viewModel.toastEvent.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    when (val state = screenState) {
        is ScreenState.FirstLanguage -> {
            FirstLanguageScreen(
                onSelectLanguage = { lang ->
                    viewModel.selectFirstLanguage(lang)
                }
            )
        }

        is ScreenState.Home -> {
            HomeScreen(
                currentLanguage = currentLanguage,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onOpenSettings = { viewModel.openSettings() },
                onSelectCentralExam = { /* Handled with coming soon */ },
                onSelectStateExam = { viewModel.openSelectState() },
                onSelectReasoning = { viewModel.openReasoning() }
            )
        }

        is ScreenState.SelectState -> {
            BackHandler {
                viewModel.goHome()
            }
            SelectStateScreen(
                currentLanguage = currentLanguage,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBack = { viewModel.goHome() },
                onOpenSettings = { viewModel.openSettings() },
                onSelectState = { stateName ->
                    Toast.makeText(context, "$stateName state syllabus & mock tests ready!", Toast.LENGTH_SHORT).show()
                }
            )
        }

        is ScreenState.ReasoningLevels -> {
            BackHandler {
                viewModel.goHome()
            }
            ReasoningLevelsScreen(
                currentLanguage = currentLanguage,
                levels = levels,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onSelectLevel = { levelNum ->
                    viewModel.startQuiz(levelNum)
                },
                onBackToHome = { viewModel.goHome() },
                onOpenSettings = { viewModel.openSettings() }
            )
        }

        is ScreenState.Quiz -> {
            BackHandler {
                viewModel.openReasoning()
            }
            val question = state.questions.getOrNull(state.currentQuestionIndex)
            if (question != null) {
                QuizScreen(
                    currentLanguage = currentLanguage,
                    levelNumber = state.levelNumber,
                    question = question,
                    currentIndex = state.currentQuestionIndex,
                    totalCount = state.questions.size,
                    remainingSeconds = remainingTimeSeconds,
                    selectedAnswerIndex = state.userAnswers[state.currentQuestionIndex],
                    currentStreak = currentStreak,
                    todayPoints = todayPoints,
                    dailyTarget = dailyTarget,
                    onSelectAnswer = { index ->
                        viewModel.selectAnswer(index)
                    },
                    onNextQuestion = {
                        viewModel.nextQuestion()
                    },
                    onSkipQuestion = {
                        viewModel.skipQuestion()
                    },
                    onPreviousQuestion = {
                        viewModel.previousQuestion()
                    },
                    onBackToLevels = {
                        viewModel.openReasoning()
                    },
                    onOpenSettings = {
                        viewModel.openSettings()
                    }
                )
            }
        }

        is ScreenState.Result -> {
            BackHandler {
                viewModel.openReasoning()
            }
            ResultScreen(
                currentLanguage = currentLanguage,
                levelNumber = state.levelNumber,
                score = state.score,
                totalQuestions = state.totalQuestions,
                pointsEarned = state.pointsEarned,
                totalAccumulatedPoints = state.totalAccumulatedPoints,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                isNextLevelUnlocked = state.isNextLevelUnlocked,
                isPassed = state.isPassed,
                hasNextLevel = state.hasNextLevel,
                questions = state.questions,
                userAnswers = state.userAnswers,
                onNextLevel = {
                    viewModel.startNextLevel(state.levelNumber)
                },
                onRetry = {
                    viewModel.retryQuiz()
                },
                onOpenDetailedReview = {
                    viewModel.openDetailedReview()
                },
                onBackToLevels = {
                    viewModel.openReasoning()
                },
                onOpenSettings = {
                    viewModel.openSettings()
                }
            )
        }

        is ScreenState.DetailedReview -> {
            BackHandler {
                viewModel.backFromDetailedReview()
            }
            DetailedReviewScreen(
                currentLanguage = currentLanguage,
                levelNumber = state.levelNumber,
                questions = state.questions,
                userAnswers = state.userAnswers,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                onBackToResult = {
                    viewModel.backFromDetailedReview()
                },
                onOpenSettings = {
                    viewModel.openSettings()
                }
            )
        }

        is ScreenState.Settings -> {
            BackHandler {
                viewModel.goHome()
            }
            SettingsScreen(
                currentLanguage = currentLanguage,
                currentStreak = currentStreak,
                todayPoints = todayPoints,
                dailyTarget = dailyTarget,
                soundEnabled = soundEnabled,
                onSoundToggle = { enabled ->
                    viewModel.setSoundEnabled(enabled)
                },
                onLanguageChange = { lang ->
                    viewModel.changeLanguage(lang)
                },
                onBack = {
                    viewModel.goHome()
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(text = "Hello $name!", modifier = modifier)
}

