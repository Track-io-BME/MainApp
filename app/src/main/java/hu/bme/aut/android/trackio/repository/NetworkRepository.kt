package hu.bme.aut.android.trackio.repository

import hu.bme.aut.android.trackio.data.ServerResponse
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.UserCreationResponse
import hu.bme.aut.android.trackio.data.UserSignUP
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.network.RetrofitInstance
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

class NetworkRepository {

    fun signUp(userSignUP: UserSignUP) : Call<UserCreationResponse?>?{
        return RetrofitInstance.api.signUp(userSignUP)
    }

    fun loginToServer(login: Login) :  Call<ServerResponse?>? {
        return RetrofitInstance.api.login(login)
    }

    fun finishTraining(token: String, @Body workout: Workout) : Call<Workout?>?{
        return RetrofitInstance.api.finishTraining(token,workout)
    }

    fun getActiveChallenges(autToken: String)  : Call<List<ActiveChallenge?>?>?{
        return RetrofitInstance.api.getActiveChallenges(autToken)
    }

    fun getCompletedChallenges(autToken: String)  : Call<List<ActiveChallenge?>?>?{
        return RetrofitInstance.api.getCompletedChallenges(autToken)
    }

    fun getTop3Workout(token: String) : Call<List<Workout?>?>?{
        return RetrofitInstance.api.getTop3Workout(token)
    }

}