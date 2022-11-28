package hu.bme.aut.android.trackio.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserGoals(
    val goalSteps: Int,
    val goalWeight: Float
)