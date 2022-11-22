package hu.bme.aut.android.trackio.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentHomeMenuBinding
import kotlinx.android.synthetic.main.fragment_detailed_measurements.*
import kotlinx.android.synthetic.main.fragment_home_menu.*

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /*super.onViewCreated(view, savedInstanceState)

        binding.btnHomeToDaily.setOnClickListener {
            findNavController().navigate(R.id.action_homeMenuFragment_to_dailyActivitiesFragment)
        }
        binding.btnHomeToProfile.setOnClickListener {
            findNavController().navigate(R.id.action_homeMenuFragment_to_profileMenuFragment)
        }
        binding.btnHomeToDetailedMeasurements.setOnClickListener {
            )
        }
        binding.btnHomeToWorkoutMenu.setOnClickListener {
            findNavController().navigate(R.id.action_homeMenuFragment_to_workoutMenuFragment)
        }
        binding.btnHomeToWorkoutHistory.setOnClickListener {
            findNavController().navigate()
        }*/



        lineChart2.gradientFillColors =
            intArrayOf(
                Color.parseColor("#81FFFF"),
                Color.TRANSPARENT
            )
        lineChart2.animation.duration = animationDuration

        lineChart2.animate(lineSet2)

        binding.imageView4.setOnClickListener {
            findNavController().navigate(R.id.action_homeMenuFragment_to_detailedMeasurementsFragment)
        }

        binding.btnHomeToDaily.setOnClickListener {
            findNavController().navigate(R.id.action_homeMenuFragment_to_dailyActivitiesFragment)
        }

        binding.imageView7.setOnClickListener{
            findNavController().navigate(R.id.action_homeMenuFragment_to_workoutHistoryFragment)
        }

        binding.imageView6.setOnClickListener{
            findNavController().navigate(R.id.action_homeMenuFragment_to_workoutHistoryFragment)
        }

        binding.imageView5.setOnClickListener{
            findNavController().navigate(R.id.action_homeMenuFragment_to_workoutHistoryFragment)
        }

        binding.tbNavigation.selectedItemId = R.id.home_menu
        binding.tbNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.workout_menu -> {
                    findNavController().navigate(R.id.action_homeMenuFragment_to_workoutMenuFragment)
                    true
                }
                R.id.profile_menu -> {
                    findNavController().navigate(R.id.action_homeMenuFragment_to_profileMenuFragment)
                    true
                }
                else -> false
            }
        }
    }
    companion object{
        private val lineSet2 = listOf(
            "05/01" to 68.5f,
            "06/14" to 67.9f,
            "08/03" to 68.1f,
            "08/28" to 67.5f,
            "10/01" to 65.4f
        )
    }
    private val animationDuration = 1000L

}