package hu.bme.aut.android.trackio.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hu.bme.aut.android.trackio.data.ServerResponse
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.UserCreationResponse
import hu.bme.aut.android.trackio.data.UserSignUP
import hu.bme.aut.android.trackio.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val networkRepository: NetworkRepository = NetworkRepository()

    fun signUp(userSignUP: UserSignUP): LiveData<String> {
        var serverResponse: UserCreationResponse
        var succesfullSignUP = MutableLiveData<String>()
        networkRepository.signUp(userSignUP)?.enqueue(object : Callback<UserCreationResponse?> {
            override fun onResponse(
                call: Call<UserCreationResponse?>,
                response: Response<UserCreationResponse?>
            ) {
                if (response.isSuccessful) {
                    Log.d("talan",response.body().toString())
                    if (response.body() != null) {
                        serverResponse = response.body()!!
                        SharedPrefConfig.put(SharedPrefConfig.pref_email, serverResponse.email)
                        succesfullSignUP.value = serverResponse.message
                    }
                }
            }

            override fun onFailure(call: Call<UserCreationResponse?>, t: Throwable) {
                t.printStackTrace()
            }

        })

        return succesfullSignUP


    }


}