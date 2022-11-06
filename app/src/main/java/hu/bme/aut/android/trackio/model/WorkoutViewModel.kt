package hu.bme.aut.android.trackio.model

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import java.util.*


class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private lateinit var timer : Timer
    private val _time = MutableLiveData(0)
    val time : LiveData<Int>
        get() = _time
    private var _timerRunning = MutableLiveData(false)
    val timerRunning : LiveData<Boolean>
        get() = _timerRunning

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


//    private lateinit var mService: LocationTrackerService
//    private var mBound: Boolean = false
//
//    /** Defines callbacks for service binding, passed to bindService()  */
//    private val connection = object : ServiceConnection {
//
//        override fun onServiceConnected(className: ComponentName, service: IBinder) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            val binder = service as LocationTrackerService.LocationTrackerBinder
//            mService = binder.service
//            mBound = true
//        }
//
//        override fun onServiceDisconnected(arg0: ComponentName) {
//            mBound = false
//        }
//    }
//
//
//    private fun startTracking() {
//        Intent(getApplication(), LocationTrackerService::class.java).also { intent ->
//            getApplication<Application>().bindService(intent, connection, Context.BIND_AUTO_CREATE)
//        }
//    }
//
//    override fun onCleared() {
//        getApplication<Application>().unbindService(connection)
//        mBound = false
//        super.onCleared()
//    }
}