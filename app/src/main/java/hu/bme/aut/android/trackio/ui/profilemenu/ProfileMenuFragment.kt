package hu.bme.aut.android.trackio.ui.profilemenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.MainActivity
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentProfileMenuBinding
import hu.bme.aut.android.trackio.viewmodel.ProfileViewModel

class ProfileMenuFragment : Fragment() {
    private lateinit var binding : FragmentProfileMenuBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileMenuBinding.inflate(inflater, container, false)
        viewModel.username.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvUsername.text = it   //TODO
            }
        }
        viewModel.stepsGoal.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvSetGoalsValues.text = getString(R.string.goals_unit, it, viewModel.weightGoal.value)   //TODO
            }
        }
        viewModel.weightGoal.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvSetGoalsValues.text = getString(R.string.goals_unit, viewModel.stepsGoal.value, it)   //TODO
            }
        }
        viewModel.weight.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvWeightValues.text = getString(R.string.kg, it)   //TODO
            }
        }
        viewModel.height.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvHeightValues.text = getString(R.string.cm, it)   //TODO
            }
        }
        viewModel.gender.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvGenderValues.text = it.toString()   //TODO
            }
        }
        viewModel.birthDate.observe(viewLifecycleOwner) {
            if (true) {
                binding.tvBirthDateValues.text = it.toString()   //TODO date conversion
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clHelp.setOnClickListener {
            findNavController().navigate(R.id.action_profileMenuFragment_to_helpFragment)
        }
//        binding.btnProfileToPersonal.setOnClickListener {
//            findNavController().navigate(R.id.action_profileMenuFragment_to_personalDialogFragment)
//        }
//        binding.btnProfileToMeasurementsDialog.setOnClickListener {
//            findNavController().navigate(R.id.action_profileMenuFragment_to_measurementsDialogFragment)
//        }
//        binding.btnProfileToWorkout.setOnClickListener {
//            findNavController().navigate(R.id.action_profileMenuFragment_to_workoutMenuFragment)
//        }
//        binding.btnProfileToHome.setOnClickListener {
//            findNavController().navigate(R.id.action_profileMenuFragment_to_homeMenuFragment)
//        }
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

        binding.clGender.setOnClickListener {
            PersonalDialogFragment(
                isGenderVisible = true
            ).show(childFragmentManager, PersonalDialogFragment.TAG)
        }
        binding.clBirthDate.setOnClickListener {
            PersonalDialogFragment(
                isBirthDateVisible = true
            ).show(childFragmentManager, PersonalDialogFragment.TAG)
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