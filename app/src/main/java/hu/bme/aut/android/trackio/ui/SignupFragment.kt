package hu.bme.aut.android.trackio.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentLoginBinding
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

        binding.spGender.adapter = ArrayAdapter(requireContext(), R.layout.spinner_content_template, resources.getStringArray(R.array.genders))

        binding.btnSignupToHome.setOnClickListener {
            findNavController().navigate(R.id.action_signupFragment_to_homeMenuFragment)
        }
//        binding.btnSignupToLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
//        }
    }
}