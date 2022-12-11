package ua.com.masterok.compositiongame.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.com.masterok.compositiongame.data.GameRepositoryImpl
import ua.com.masterok.compositiongame.domain.entity.GameSettings
import ua.com.masterok.compositiongame.domain.entity.Level

class GameViewModel : ViewModel() {

    private val repository: GameRepositoryImpl = GameRepositoryImpl

    private var _timer = MutableLiveData<Int>()
    val timer: LiveData<Int>
    get() = _timer

    private val _isTimerFinished = MutableLiveData<Boolean>()
    val isTimerFinished: LiveData<Boolean>
    get() = _isTimerFinished


    fun countDownTimer(level: Level) {
        val gameMode = getGameSettings(level)
        val maxTimeInMill = (gameMode.gameTimeInSeconds * 1000).toLong()
        val timer = object : CountDownTimer(maxTimeInMill, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timer.value = (millisUntilFinished/1000).toInt()
            }

            override fun onFinish() {
                _isTimerFinished.value = true
            }

        }
        timer.start()
    }

    private fun getGameSettings(level: Level): GameSettings {
         return repository.getGameSettings(level)
    }

}