package hu.bme.aut.android.trackio.ui

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentDailyActivitiesBinding
import hu.bme.aut.android.trackio.databinding.FragmentMeasurementsDialogBinding

class MeasurementsDialogFragment : DialogFragment() {
    private lateinit var binding : FragmentMeasurementsDialogBinding

    companion object {
        const val TAG = "MeasurementsDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentMeasurementsDialogBinding.inflate(layoutInflater)
        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .setMessage("Set your measurements and goals")
            .setPositiveButton("ok") { _, _ -> }
            .setNegativeButton("back", null)
            .create()
    }
}