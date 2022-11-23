package hu.bme.aut.android.trackio.network

import hu.bme.aut.android.trackio.data.AutToken
import hu.bme.aut.android.trackio.data.Login
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TrackAPI {

    @POST("login")
    fun login(@Body login: Login) : Call<AutToken?>?


    @GET("/challenges/getActiveChallenges")
    fun getActiveChallenges(@Header("Authorization") token: String) : Call<List<ActiveChallenge?>?>?
}