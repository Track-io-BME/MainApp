package hu.bme.aut.android.trackio.model

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import java.util.*

class LocationTrackerService : LifecycleService(), java.io.Serializable {
    private val binder = LocationTrackerBinder()
    private var workoutType = ActiveChallenge.SportType.WALKING

    private var lastLocation: Location? = null
    private lateinit var _locationLiveData: LocationLiveData
    val locationLiveData: LiveData<Location>
        get() = _locationLiveData

    private val _distance = MutableLiveData(0.0F)
    val distance: LiveData<Float>
        get() = _distance

    private lateinit var timer: Timer
    private val _time = MutableLiveData(0L)
    val time: LiveData<Long> = _time

    private val _tracking = MutableLiveData(false)
    val tracking: LiveData<Boolean> = _tracking

    inner class LocationTrackerBinder : Binder() {
        val service: LocationTrackerService
            get() = this@LocationTrackerService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        _locationLiveData = LocationLiveData(this)
        _locationLiveData.observe(this) {
            if (lastLocation != null && _tracking.value == true) {
                _distance.value = _distance.value?.plus(it.distanceTo(lastLocation) / 1000)
            }
            lastLocation = it
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        if (_tracking.value == true) stop()
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        if (_tracking.value == true) stop()
        super.onDestroy()
    }

    fun startStop(workoutType: ActiveChallenge.SportType) {
        this.workoutType = workoutType
        _tracking.value = if (_tracking.value == true) {
            stop()
            false
        } else {
            start()
            true
        }
    }

    private fun start() {
        startForeground(
            WorkoutNotificationHelper.WORKOUT_NOTIFICATION_ID,
            WorkoutNotificationHelper.getWorkoutNotification(workoutType)
        )
        _locationLiveData.startLocationMonitoring()
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
        _tracking.value = true
    }

    private fun stop() {
        stopForeground(STOP_FOREGROUND_REMOVE)
        _locationLiveData.stopLocationMonitoring()
        timer.cancel()
        timer.purge()
        lastLocation = null
        _tracking.value = false
    }

    fun clear() {
        if (_tracking.value == true) stop()
        _time.value = 0
        _distance.value = 0.0F
        _tracking.value = false
        lastLocation = null
    }
}