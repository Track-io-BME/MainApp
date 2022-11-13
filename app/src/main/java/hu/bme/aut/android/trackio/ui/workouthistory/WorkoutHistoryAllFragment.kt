package hu.bme.aut.android.trackio.ui.workouthistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutHistoryAllBinding
import kotlinx.android.synthetic.main.fragment_workout_history.*

class WorkoutHistoryAllFragment : Fragment() {
    private lateinit var binding: FragmentWorkoutHistoryAllBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutHistoryAllBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerViewData = listOf<RecyclerViewData>()
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "01:18:49","16:23","2022.04.20")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "01:10:14","08:15","2022.04.24")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "03:14:48","17:00","2022.04.26")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "02:56:33","09:04","2022.05.10")

        val layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        val myRecyclerViewAdapter = MyRecyclerViewAdapter(recyclerViewData)
        recyclerView.adapter = myRecyclerViewAdapter
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                layoutManager.orientation
            )
        )

        myRecyclerViewAdapter.notifyDataSetChanged()

        binding.workoutHistoryBack.setOnClickListener {
            findNavController().navigate(R.id.action_workoutHistoryAllFragment2_to_homeMenuFragment)
        }

        binding.bottomNavigationView.selectedItemId = R.id.workout_history_all_menu_item
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.workout_history_month_menu_item -> {
                    findNavController().navigate(R.id.action_workoutHistoryAllFragment2_to_workoutHistoryMonthFragment)
                    true
                }
                R.id.workout_history_week_menu_item -> {
                    findNavController().navigate(R.id.action_workoutHistoryAllFragment2_to_workoutHistoryFragment)
                    true
                }
                else -> false
            }
        }

    }
}