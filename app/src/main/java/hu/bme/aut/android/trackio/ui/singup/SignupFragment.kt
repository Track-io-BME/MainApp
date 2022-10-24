package hu.bme.aut.android.trackio.ui.singup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentSignupBinding

class SignupFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var viewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        binding.spGender.adapter = ArrayAdapter(requireContext(), R.layout.spinner_content_template, resources.getStringArray(R.array.genders))

        binding.btnSignupToHome.setOnClickListener {
            if(viewModel.signUp(
                    binding.etFirstName.text.toString(),
                    binding.etLastName.text.toString(),
                    binding.etEmail.text.toString(),
                    0,
                    binding.etBirthDate.drawingTime,
                    binding.etWeight.text.toString().toFloat(),
                    binding.etHeight.text.toString().toFloat(),
                    binding.etPassword.text.toString()
                )){
                findNavController().navigate(R.id.action_signupFragment_to_homeMenuFragment)

            }
        }
//        binding.btnSignupToLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
//        }
    }

}