package hu.bme.aut.android.trackio.network

import hu.bme.aut.android.trackio.data.ServerResponse
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.UserCreationResponse
import hu.bme.aut.android.trackio.data.UserSignUP
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import retrofit2.Call
import retrofit2.http.*

interface TrackAPI {

    @PUT("signup")
    fun signUp(@Body userSignUP: UserSignUP) : Call<UserCreationResponse?>?

    @POST("login")
    fun login(@Body login: Login) : Call<ServerResponse?>?

    @POST("/userWorkout/finishTraining")
    fun finishTraining(@Header("Authorization") token: String, @Body workout: Workout) : Call<Workout?>?

    @GET("/challenges/getActiveChallenges")
    fun getActiveChallenges(@Header("Authorization") token: String) : Call<List<ActiveChallenge?>?>?

    @GET("/challenges/getCompletedChallenges")
    fun getCompletedChallenges(@Header("Authorization") token: String) : Call<List<ActiveChallenge?>?>?

    @GET("/userWorkout/top3")
    fun getTop3Workout(@Header("Authorization") token: String) : Call<List<Workout?>?>?
}