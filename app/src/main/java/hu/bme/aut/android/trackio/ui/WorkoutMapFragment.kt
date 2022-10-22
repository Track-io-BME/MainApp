package hu.bme.aut.android.trackio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMapBinding

class WorkoutMapFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutMapBinding
    private val callback = OnMapReadyCallback { googleMap ->
        val budapest = LatLng(47.473194, 19.06025)
        googleMap.addMarker(MarkerOptions().position(budapest).title("Marker in Budapest"))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(budapest))
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(budapest,15.0f))
//        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(budapest, 15.0f))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(budapest, 15.0f), 2000, null)
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.workoutMapFragment) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}