package hu.bme.aut.android.trackio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDuringWorkoutBinding

class DuringWorkoutFragment : Fragment() {
    private lateinit var binding : FragmentDuringWorkoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDuringWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDuringToMap.setOnClickListener {
            findNavController().navigate(R.id.action_duringWorkoutFragment_to_workoutMapFragment)
        }
        binding.btnPlayPause.setOnClickListener {
            binding.btnPlayPause.isActivated = !binding.btnPlayPause.isActivated
        }
//        binding.btnDuringToWorkoutMenu.setOnClickListener {
//            findNavController().navigate(R.id.action_duringWorkoutFragment_to_workoutMenuFragment)
//        }
    }
}