package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class User(
    val id: Int,
    val username: String,
    val password: String,
    val Authentication: String,
    val emailAddress: String,
)