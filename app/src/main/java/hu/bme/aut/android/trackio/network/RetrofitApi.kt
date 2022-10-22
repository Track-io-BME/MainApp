package hu.bme.aut.android.trackio.network

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitApi {
    @GET("btn")
    fun getData(): Call<List<MyDataItem>>

    @FormUrlEncoded
    @POST("posts")
    fun SendAndGetPost(
        @Field("name") userName: String?,
        @Field("password") userPass: String?
    ):Call<MyDataItem?>?


}