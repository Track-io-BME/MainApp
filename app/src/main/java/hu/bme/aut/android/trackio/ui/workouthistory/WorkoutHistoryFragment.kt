package hu.bme.aut.android.trackio.ui.workouthistory

import android.app.Activity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutHistoryBinding
import kotlinx.android.synthetic.main.fragment_workout_history.*
import java.util.*


class WorkoutHistoryFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutHistoryBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWorkoutHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var recyclerViewData = listOf<RecyclerViewData>()
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "01:40:15", "14:40", "2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "00:20:10","10:20", "2022.04.19")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "01:18:49","16:23","2022.04.20")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "01:10:14","08:15","2022.04.24")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "03:14:48","17:00","2022.04.26")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "02:56:33","09:04","2022.05.10")

        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)
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
            findNavController().navigate(R.id.action_workoutHistoryFragment_to_homeMenuFragment)
        }

        binding.bottomNavigationView.selectedItemId = R.id.workout_history_week_menu_item
        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.workout_history_month_menu_item -> {
                    findNavController().navigate(R.id.action_workoutHistoryFragment_to_workoutHistoryMonthFragment)
                    true
                }
                R.id.workout_history_all_menu_item -> {
                    findNavController().navigate(R.id.action_workoutHistoryFragment_to_workoutHistoryAllFragment2)
                    true
                }
                else -> false
            }
        }


    }




}