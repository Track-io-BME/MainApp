package hu.bme.aut.android.trackio.ui.singup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.UserSignUP
import hu.bme.aut.android.trackio.databinding.FragmentSignupBinding
import hu.bme.aut.android.trackio.network.InternetConnectivityChecker
import hu.bme.aut.android.trackio.viewmodel.SignupViewModel

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignupViewModel by viewModels()

    companion object {
        const val DATE_SELECTED_KEY = "date_selected"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spGender.adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_content_template,
            resources.getStringArray(R.array.genders)
        )

        binding.etBirthDate.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_datePickerDialogFragment)
        }

        if (!InternetConnectivityChecker.isOnline()) {
            Toast.makeText(context, getString(R.string.no_internet), Toast.LENGTH_LONG).show()
        }
        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<DatePickerDialogFragment.DatePickerResult>(
            DATE_SELECTED_KEY
        )?.observe(viewLifecycleOwner) { result ->
            result.month++
            val birthDate =
                result.year.toString() + "." + result.month.toString() + "." + result.day.toString()
            binding.etBirthDate.setText(birthDate)
        }

        binding.btnSignupToHome.setOnClickListener {
            var allSet = true
            if (binding.etFirstName.text.isEmpty()) allSet = false
            else if (binding.etLastName.text.isEmpty()) allSet = false
            else if (binding.etEmail.text.isEmpty()) allSet = false
            else if (binding.etBirthDate.text.isEmpty()) allSet = false
            else if (binding.etHeight.text.isEmpty()) allSet = false
            else if (binding.etWeight.text.isEmpty()) allSet = false
            else if (binding.etPassword.text.isEmpty()) allSet = false

            if (!allSet) {
                Toast.makeText(context, getString(R.string.set_all_description), Toast.LENGTH_LONG)
                    .show()
            }
            if (allSet) {
                viewModel.signUp(
                    UserSignUP(
                        firstname = binding.etFirstName.text.toString(),
                        lastname = binding.etLastName.text.toString(),
                        gender = getGenderFromPosition(binding.spGender.selectedItemPosition),
                        dateofbirth = binding.etBirthDate.text.toString(),
                        email = binding.etEmail.text.toString(),
                        height = binding.etHeight.text.toString().toFloat(),
                        weight = binding.etWeight.text.toString().toFloat(),
                        password = binding.etPassword.text.toString()
                    )
                ).observe(viewLifecycleOwner) { messageToUser ->
                    Toast.makeText(context, messageToUser, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun getGenderFromPosition(ordinal: Int): String {
        return if (ordinal == 0) {
            getString(R.string.male).lowercase()
        } else {
            getString(R.string.female).lowercase()
        }
    }
}