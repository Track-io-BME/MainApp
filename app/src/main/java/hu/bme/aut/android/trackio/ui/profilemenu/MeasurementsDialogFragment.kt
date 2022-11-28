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
import hu.bme.aut.android.trackio.data.UserGoals
import hu.bme.aut.android.trackio.data.roomentities.UserWeight
import hu.bme.aut.android.trackio.databinding.FragmentMeasurementsDialogBinding
import hu.bme.aut.android.trackio.repository.NetworkRepository
import java.util.Calendar

class MeasurementsDialogFragment(
    private var isWeightValueVisible: Boolean = false,
    private var isWeightGoalValueVisible: Boolean = false,
    private var isHeightValueVisible: Boolean = false,
    private var isStepsGoalValueVisible: Boolean = false
) : DialogFragment() {
    private lateinit var binding: FragmentMeasurementsDialogBinding
    private val networkRepository: NetworkRepository = NetworkRepository()

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
        binding.etWeightGoalValue.filters =
            arrayOf<InputFilter>(MinMaxFilter(MIN_VALUE, MAX_WEIGHT))
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
                if (binding.etWeightValue.text.isNotEmpty()) {
                    val newWeightValue = binding.etWeightValue.text.toString().toFloat()
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_weight,
                        newWeightValue
                    )
                    networkRepository.postUserWeight(
                        SharedPrefConfig.pref_token,
                        UserWeight(0, Calendar.getInstance().timeInMillis, newWeightValue)
                    )
                }
                if (binding.etHeightValue.text.isNotEmpty()) {
                    val newHeightValue = binding.etHeightValue.text.toString().toFloat()
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_height,
                        newHeightValue
                    )
                    networkRepository.postUserHeight(SharedPrefConfig.pref_token, newHeightValue)
                }
                var newWeightGoalValue: Float? = null
                var newStepsGoalValue: Int? = null
                if (binding.etWeightGoalValue.text.isNotEmpty()) {
                    newWeightGoalValue = binding.etWeightGoalValue.text.toString().toFloat()
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_weight_goal,
                        newWeightGoalValue
                    )
                }
                if (binding.etStepsGoalValue.text.isNotEmpty()) {
                    newStepsGoalValue = binding.etStepsGoalValue.text.toString().toInt()
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_steps_goal,
                        newStepsGoalValue
                    )
                }
                if (newStepsGoalValue != null || newWeightGoalValue != null) {
                    networkRepository.putGoals(
                        SharedPrefConfig.getString(SharedPrefConfig.pref_token),
                        UserGoals(
                            newStepsGoalValue
                                ?: SharedPrefConfig.getInt(SharedPrefConfig.pref_steps_goal),
                            newWeightGoalValue
                                ?: SharedPrefConfig.getFloat(SharedPrefConfig.pref_weight_goal)
                        )
                    )
                }
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