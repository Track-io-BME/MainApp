package hu.bme.aut.android.trackio.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        binding.btnLoginToHome.setOnClickListener{
            if(!TextUtils.isEmpty(binding.etEmail.text) && !TextUtils.isEmpty(binding.etPassword.text)){
                Log.d("baj van", "login fragment");
                if(viewModel.LoginUser(binding.etEmail.text.toString(),binding.etPassword.text.toString())){
                    findNavController().navigate(R.id.action_loginFragment_to_homeMenuFragment)

                }

            }
        }

        binding.tvLoginToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}