package hu.bme.aut.android.trackio.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentLoginBinding
import hu.bme.aut.android.trackio.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.etEmail.setText(SharedPrefConfig.getString(SharedPrefConfig.pref_username, ""))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLoginToHome.setOnClickListener{
            if(!TextUtils.isEmpty(binding.etEmail.text) && !TextUtils.isEmpty(binding.etPassword.text)){
                Log.d("baj van", "login fragment")
                if(viewModel.LoginUser(binding.etEmail.text.toString(),binding.etPassword.text.toString())){
                    findNavController().navigate(R.id.action_loginFragment_to_homeMenuFragment)

                }

            }
            else{   // todo - kiszedni, ha mar mukodik a login
                Toast.makeText(context, "shortcut", Toast.LENGTH_SHORT).show()
                if (binding.etEmail.text.toString() != SharedPrefConfig.getString(SharedPrefConfig.pref_username, ""))
                    SharedPrefConfig.clear()
                SharedPrefConfig.put(SharedPrefConfig.pref_signed_in, true)
                SharedPrefConfig.put(SharedPrefConfig.pref_username, binding.etEmail.text.toString())
                findNavController().navigate(R.id.action_loginFragment_to_homeMenuFragment)
            }
        }

        binding.tvLoginToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }
}