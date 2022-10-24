package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class UserChallenges(
    val id: Int,
    val userId: Int,
    val challengeId: Int
)
