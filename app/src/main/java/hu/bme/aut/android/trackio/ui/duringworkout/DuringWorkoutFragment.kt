package hu.bme.aut.android.trackio.ui.duringworkout

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDuringWorkoutBinding
import hu.bme.aut.android.trackio.model.LocationTrackerService
import hu.bme.aut.android.trackio.viewmodel.WorkoutViewModel

class DuringWorkoutFragment : Fragment() {
    private lateinit var binding : FragmentDuringWorkoutBinding
    private val viewModel : WorkoutViewModel by activityViewModels()

    private var mBound: Boolean = false
    private lateinit var mService: LocationTrackerService
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as LocationTrackerService.LocationTrackerBinder
            mService = binder.service
            if (viewModel.clearNeeded) {
                mService.clear()
                viewModel.clearNeeded = false
            }
            observeService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDuringWorkoutBinding.inflate(inflater, container, false)
        when (viewModel.currentWorkoutType) {
            WorkoutViewModel.WorkoutType.WALKING -> binding.tvWorkoutType.text = getString(R.string.walking)
            WorkoutViewModel.WorkoutType.RUNNING -> binding.tvWorkoutType.text = getString(R.string.running)
            WorkoutViewModel.WorkoutType.CYCLING -> binding.tvWorkoutType.text = getString(R.string.cycling)
        }
        if (!mBound) {
            Intent(requireContext(), LocationTrackerService::class.java).also {
                requireActivity().bindService(it, connection, Context.BIND_AUTO_CREATE)
            }
        }
        else {
            observeService()
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
        }
    }

    private fun observeService() {
        mService.distance.observe(viewLifecycleOwner) {
            Log.d("fragment", "OBSERVE")
            viewModel.distance = it
            binding.tvWorkoutDistance.text = String.format("%.2f", it)
        }

        mService.time.observe(viewLifecycleOwner) {
            val seconds = it % 60
            var minutes = it / 60
            val hours = minutes / 60
            minutes %= 60
            viewModel.time = it
            binding.tvTimeValue.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

        mService.tracking.observe(viewLifecycleOwner) {
            binding.btnPlayPause.isActivated = it
        }
    }
}