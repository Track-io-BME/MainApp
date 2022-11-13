package hu.bme.aut.android.trackio.ui.profilemenu

import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentPersonalDialogBinding
import hu.bme.aut.android.trackio.model.ProfileViewModel

class PersonalDialogFragment : DialogFragment() {
    private lateinit var binding : FragmentPersonalDialogBinding
    private val viewModel: ProfileViewModel by activityViewModels()

    companion object {
        const val TAG = "PersonalDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentPersonalDialogBinding.inflate(layoutInflater)

        val spAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_content_template,
            resources.getStringArray(R.array.genders)
        )
        binding.spGender.adapter = spAdapter
        binding.spGender.setSelection(
            spAdapter.getPosition(viewModel.gender.value)
        )

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage("Set your personal information")
            .setPositiveButton("ok") { _, _ ->
                if(binding.etBirthDate.text.isNotEmpty() &&
                    binding.etBirthDate.text.toString().toLong() != viewModel.birthDate.value)   //TODO date conversion
                    viewModel.birthDate.value = binding.etBirthDate.text.toString().toLong()
                if(binding.spGender.selectedItem.toString() != viewModel.gender.value)
                    viewModel.gender.value = binding.spGender.selectedItem.toString()
            }
            .setNegativeButton("back", null)
            .create()
    }
}