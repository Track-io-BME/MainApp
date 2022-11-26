package hu.bme.aut.android.trackio.repository

import hu.bme.aut.android.trackio.data.*
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.UserWeight
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.network.RetrofitInstance
import retrofit2.Call

class NetworkRepository {

    fun signUp(userSignUP: UserSignUP): Call<UserCreationResponse?>? {
        return RetrofitInstance.api.signUp(userSignUP)
    }

    fun loginToServer(login: Login): Call<ServerResponse?>? {
        return RetrofitInstance.api.login(login)
    }

    fun finishTraining(token: String, workout: Workout): Call<Workout?>? {
        return RetrofitInstance.api.finishTraining(token, workout)
    }

    fun getActiveChallenges(autToken: String): Call<List<ActiveChallenge?>?>? {
        return RetrofitInstance.api.getActiveChallenges(autToken)
    }

    fun getCompletedChallenges(autToken: String): Call<List<ActiveChallenge?>?>? {
        return RetrofitInstance.api.getCompletedChallenges(autToken)
    }

    fun getAllWorkoutHistory(token: String): Call<List<Workout?>?>? {
        return RetrofitInstance.api.getAllWorkoutHistory(token)
    }

    fun getTop3Workout(token: String): Call<List<Workout?>?>? {
        return RetrofitInstance.api.getTop3Workout(token)
    }

    fun getUserDetails(token: String): Call<UserDetails?>? {
        return RetrofitInstance.api.getUserDetails(token)
    }

    fun postUserWeight(token: String, userWeight: UserWeight): Call<UserWeight?>? {
        return RetrofitInstance.api.postUserWeight(token, userWeight)
    }

    fun getUserWeight(token: String): Call<List<UserWeight?>?>? {
        return RetrofitInstance.api.getUserWeight(token)
    }

    fun putGoals(token: String, userGoals: UserGoals): Call<UserGoals?>? {
        return RetrofitInstance.api.putGoals(token, userGoals)
    }
}