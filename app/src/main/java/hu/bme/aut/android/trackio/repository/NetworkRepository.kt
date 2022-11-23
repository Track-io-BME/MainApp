package hu.bme.aut.android.trackio.repository

import hu.bme.aut.android.trackio.data.AutToken
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.network.RetrofitInstance
import retrofit2.Call

class NetworkRepository {
    fun loginToServer(login: Login) :  Call<AutToken?>? {
        return RetrofitInstance.api.login(login)
    }

    fun getActiveChallenges(autToken: String)  : Call<List<ActiveChallenge?>?>?{
        return RetrofitInstance.api.getActiveChallenges(autToken)
    }


}