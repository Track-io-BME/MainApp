package hu.bme.aut.android.trackio.viewmodel

import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.UserDetails

class SignupViewModel : ViewModel() {
    var userdetail : UserDetails? = null

    fun getUserDetail() : UserDetails?{
        return userdetail
    }

    fun signUp( firstName: String,
                lastName: String,
                emailAddress: String,
                gender: Int,
                birthDate: Long,
                weight: Float,
                height: Float,
                password: String){

    }
}