package hu.bme.aut.android.trackio.data.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName ="workout_table")
data class Workout(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val date: Long,
    val totalDuration : Long,
    val distance: Float,
    val averageSpeed: Float,
    val calories: Float,
    val sportType: ActiveChallenge.SportType
)
