package hu.bme.aut.android.trackio.ui.login

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.UserDetails
import hu.bme.aut.android.trackio.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel: ViewModel() {
    lateinit var userDetail: UserDetails
    lateinit var response: String

    fun LoginUser(userEmail : String, password : String): Boolean{
        Log.d("baj van", "viewmodel")

        NetworkManager.loginUser(userEmail,password).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d("baj van ?","Response: " + response.code())
                Log.d("baj van ?","dehogy van diló")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("baj van ","jaj neeee diló")
            }
        })


        return true
    }


}