package hu.bme.aut.android.trackio.model

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*

class LocationTrackerService : LifecycleService() {
    private val binder = LocationTrackerBinder()
    private var lastLocation: Location? = null
    private lateinit var _locationLiveData : LocationLiveData
    val locationLiveData : LiveData<Location>
        get() = _locationLiveData
    private var _distance = MutableLiveData(0.0F)
    val distance : LiveData<Float>
        get() = _distance
    private lateinit var timer: Timer
    private val _time = MutableLiveData(0L)
    val time: LiveData<Long> = _time
    private var _tracking = MutableLiveData(false)
    val tracking: LiveData<Boolean> = _tracking

    inner class LocationTrackerBinder : Binder() {
        val service: LocationTrackerService
            get() = this@LocationTrackerService
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        Log.d("service", "BIND")

        _locationLiveData = LocationLiveData(this)
        _locationLiveData.observe(this) {
            Log.d("service", "OBSERVE")
            if (lastLocation != null && _tracking.value == true) {
                _distance.value = _distance.value?.plus(it.distanceTo(lastLocation) / 1000)
            }
            lastLocation = it
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("service", "UNBIND")
        if (_tracking.value == true) {
            _locationLiveData.stopLocationMonitoring()
            _tracking.value = false
        }
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("service", tracking.toString())
        if (_tracking.value == true) {
            _locationLiveData.stopLocationMonitoring()
            _tracking.value = false
        }
        Log.d("service", "DESTROY")
        super.onDestroy()
    }

    override fun onRebind(intent: Intent?) {
        Log.d("service", "REBIND")
        super.onRebind(intent)
    }

    fun startStop() {
        _tracking.value = if (_tracking.value == true) {
            _locationLiveData.stopLocationMonitoring()
            lastLocation = null
            stopTimer()
            Log.d("service", "STOP")
            false
        } else {
            _locationLiveData.startLocationMonitoring()
            startTimer()
            Log.d("service", "START")
            true
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
        _tracking.value = true
    }

    private fun stopTimer() {
        timer.cancel()
        timer.purge()
        _tracking.value = false
    }

    fun clear() {
        if (_tracking.value == true) {
            stopTimer()
            _locationLiveData.stopLocationMonitoring()
        }
        _time.value = 0
        _distance.value = 0.0F
        _tracking.value = false
        lastLocation = null
    }
}