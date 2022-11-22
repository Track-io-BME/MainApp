package hu.bme.aut.android.trackio.data.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName ="sporthistrory_table")
data class SportHistory(
    @PrimaryKey(autoGenerate = true)
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
    val category: Int,
    val map: Int
)
