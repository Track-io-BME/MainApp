package hu.bme.aut.android.trackio.ui.singup

import android.util.Log
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.UserDetails
import hu.bme.aut.android.trackio.network.NetworkManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    var userdetail : UserDetails? = null

    fun getUserDetail() : UserDetails?{
        return userdetail
    }
    fun signUp(
        firstName: String,
        lastName: String,
        emailAddress: String,
        gender: Int,
        birthDate: Long,
        weight: Float,
        height: Float,
        password: String
    ) : Boolean{
        NetworkManager.signUp(firstName,lastName,emailAddress,gender,birthDate,weight,height,password).enqueue(object :
            Callback<String> {
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