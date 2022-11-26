package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import androidx.lifecycle.*
import hu.bme.aut.android.trackio.data.ServerResponse
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.database.AppDatabase
import hu.bme.aut.android.trackio.repository.DbRepository
import hu.bme.aut.android.trackio.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val dbRepository: DbRepository
    private val networkRepository: NetworkRepository = NetworkRepository()

    init {
        val databaseDAO = AppDatabase.getDatabase(application).databaseDAO()
        dbRepository = DbRepository(databaseDAO)
    }

    fun login(login: Login): LiveData<Boolean> {
        var serverResponse: ServerResponse
        var succesfulLogin = MutableLiveData<Boolean>()
        networkRepository.loginToServer(login)?.enqueue(object : Callback<ServerResponse?> {
            override fun onResponse(
                call: Call<ServerResponse?>,
                response: Response<ServerResponse?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        serverResponse = response.body()!!
                        //Log.d("talan", response.body().toString())
                        if (serverResponse.email != SharedPrefConfig.getString(SharedPrefConfig.pref_email)) {
                            deleteAllUserDB()
                            SharedPrefConfig.deleteAll()
                        }
                        SharedPrefConfig.put(
                            SharedPrefConfig.pref_token,
                            "Bearer " + serverResponse.token
                        )
                        SharedPrefConfig.put(SharedPrefConfig.pref_password, login.password)
                        SharedPrefConfig.put(SharedPrefConfig.pref_email, serverResponse.email)
                        SharedPrefConfig.put(SharedPrefConfig.pref_signed_in, true)
                        succesfulLogin.value = true
                    }

                } else {
                    succesfulLogin.value = false
                }
            }

            override fun onFailure(call: Call<ServerResponse?>, t: Throwable) {

                t.printStackTrace()
            }

        })
        return succesfulLogin
    }

    fun deleteAllUserDB(){
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteAllData()
        }
    }
}