package hu.bme.aut.android.trackio.ui

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMapBinding
import hu.bme.aut.android.trackio.model.LocationTrackerService

class WorkoutMapFragment : Fragment() {
    companion object {
        private const val ACCESS_LOCATION_REQUEST_CODE = 3003//Todo
        const val ARG_LOCATION_TRACKER_SERVICE = "location_tracker_service"
    }

    private lateinit var binding: FragmentWorkoutMapBinding
    private lateinit var googleMap: GoogleMap
    private var locationTrackerService: LocationTrackerService? = null

    @SuppressLint("MissingPermission")// TODO PERMISSION!!!
    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
        locationTrackerService?.locationLiveData?.observe(viewLifecycleOwner) {
            moveToUserLocation(it)
        }

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            this.googleMap.isMyLocationEnabled = true
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Give us location permission")
                    .setMessage("Tracking will not work without it")
                    .setPositiveButton(getString(R.string.yes)) { _, _ ->
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                            ACCESS_LOCATION_REQUEST_CODE
                        )
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .show()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    Array(1) { Manifest.permission.ACCESS_FINE_LOCATION },
                    ACCESS_LOCATION_REQUEST_CODE
                )
//                findNavController().navigateUp()
            }
        }
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
        locationTrackerService = arguments?.get(ARG_LOCATION_TRACKER_SERVICE) as LocationTrackerService
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.workoutMapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        locationTrackerService = arguments?.getSerializable(ARG_LOCATION_TRACKER_SERVICE) as LocationTrackerService
    }
}