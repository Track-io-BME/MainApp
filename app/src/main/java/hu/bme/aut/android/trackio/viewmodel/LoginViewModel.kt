package hu.bme.aut.android.trackio.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.AutToken
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.UserDetails
import hu.bme.aut.android.trackio.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class LoginViewModel: ViewModel() {
    private val networkRepository: NetworkRepository = NetworkRepository()

    fun login(login: Login) : LiveData<Boolean> {
        var autToken : AutToken
        var succefulLogin = MutableLiveData<Boolean>()
        networkRepository.loginToServer(login)?.enqueue(object : Callback<AutToken?> {
            override fun onResponse(call: Call<AutToken?>, response: Response<AutToken?>) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        autToken = response.body()!!
                        Log.d("talan",response.body().toString())
                        SharedPrefConfig.put(SharedPrefConfig.pref_token,"Bearer "+autToken.token)
                        SharedPrefConfig.put(SharedPrefConfig.pref_password,login.password)
                        SharedPrefConfig.put(SharedPrefConfig.pref_email,autToken.email)
                        SharedPrefConfig.put(SharedPrefConfig.pref_signed_in,true)
                        val expiry_date = Calendar.getInstance().getTime().time+3600000
                        SharedPrefConfig.put(SharedPrefConfig.pref_expiry_date,expiry_date)
                        succefulLogin.value=true
                    }

                }
                else{
                    succefulLogin.value=false
                }
            }

            override fun onFailure(call: Call<AutToken?>, t: Throwable) {

                t.printStackTrace()
            }

        })
        return succefulLogin
    }

}