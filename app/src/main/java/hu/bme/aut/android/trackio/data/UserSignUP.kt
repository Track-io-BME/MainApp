package hu.bme.aut.android.trackio.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserSignUP(
    val firstname : String,
    val lastname : String,
    var gender: String,
    val dateofbirth : String,
    val email : String,
    val height: Float,
    val weight : Float,
    val password : String
)