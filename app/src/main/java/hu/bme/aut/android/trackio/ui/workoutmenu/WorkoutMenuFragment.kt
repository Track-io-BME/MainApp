package hu.bme.aut.android.trackio.ui.workoutmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMenuBinding

class WorkoutMenuFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.btnWorkoutToHome.setOnClickListener {
//            findNavController().navigate(R.id.action_workoutMenuFragment_to_homeMenuFragment)
//        }
//        binding.btnWorkoutToDuring.setOnClickListener {
//            findNavController().navigate(R.id.action_workoutMenuFragment_to_duringWorkoutFragment)
//        }
//        binding.btnWorkoutToProfile.setOnClickListener {
//            findNavController().navigate(R.id.action_workoutMenuFragment_to_profileMenuFragment)
//        }

        binding.btnStartWorkout.setOnClickListener {
            findNavController().navigate(R.id.action_workoutMenuFragment_to_duringWorkoutFragment)
        }

        binding.tbNavigation.selectedItemId = R.id.workout_menu
        binding.tbNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    findNavController().navigate(R.id.action_workoutMenuFragment_to_homeMenuFragment)
//                    it.isChecked = true
                    true
                }
                R.id.profile_menu -> {
                    findNavController().navigate(R.id.action_workoutMenuFragment_to_profileMenuFragment)
//                    it.isChecked = true
                    true
                }
                else -> false
            }
        }
    }
}