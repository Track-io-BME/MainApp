package hu.bme.aut.android.trackio.data.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "dailyhistory_table")
data class DailyHistory(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val UserId: Int,
    val date: Long,
    val steps: Int,
    val distance: Float,
    val calories: Int
)
