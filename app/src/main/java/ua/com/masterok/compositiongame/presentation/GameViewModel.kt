package ua.com.masterok.compositiongame.presentation

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ua.com.masterok.compositiongame.R
import ua.com.masterok.compositiongame.data.GameRepositoryImpl
import ua.com.masterok.compositiongame.domain.entity.GameResult
import ua.com.masterok.compositiongame.domain.entity.GameSettings
import ua.com.masterok.compositiongame.domain.entity.Level
import ua.com.masterok.compositiongame.domain.entity.Question
import ua.com.masterok.compositiongame.domain.usecases.GenerateQuestionsUseCase
import ua.com.masterok.compositiongame.domain.usecases.GetGameSettingsUseCase

class GameViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var gameSettings: GameSettings
    private lateinit var level: Level

    private val context = application

    private val repository: GameRepositoryImpl = GameRepositoryImpl

    private val generateQuestionUseCase = GenerateQuestionsUseCase(repository)
    private val getGameSettingsUseCase = GetGameSettingsUseCase(repository)

    private val _formattedTime = MutableLiveData<String>()
    val formattedTime: LiveData<String>
        get() = _formattedTime

    private var timer: CountDownTimer? = null

    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question


    private val _percentOfRightAnswer = MutableLiveData<Int>()
    val percentOfRightAnswer: LiveData<Int>
        get() = _percentOfRightAnswer

    private val _progressAnswers = MutableLiveData<String>()
    val progressAnswers: LiveData<String>
        get() = _progressAnswers

    private val _enoughCountOfRightAnswers = MutableLiveData<Boolean>()
    val enoughCountOfRightAnswers: LiveData<Boolean>
        get() = _enoughCountOfRightAnswers

    private val _enoughPercentOfRightAnswers = MutableLiveData<Boolean>()
    val enoughPercentOfRightAnswers: LiveData<Boolean>
        get() = _enoughPercentOfRightAnswers

    private val _minPercent = MutableLiveData<Int>()
    val minPercent: LiveData<Int>
        get() = _minPercent

    private val _gameResult = MutableLiveData<GameResult>()
    val gameResult: LiveData<GameResult>
        get() = _gameResult

    private var countOfRightAnswers = 0
    private var countOfQuestions = 0

    fun startGame(level: Level) {
        getGameSettings(level)
        startTimer()
        generateQuestion()
        updateProgress()
    }

    private fun getGameSettings(level: Level) {
        this.level = level
        this.gameSettings = getGameSettingsUseCase(level)
        _minPercent.value = gameSettings.minPercentOfRightAnswer
    }

    private fun startTimer() {
        val maxTimeInMill = (gameSettings.gameTimeInSeconds * MILLIS_IN_SECONDS)
        timer = object : CountDownTimer(maxTimeInMill, MILLIS_IN_SECONDS) {
            override fun onTick(millisUntilFinished: Long) {
                _formattedTime.value = formatTime(millisUntilFinished)
            }

            override fun onFinish() {
                finishGame()
            }

        }
        timer?.start()
    }

    private fun formatTime(millisUntilFinished: Long): String {
        val secs = (millisUntilFinished / MILLIS_IN_SECONDS).toInt()
        val formatted =
            "${(secs / 60).toString().padStart(2, '0')}:${(secs % 60).toString().padStart(2, '0')}"
        return formatted
    }

    private fun finishGame() {
        _gameResult.value = GameResult(
            enoughCountOfRightAnswers.value == true && enoughPercentOfRightAnswers.value == true,
            countOfRightAnswers,
            countOfQuestions,
            gameSettings
        )
    }

    private fun generateQuestion() {
        _question.value = generateQuestionUseCase(gameSettings.maxSumValue)
    }

    fun chooseAnswer(number: Int) {
        checkAnswer(number)
        updateProgress()
        generateQuestion()
    }

    private fun checkAnswer(number: Int) {
        val rightAnswer = question.value?.rightAnswer
        if (number == rightAnswer) {
            countOfRightAnswers++
        }
        countOfQuestions++
    }

    private fun updateProgress() {
        val percent = calculatePercentOfRightAnswer()
        _percentOfRightAnswer.value = percent
        _progressAnswers.value = String.format(
            context.resources.getString(R.string.progress_answers),
            countOfRightAnswers,
            gameSettings.minCountOfRightAnswer
        )
        _enoughCountOfRightAnswers.value = countOfRightAnswers >= gameSettings.minCountOfRightAnswer
        _enoughPercentOfRightAnswers.value = percent >= gameSettings.minPercentOfRightAnswer
    }

    private fun calculatePercentOfRightAnswer(): Int {
        if (countOfQuestions == 0) {
            return 0
        }
        return ((countOfRightAnswers / countOfQuestions.toDouble()) * 100).toInt()
    }

    override fun onCleared() {
        super.onCleared()
        timer?.cancel()
    }


    companion object {
        private const val MILLIS_IN_SECONDS = 1000L
    }

}