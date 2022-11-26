package hu.bme.aut.android.trackio.network

import hu.bme.aut.android.trackio.data.*
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.UserWeight
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

    @GET("all")
    fun getAllWorkoutHistory(@Header("Authorization") token: String) : Call<List<Workout?>?>?

    @GET("/userWorkout/top3")
    fun getTop3Workout(@Header("Authorization") token: String) : Call<List<Workout?>?>?


    //USERDETAILS dolgok
    @GET("/userDetails/all")
    fun getUserDetails(@Header("Authorization") token: String) : Call<UserDetails?>?

    @POST("/userDetails/weight")
    fun postUserWeight(@Header("Authorization") token: String,@Body userWeight: UserWeight)  : Call<UserWeight?>?

    @GET("userDetails/weight")
    fun getUserWeight(@Header("Authorization") token: String): Call<List<UserWeight?>?>?

    @PUT("userDetails/goals")
    fun putGoals(@Header("Authorization") token: String,@Body userGoals: UserGoals) : Call<UserGoals?>?










}