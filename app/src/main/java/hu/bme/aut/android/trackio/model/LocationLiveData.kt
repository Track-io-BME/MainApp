package hu.bme.aut.android.trackio.model

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*

class LocationLiveData(context: Context) : LiveData<Location>() {
    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            Log.d("liveData", "OBSERVE")
            value = locationResult.lastLocation
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocationMonitoring() {
        val locationRequest = LocationRequest
            .Builder(
                Priority.PRIORITY_HIGH_ACCURACY,
                5000
            )
            .setMinUpdateIntervalMillis(1000)
            .build()
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback, Looper.myLooper())
        Log.d("monitoring", "start")
    }

    fun stopLocationMonitoring() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.d("monitoring", "stop")
    }
}