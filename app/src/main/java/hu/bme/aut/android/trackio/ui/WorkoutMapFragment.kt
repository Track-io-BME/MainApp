package hu.bme.aut.android.trackio.ui

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMapBinding
import hu.bme.aut.android.trackio.model.LocationTrackerService

class WorkoutMapFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutMapBinding
    private lateinit var googleMap: GoogleMap
    private var locationTrackerService: LocationTrackerService? = null

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        locationTrackerService?.locationLiveData?.observe(viewLifecycleOwner) {
            moveToUserLocation(it)
        }
        this.googleMap.isMyLocationEnabled = true
    }

    private fun moveToUserLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.workoutMapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        locationTrackerService =
            arguments?.let { WorkoutMapFragmentArgs.fromBundle(it).locationTrackerService }
    }
}