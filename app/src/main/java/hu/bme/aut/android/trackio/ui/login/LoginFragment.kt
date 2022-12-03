package hu.bme.aut.android.trackio.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.databinding.FragmentLoginBinding
import hu.bme.aut.android.trackio.network.InternetConnectivityChecker
import hu.bme.aut.android.trackio.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.etEmail.setText(SharedPrefConfig.getString(SharedPrefConfig.pref_email, ""))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        val login = SharedPrefConfig.getBoolean(SharedPrefConfig.pref_signed_in, false)
        if (login) {
            if (InternetConnectivityChecker.isOnline()) {
                viewModel.login(
                    Login(
                        SharedPrefConfig.getString(SharedPrefConfig.pref_email),
                        SharedPrefConfig.getString(SharedPrefConfig.pref_password)
                    )
                ).observe(viewLifecycleOwner) { succesfulLogin ->
                    if (succesfulLogin) {
                        SharedPrefConfig.put(SharedPrefConfig.pref_signed_in, true)
                        findNavController().navigate(R.id.action_loginFragment_to_homeMenuFragment)
                    } else {
                        Toast.makeText(context, "Wrong password or email", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "No internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.btnLoginToHome.setOnClickListener {
            if (InternetConnectivityChecker.isOnline()) {
                viewModel.login(
                    Login(
                        binding.etEmail.text.toString(),
                        binding.etPassword.text.toString()
                    )
                ).observe(viewLifecycleOwner) { succesfulLogin ->
                    if (succesfulLogin) {
                        findNavController().navigate(R.id.action_loginFragment_to_homeMenuFragment)
                    } else {
                        Toast.makeText(context, "Wrong password or email", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(
                    context,
                    "No internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        binding.tvLoginToSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }

            }
}