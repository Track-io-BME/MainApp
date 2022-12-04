package hu.bme.aut.android.trackio.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ServerResponse(
    val token: String,
    val email :String
)