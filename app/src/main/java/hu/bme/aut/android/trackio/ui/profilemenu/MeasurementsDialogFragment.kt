package hu.bme.aut.android.trackio.ui.profilemenu

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import hu.bme.aut.android.trackio.databinding.FragmentMeasurementsDialogBinding
import hu.bme.aut.android.trackio.model.ProfileViewModel

class MeasurementsDialogFragment : DialogFragment() {
    private lateinit var binding : FragmentMeasurementsDialogBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    companion object {
        const val TAG = "MeasurementsDialogFragment"
        const val MIN_VALUE = 0F
        const val MAX_WEIGHT = 500F
        const val MAX_HEIGHT = 350F
        const val MAX_STEPS = 1_000_000F
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentMeasurementsDialogBinding.inflate(layoutInflater)
        binding.etWeightValue.filters = arrayOf<InputFilter>(MinMaxFilter(MIN_VALUE, MAX_WEIGHT))
        binding.etWeightGoalValue.filters = arrayOf<InputFilter>(MinMaxFilter(MIN_VALUE, MAX_WEIGHT))
        binding.etHeightValue.filters = arrayOf<InputFilter>(MinMaxFilter(MIN_VALUE, MAX_HEIGHT))
        binding.etStepsGoalValue.filters = arrayOf<InputFilter>(MinMaxFilter(MIN_VALUE, MAX_STEPS))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage("Set your measurements and goals")
            .setPositiveButton("ok") { _, _ ->
                if(binding.etWeightValue.text.isNotEmpty() &&
                    binding.etWeightValue.text.toString().toFloat() != viewModel.weight.value)
                    viewModel.weight.value = binding.etWeightValue.text.toString().toFloat()
                if(binding.etWeightGoalValue.text.isNotEmpty() &&
                    binding.etWeightGoalValue.text.toString().toFloat() != viewModel.weightGoal.value)
                    viewModel.weightGoal.value = binding.etWeightGoalValue.text.toString().toFloat()
                if(binding.etHeightValue.text.isNotEmpty() &&
                    binding.etHeightValue.text.toString().toFloat() != viewModel.height.value)
                    viewModel.height.value = binding.etHeightValue.text.toString().toFloat()
                if(binding.etStepsGoalValue.text.isNotEmpty() &&
                    binding.etStepsGoalValue.text.toString().toInt() != viewModel.stepsGoal.value)
                    viewModel.stepsGoal.value = binding.etStepsGoalValue.text.toString().toInt()
            }
            .setNegativeButton("back", null)
            .create()
    }

    inner class MinMaxFilter(private val min: Float, private val max: Float) : InputFilter {
        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dStart: Int,
            dEnd: Int
        ): CharSequence? {
            val input = (dest.toString() + source.toString())
            if (input.length <= max.toString().length)
                try {
                    val value = input.toFloat()
                    if (isInRange(min, max, value)) {
                        return null
                    }
                } catch (e: NumberFormatException) {
                    e.printStackTrace()
                }
            return ""
        }

        private fun isInRange(a: Float, b: Float, c: Float): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
}