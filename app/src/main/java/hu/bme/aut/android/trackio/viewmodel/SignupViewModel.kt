package hu.bme.aut.android.trackio.viewmodel

import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.UserDetails

class SignupViewModel : ViewModel() {
    var userdetail : UserDetails? = null

    fun getUserDetail() : UserDetails?{
        return userdetail
    }
}