package hu.bme.aut.android.trackio.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDetails(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val height: Float,
    val sex: String,
    val birthDate: Long,
    val goalSteps: Int,
    val goalWeight: Float
)
