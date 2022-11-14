package hu.bme.aut.android.trackio.model

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class LocationTrackerService : LifecycleService() {
    private val binder = LocationTrackerBinder()
    private var tracking = false
    private var lastLocation: Location? = null
    private lateinit var _locationLiveData : LocationLiveData
    val locationLiveData : LiveData<Location>
        get() = _locationLiveData
    private var _distance = MutableLiveData(0.0F)
    val distance : LiveData<Float>
        get() = _distance

    inner class LocationTrackerBinder : Binder() {
        val service: LocationTrackerService
            get() = this@LocationTrackerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        _locationLiveData = LocationLiveData(this)
        _locationLiveData.observe(this) {
            if (lastLocation != null && tracking) {
                _distance.value = _distance.value?.plus(it.distanceTo(lastLocation) / 1000)
            }
            lastLocation = it
        }
        return START_STICKY_COMPATIBILITY
    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    fun startStop() {
        tracking = if (tracking) {
            _locationLiveData.stopLocationMonitoring()
            lastLocation = null
            false
        } else {
            _locationLiveData.startLocationMonitoring()
            true
        }
    }
}