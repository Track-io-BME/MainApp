package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class UserWeight(
    val id: Int,
    val userId: Int,
    val date: Long,
    val weight: Float
)
