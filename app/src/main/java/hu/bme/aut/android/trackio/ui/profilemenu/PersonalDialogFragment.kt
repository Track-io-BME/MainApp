package hu.bme.aut.android.trackio.ui.profilemenu

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentPersonalDialogBinding

class PersonalDialogFragment(
    private var isGenderVisible: Boolean = false,
    private var isBirthDateVisible: Boolean = false
) : DialogFragment() {
    private lateinit var binding : FragmentPersonalDialogBinding

    companion object {
        const val TAG = "PersonalDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        if (!this::binding.isInitialized)
            binding = FragmentPersonalDialogBinding.inflate(layoutInflater)

        val spAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_content_template,
            resources.getStringArray(R.array.genders)
        )
        binding.spGender.adapter = spAdapter
        binding.spGender.setSelection(
            spAdapter.getPosition(SharedPrefConfig.getString(SharedPrefConfig.pref_gender, getString(R.string.male)))
        )

        if (!isGenderVisible)
            binding.spGender.visibility = View.GONE
        if (!isBirthDateVisible)
            binding.etBirthDate.visibility = View.GONE

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage("Set your personal information")
            .setPositiveButton("ok") { _, _ ->
                if(binding.etBirthDate.text.isNotEmpty()) {   //TODO date conversion
                    SharedPrefConfig.put(
                        SharedPrefConfig.pref_birth_date,
                        binding.etBirthDate.text.toString().toLong()
                    )
                }
                SharedPrefConfig.put(
                    SharedPrefConfig.pref_gender,
                    binding.spGender.selectedItem.toString()
                )
            }
            .setNegativeButton("back", null)
            .create()
    }
}