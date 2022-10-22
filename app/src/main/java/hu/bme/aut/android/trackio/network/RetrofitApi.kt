package hu.bme.aut.android.trackio.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface RetrofitApi {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") emailAddress : String,
        @Field("password") password : String
    ):Call<String>

    @FormUrlEncoded
    @POST("signup")
    fun signUp(
        @Field("firstname") firstname : String,
        @Field("lastname") lastname : String,
        @Field("email") email: String,
        @Field("gender") gender : Int,
        @Field("weight") weight : Float,
        @Field("height") height : Float,
        @Field("dateofbirth") dateofbirth : Long,
        @Field("password") password : String
    ):Call<String>
}