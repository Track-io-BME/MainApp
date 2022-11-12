package hu.bme.aut.android.trackio.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var timer : Timer
    private val _time = MutableLiveData(0)
    val time : LiveData<Int> = _time
    private var _timerRunning = MutableLiveData(false)
    val timerRunning : LiveData<Boolean> = _timerRunning

    var distance = 0.0F

    fun startStop() {
        if (_timerRunning.value == true) {
            stopTimer()
        }
        else {
            startTimer()
        }
    }

    private fun startTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    _time.postValue(_time.value?.plus(1))
                }
            },
            0,
            1000
        )
        _timerRunning.value = true
    }

    private fun stopTimer() {
        timer.cancel()
        timer.purge()
        _timerRunning.value = false
    }

    override fun onCleared() {
        super.onCleared()
    }
}