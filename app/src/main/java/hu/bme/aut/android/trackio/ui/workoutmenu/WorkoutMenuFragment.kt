package hu.bme.aut.android.trackio.ui.workoutmenu

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutMenuBinding
import hu.bme.aut.android.trackio.viewmodel.WorkoutViewModel


class WorkoutMenuFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutMenuBinding
    private val viewModel : WorkoutViewModel by activityViewModels()
    private lateinit var  adapter: ActiveChallengesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutMenuBinding.inflate(inflater, container, false)
        initRecycleView()


        when (viewModel.currentWorkoutType) {
            WorkoutViewModel.WorkoutType.WALKING -> {
                binding.tvWalkingMode.text = underlineText(binding.tvWalkingMode.text.toString())
                viewModel.activeWalkingChallengesFromDB.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            WorkoutViewModel.WorkoutType.RUNNING -> {
                binding.tvRunningMode.text = underlineText(binding.tvRunningMode.text.toString())
                viewModel.activeRunningChallengesFromDB.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
            WorkoutViewModel.WorkoutType.CYCLING -> {
                binding.tvCyclingMode.text = underlineText(binding.tvCyclingMode.text.toString())
                viewModel.activeCyclingChallengesFromDB.observe(viewLifecycleOwner){
                    adapter.setData(it)
                }
            }
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
            viewModel.activeWalkingChallengesFromDB.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
        }
        binding.tvRunningMode.setOnClickListener {
            binding.tvWalkingMode.text = binding.tvWalkingMode.text.toString()
            binding.tvRunningMode.text = underlineText(binding.tvRunningMode.text.toString())
            binding.tvCyclingMode.text = binding.tvCyclingMode.text.toString()
            viewModel.currentWorkoutType = WorkoutViewModel.WorkoutType.RUNNING
            viewModel.activeRunningChallengesFromDB.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
        }
        binding.tvCyclingMode.setOnClickListener {
            binding.tvWalkingMode.text = binding.tvWalkingMode.text.toString()
            binding.tvRunningMode.text = binding.tvRunningMode.text.toString()
            binding.tvCyclingMode.text = underlineText(binding.tvCyclingMode.text.toString())
            viewModel.currentWorkoutType = WorkoutViewModel.WorkoutType.CYCLING
            viewModel.activeCyclingChallengesFromDB.observe(viewLifecycleOwner){
                adapter.setData(it)
            }
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

    private fun initRecycleView(){
        adapter = ActiveChallengesAdapter()
        binding.challengeRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.challengeRecyclerView.adapter=adapter

    }
}

