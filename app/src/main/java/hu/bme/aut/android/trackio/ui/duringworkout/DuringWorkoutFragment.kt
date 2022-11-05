package hu.bme.aut.android.trackio.ui.duringworkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDuringWorkoutBinding
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDuringToMap.setOnClickListener {
            findNavController().navigate(R.id.action_duringWorkoutFragment_to_workoutMapFragment)
        }
        binding.btnPlayPause.setOnClickListener {
            viewModel.startStop()
        }
    }
}