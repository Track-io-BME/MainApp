package hu.bme.aut.android.trackio.model

import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.LiveData


class LocationTrackerService() : LifecycleService() {
    private lateinit var _locationLiveData : LocationLiveData
    val locationLiveData : LiveData<Location>
        get() = _locationLiveData
//    private val binder = LocationTrackerBinder()
//
//    inner class LocationTrackerBinder() : Binder() {
//        val service: LocationTrackerService
//            get() = this@LocationTrackerService
//    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        _locationLiveData = LocationLiveData(this)

        locationLiveData.observe(this) {
            val location = _locationLiveData.value as Location
            Log.d("location", "\tlat:${location.latitude}\tlong:${location.longitude}")
        }
        _locationLiveData.startLocationMonitoring()

        return START_STICKY_COMPATIBILITY
    }

    override fun onDestroy() {
        _locationLiveData.stopLocationMonitoring()
        super.onDestroy()
    }

//    override fun onBind(intent: Intent?): IBinder? {
//        return binder
//    }
}