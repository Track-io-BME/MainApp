package hu.bme.aut.android.trackio.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDailyActivitiesBinding
import hu.bme.aut.android.trackio.databinding.FragmentMeasurementsDialogBinding
import hu.bme.aut.android.trackio.databinding.FragmentPersonalDialogBinding
import hu.bme.aut.android.trackio.databinding.FragmentProfileMenuBinding

class PersonalDialogFragment : DialogFragment() {
    private lateinit var binding : FragmentPersonalDialogBinding

    companion object {
        const val TAG = "PersonalDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentPersonalDialogBinding.inflate(layoutInflater)

        binding.spGender.adapter = ArrayAdapter(requireContext(), R.layout.spinner_content_template, resources.getStringArray(R.array.genders))

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage("Set your personal information")
            .setPositiveButton("ok") { _, _ -> }
            .setNegativeButton("back", null)
            .create()
    }
}