package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class DailyHistory(
    val id: Int,
    val UserId: Int,
    val date: Long,
    val steps: Int,
    val distance: Float,
    val calories: Int
)
