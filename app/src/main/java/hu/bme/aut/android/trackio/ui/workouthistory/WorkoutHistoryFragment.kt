package hu.bme.aut.android.trackio.ui.workouthistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutHistoryBinding
import hu.bme.aut.android.trackio.ui.home.RowItemClick
import hu.bme.aut.android.trackio.ui.home.WorkoutAdapter
import hu.bme.aut.android.trackio.viewmodel.WorkoutHistoryViewModel

class WorkoutHistoryFragment : Fragment(), RowItemClick {
    private lateinit var binding: FragmentWorkoutHistoryBinding
    private val viewModel: WorkoutHistoryViewModel by activityViewModels()
    private lateinit var adapter: WorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        viewModel.getWeekWorkoutHistory().observe(viewLifecycleOwner) { workoutList ->
            adapter.setData(workoutList as List<Workout>)
        }
        binding.bottomNavigationView.selectedItemId = R.id.workout_history_week_menu_item
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.workout_history_week_menu_item -> {
                    viewModel.getWeekWorkoutHistory().observe(viewLifecycleOwner) { workoutList ->
                        adapter.setData(workoutList as List<Workout>)
                    }
                    true
                }
                R.id.workout_history_month_menu_item -> {
                    viewModel.getMonthWorkoutHistory().observe(viewLifecycleOwner) { workoutList ->
                        adapter.setData(workoutList as List<Workout>)
                    }
                    true
                }
                R.id.workout_history_all_menu_item -> {
                    viewModel.getAllWorkoutHistory().observe(viewLifecycleOwner) { workoutList ->
                        adapter.setData(workoutList as List<Workout>)
                    }
                    true
                }
                else -> false
            }
        }

        binding.workoutHistoryBack.setOnClickListener {
            findNavController().navigate(R.id.action_workoutHistoryFragment_to_homeMenuFragment)
        }
    }

    private fun initRecyclerView() {
        adapter = WorkoutAdapter(this)
        binding.WorkoutHistoryrecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.WorkoutHistoryrecyclerView.adapter = adapter
    }

    override fun onItemClick(currentItem: Workout) {

    }
}