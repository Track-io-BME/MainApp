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
import com.google.android.material.bottomnavigation.BottomNavigationView
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentWorkoutHistoryBinding
import kotlinx.android.synthetic.main.fragment_workout_history.*
import java.util.*

class WorkoutHistoryFragment : Fragment() {
    private lateinit var binding : FragmentWorkoutHistoryBinding
    private lateinit var navController: NavController

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
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "One", "One", "2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "Two","One", "2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "Three","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "Four","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "Five","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "Six","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "Seven","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "Eight","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "Nine", "One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "Ten","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "Eleven","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "Twelve","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Cycling", "Thirteen","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Walking", "Fourteen","One","2022.04.10")
        recyclerViewData = recyclerViewData + RecyclerViewData("Running", "Fifteen","One","2022.04.10")

        // create a vertical layout manager
        val layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL, false)

        // create instance of MyRecyclerViewAdapter
        val myRecyclerViewAdapter = MyRecyclerViewAdapter(recyclerViewData)

        // attach the adapter to the recycler view
        recyclerView.adapter = myRecyclerViewAdapter

        // also attach the layout manager
        recyclerView.layoutManager = layoutManager

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                /* context = */ requireContext(),
                /* orientation = */ layoutManager.orientation
            )
        )

        // make the adapter the data set
        // changed for the recycler view
        myRecyclerViewAdapter.notifyDataSetChanged()

        binding.workoutHistoryBack.setOnClickListener {
            findNavController().navigate(R.id.action_workoutHistoryFragment_to_homeMenuFragment)
        }


    }


}