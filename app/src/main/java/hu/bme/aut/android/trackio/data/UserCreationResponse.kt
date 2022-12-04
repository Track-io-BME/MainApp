package hu.bme.aut.android.trackio.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserCreationResponse(
    val message: String,
    val email :String
)