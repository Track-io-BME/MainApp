package hu.bme.aut.android.trackio.ui.workoutmenu

import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMenuBinding
import hu.bme.aut.android.trackio.model.WorkoutViewModel

class WorkoutMenuFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutMenuBinding
    private val viewModel : WorkoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutMenuBinding.inflate(inflater, container, false)
        when (viewModel.currentWorkoutType) {
            WorkoutViewModel.WorkoutType.WALKING ->
                binding.tvWalkingMode.text = underlineText(binding.tvWalkingMode.text.toString())
            WorkoutViewModel.WorkoutType.RUNNING ->
                binding.tvRunningMode.text = underlineText(binding.tvRunningMode.text.toString())
            WorkoutViewModel.WorkoutType.CYCLING ->
                binding.tvCyclingMode.text = underlineText(binding.tvCyclingMode.text.toString())
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvWalkingMode.setOnClickListener {
            binding.tvWalkingMode.text = underlineText(binding.tvWalkingMode.text.toString())
            binding.tvRunningMode.text = binding.tvRunningMode.text.toString()
            binding.tvCyclingMode.text = binding.tvCyclingMode.text.toString()
            viewModel.currentWorkoutType = WorkoutViewModel.WorkoutType.WALKING
        }
        binding.tvRunningMode.setOnClickListener {
            binding.tvWalkingMode.text = binding.tvWalkingMode.text.toString()
            binding.tvRunningMode.text = underlineText(binding.tvRunningMode.text.toString())
            binding.tvCyclingMode.text = binding.tvCyclingMode.text.toString()
            viewModel.currentWorkoutType = WorkoutViewModel.WorkoutType.RUNNING
        }
        binding.tvCyclingMode.setOnClickListener {
            binding.tvWalkingMode.text = binding.tvWalkingMode.text.toString()
            binding.tvRunningMode.text = binding.tvRunningMode.text.toString()
            binding.tvCyclingMode.text = underlineText(binding.tvCyclingMode.text.toString())
            viewModel.currentWorkoutType = WorkoutViewModel.WorkoutType.CYCLING
        }

        binding.btnStartWorkout.setOnClickListener {
            findNavController().navigate(R.id.action_workoutMenuFragment_to_duringWorkoutFragment)
        }

        binding.tbNavigation.selectedItemId = R.id.workout_menu
        binding.tbNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    findNavController().navigate(R.id.action_workoutMenuFragment_to_homeMenuFragment)
                    true
                }
                R.id.profile_menu -> {
                    findNavController().navigate(R.id.action_workoutMenuFragment_to_profileMenuFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun underlineText(string: String) : SpannableString {
        val text = SpannableString(string)
        text.setSpan(UnderlineSpan(), 0, text.length, 0)
        return text
    }
}