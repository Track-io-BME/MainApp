package hu.bme.aut.android.trackio.ui.profilemenu

import android.app.Dialog
import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentMeasurementsDialogBinding

class MeasurementsDialogFragment(
    private var isWeightValueVisible: Boolean = false,
    private var isWeightGoalValueVisible: Boolean = false,
    private var isHeightValueVisible: Boolean = false,
    private var isStepsGoalValueVisible: Boolean = false
) : DialogFragment() {
    private lateinit var binding : FragmentMeasurementsDialogBinding

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

        var setterTitle = ""

        if (!isWeightValueVisible)
            binding.etWeightValue.visibility = View.GONE
        if (!isWeightGoalValueVisible)
            binding.etWeightGoalValue.visibility = View.GONE
        if (!isHeightValueVisible)
            binding.etHeightValue.visibility = View.GONE
        if (!isStepsGoalValueVisible)
            binding.etStepsGoalValue.visibility = View.GONE

        if (isWeightValueVisible || isHeightValueVisible)
            setterTitle = getString(R.string.measurements)
        if (isWeightGoalValueVisible || isStepsGoalValueVisible) {
            if (setterTitle != "")
                setterTitle += " and "
            setterTitle += getString(R.string.goals)
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage(getString(R.string.set_your_something, setterTitle))
            .setPositiveButton(getString(R.string.ok)) { _, _ ->
                if(binding.etWeightValue.text.isNotEmpty())
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_weight,
                        binding.etWeightValue.text.toString().toFloat()
                    )
                if(binding.etWeightGoalValue.text.isNotEmpty())
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_weight_goal,
                        binding.etWeightGoalValue.text.toString().toFloat()
                    )
                if(binding.etHeightValue.text.isNotEmpty())
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_height,
                        binding.etHeightValue.text.toString().toFloat()
                    )
                if(binding.etStepsGoalValue.text.isNotEmpty())
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_steps_goal,
                        binding.etStepsGoalValue.text.toString().toInt()
                    )
            }
            .setNegativeButton(getString(R.string.back), null)
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