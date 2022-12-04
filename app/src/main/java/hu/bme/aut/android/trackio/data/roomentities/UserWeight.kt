package hu.bme.aut.android.trackio.data.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "userweight_table")
data class UserWeight(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val date: Long,
    val weight: Float
)
