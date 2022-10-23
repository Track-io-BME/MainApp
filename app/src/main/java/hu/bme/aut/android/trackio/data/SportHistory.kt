package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class SportHistory(
    val id: Int,
    val UserId: Int,
    val date: Long,
    val totalDuration: Long,
    val workoutDuration: Long,
    val steps: Int,
    val distance: Float,
    val averageSpeed: Float,
    val calories: Int,
    val elevation: Float,
    val category: Int
)
