package hu.bme.aut.android.trackio.model

import android.content.Intent
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.util.Log
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

    override fun onBind(intent: Intent): IBinder {
        super.onBind(intent)
        Log.d("service", "BIND")

        _locationLiveData = LocationLiveData(this)
        _locationLiveData.observe(this) {
            Log.d("service", "OBSERVE")
            if (lastLocation != null && tracking) {
                _distance.value = _distance.value?.plus(it.distanceTo(lastLocation) / 1000)
            }
            lastLocation = it
        }
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("service", "UNBIND")
        if (tracking) {
            _locationLiveData.stopLocationMonitoring()
            tracking = false
        }
        return super.onUnbind(intent)
    }

    override fun onDestroy() {
        Log.d("service", tracking.toString())
        if (tracking) {
            _locationLiveData.stopLocationMonitoring()
            tracking = false
        }
        Log.d("service", "DESTROY")
        super.onDestroy()
    }

    override fun onRebind(intent: Intent?) {
        Log.d("service", "REBIND")
        super.onRebind(intent)
    }

    fun startStop() {
        tracking = if (tracking) {
            _locationLiveData.stopLocationMonitoring()
            lastLocation = null
            Log.d("service", "STOP")
            false
        } else {
            _locationLiveData.startLocationMonitoring()
            Log.d("service", "START")
            true
        }
    }
}