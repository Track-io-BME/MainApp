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
    val totalduration : Long,
    val steps: Int,
    val distance: Float,
    val averageSpeed: Float,
    val calories: Int,
    val elevation: Float,
    val sportType: ActiveChallenge.SportType,
)
