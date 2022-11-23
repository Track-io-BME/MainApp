package hu.bme.aut.android.trackio.data.roomentities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "activeChallenges_table")
data class ActiveChallenge(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val distance: Float,
    val sportType : SportType,
    val startDate : Long,
    val duration: SportDuration
){
    enum class SportType {
        WALKING, RUNNING, CYCLING;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): SportType? {
                var ret: SportType? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: SportType): Int {
                return category.ordinal
            }
        }
    }

    enum class SportDuration {
        DAILY, WEEKLY;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): SportDuration? {
                var ret: SportDuration? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: SportDuration): Int {
                return category.ordinal
            }
        }
    }
}


