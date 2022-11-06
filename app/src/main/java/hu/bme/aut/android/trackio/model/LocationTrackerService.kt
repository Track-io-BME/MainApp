package hu.bme.aut.android.trackio.model

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData


class LocationTrackerService : LifecycleService() {

    private lateinit var _locationLiveData : LocationLiveData
    val locationLiveData : LiveData<Location>
        get() = _locationLiveData
    private val binder = LocationTrackerBinder()
    private var tracking = false

    inner class LocationTrackerBinder : Binder() {
        val service: LocationTrackerService
            get() = this@LocationTrackerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        _locationLiveData = LocationLiveData(this)

        locationLiveData.observe(this) {
            val location = _locationLiveData.value as Location
            Log.d("location", "\tlat:${location.latitude}\tlong:${location.longitude}")
        }

        return START_STICKY_COMPATIBILITY
    }

//    override fun onDestroy() {
//        _locationLiveData.stopLocationMonitoring()
//        super.onDestroy()
//    }

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        return binder
    }

    fun startStop() {
        tracking = if (tracking) {
            _locationLiveData.stopLocationMonitoring()
            false
        } else {
            _locationLiveData.startLocationMonitoring()
            true
        }
    }
}