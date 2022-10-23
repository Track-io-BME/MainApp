package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class UserDetails(
    val id: Int,
    val UserId: Int,
    val height: Int,
    var gender: String,
    val birthDate: Long,
    val goalSteps: Int,
    val goalWeight: Int
)
