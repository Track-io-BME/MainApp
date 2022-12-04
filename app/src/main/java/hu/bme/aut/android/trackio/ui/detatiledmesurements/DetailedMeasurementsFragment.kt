package hu.bme.aut.android.trackio.ui.detatiledmesurements

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.trackio.databinding.FragmentDetailedMeasurementsBinding
import hu.bme.aut.android.trackio.ui.profilemenu.MeasurementsDialogFragment
import kotlinx.android.synthetic.main.fragment_detailed_measurements.*

class DetailedMeasurementsFragment : Fragment() {
    private lateinit var binding : FragmentDetailedMeasurementsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailedMeasurementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lineChart.gradientFillColors =
            intArrayOf(
                Color.parseColor("#81FFFF"),
                Color.TRANSPARENT
            )
        lineChart.animation.duration = animationDuration

        lineChart.animate(lineSet)

        binding.newDetails.setOnClickListener {
            MeasurementsDialogFragment(
                isWeightValueVisible = true,
                isHeightValueVisible = true
            ).show(childFragmentManager, MeasurementsDialogFragment.TAG)
        }
    }

    companion object{
        private val lineSet = listOf(
            "05/01" to 68.5f,
            "06/14" to 67.9f,
            "08/03" to 68.1f,
            "08/28" to 67.5f,
            "10/01" to 68.4f
        )
    }

    private val animationDuration = 1000L
}