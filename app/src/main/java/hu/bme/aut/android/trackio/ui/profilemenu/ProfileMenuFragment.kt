package hu.bme.aut.android.trackio.ui.profilemenu

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.MainActivity
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentProfileMenuBinding
import hu.bme.aut.android.trackio.viewmodel.ProfileViewModel

class ProfileMenuFragment : Fragment() {
    private lateinit var binding: FragmentProfileMenuBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        viewModel.username.observe(viewLifecycleOwner) {
            binding.tvUsername.text = it
        }
        viewModel.stepsGoal.observe(viewLifecycleOwner) {
            binding.tvSetGoalsValues.text =
                getString(R.string.goals_unit, it, viewModel.weightGoal.value)
        }
        viewModel.weightGoal.observe(viewLifecycleOwner) {
            binding.tvSetGoalsValues.text =
                getString(R.string.goals_unit, viewModel.stepsGoal.value, it)
        }
        viewModel.weight.observe(viewLifecycleOwner) {
            binding.tvWeightValues.text = getString(R.string.kg, it)
        }
        viewModel.height.observe(viewLifecycleOwner) {
            binding.tvHeightValues.text = getString(R.string.cm, it)
        }
        viewModel.gender.observe(viewLifecycleOwner) {
            binding.tvGenderValues.text = it.toString()
        }
        viewModel.birthDate.observe(viewLifecycleOwner) {
            binding.tvBirthDateValues.text = it.toString()
        }

        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    AlertDialog.Builder(requireContext())
                        .setTitle(getString(R.string.exit_the_app))
                        .setPositiveButton(getString(R.string.yes)) { _, _ ->
                            activity?.finish()
                        }
                        .setNegativeButton(getString(R.string.no), null)
                        .show()
                }
            })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clHelp.setOnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment_to_helpFragment)
        }
        binding.clSignOut.setOnClickListener {
            SharedPrefConfig.put(SharedPrefConfig.pref_signed_in, false)
            val intent = Intent(requireContext(), MainActivity::class.java)
            activity?.finish()
            startActivity(intent)
        }

        binding.tbNavigation.selectedItemId = R.id.profile_menu
        binding.tbNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home_menu -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_homeMenuFragment)
                    true
                }
                R.id.workout_menu -> {
                    findNavController().navigate(R.id.action_profileMenuFragment_to_workoutMenuFragment)
                    true
                }
                else -> false
            }
        }

        binding.clSetGoals.setOnClickListener {
            MeasurementsDialogFragment(
                isWeightGoalValueVisible = true,
                isStepsGoalValueVisible = true
            ).show(childFragmentManager, MeasurementsDialogFragment.TAG)
        }
        binding.clWeight.setOnClickListener {
            MeasurementsDialogFragment(
                isWeightValueVisible = true
            ).show(childFragmentManager, MeasurementsDialogFragment.TAG)
        }
        binding.clHeight.setOnClickListener {
            MeasurementsDialogFragment(
                isHeightValueVisible = true
            ).show(childFragmentManager, MeasurementsDialogFragment.TAG)
        }
    }

    override fun onStart() {
        super.onStart()
        SharedPrefConfig.registerListener(viewModel)
    }

    override fun onStop() {
        super.onStop()
        SharedPrefConfig.unregisterListener(viewModel)
    }
}