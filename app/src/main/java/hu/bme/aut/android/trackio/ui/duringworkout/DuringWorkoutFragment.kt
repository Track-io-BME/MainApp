package hu.bme.aut.android.trackio.ui.duringworkout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDuringWorkoutBinding
import hu.bme.aut.android.trackio.model.LocationTrackerService
import hu.bme.aut.android.trackio.model.WorkoutViewModel

class DuringWorkoutFragment : Fragment() {
    private lateinit var binding : FragmentDuringWorkoutBinding
    private val viewModel: WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDuringWorkoutBinding.inflate(inflater, container, false)
//        viewModel = ViewModelProvider(requireActivity())[WorkoutViewModel::class.java]
        viewModel.time.observe(viewLifecycleOwner) {
            val seconds = it % 60
            var minutes = it / 60
            val hours = minutes / 60
            minutes %= 60
            binding.tvTimeValue.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
        viewModel.timerRunning.observe(viewLifecycleOwner) {
            binding.btnPlayPause.isActivated = it
        }
        distance.observe(viewLifecycleOwner) {
            binding.tvWorkoutDistance.text = String.format("%.2f", it)

        }
        Intent(requireContext(), LocationTrackerService::class.java).also {
            requireActivity().startService(it)
            requireActivity().bindService(it, connection, Context.BIND_AUTO_CREATE)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDuringToMap.setOnClickListener {
            findNavController().navigate(R.id.action_duringWorkoutFragment_to_workoutMapFragment)
       }
        binding.btnPlayPause.setOnClickListener {
            mService.startStop()
            viewModel.startStop()
        }
    }


    private lateinit var mService: LocationTrackerService
    private var mBound: Boolean = false
    private lateinit var lastLocation: Location
    private var distance = MutableLiveData(0.0F)

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocationTrackerService.LocationTrackerBinder
            mService = binder.service
            mService.locationLiveData.observe(viewLifecycleOwner) {
                if (::lastLocation.isInitialized)
                    distance.value = distance.value?.plus(it.distanceTo(lastLocation) / 1000)
                lastLocation = it
            }
            mBound = true
            Log.d("bound", "BOUND")
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
            Log.d("unbound", "UNBOUND")
        }
    }
}