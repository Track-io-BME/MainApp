package hu.bme.aut.android.trackio.ui.dailyactivities

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.databinding.FragmentDailyActivitiesBinding
import hu.bme.aut.android.trackio.ui.workoutmenu.ActiveChallengesAdapter
import hu.bme.aut.android.trackio.viewmodel.DailyActivitiesViewModel

class DailyActivitiesFragment : Fragment() {
    private lateinit var binding: FragmentDailyActivitiesBinding
    private val viewModel: DailyActivitiesViewModel by viewModels()
    private lateinit var adapter: ActiveChallengesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDailyActivitiesBinding.inflate(inflater, container, false)
        initRecycleView()

        viewModel.getCompletedChallenges().observe(viewLifecycleOwner) {
            adapter.setData(it as List<ActiveChallenge>)
        }
        return binding.root
    }

    private fun initRecycleView() {
        adapter = ActiveChallengesAdapter()
        binding.completedChallenges.layoutManager = LinearLayoutManager(requireContext())
        binding.completedChallenges.adapter = adapter
    }
}