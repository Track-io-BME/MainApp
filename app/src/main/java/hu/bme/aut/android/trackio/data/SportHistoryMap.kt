package hu.bme.aut.android.trackio.data

import androidx.room.Entity

@Entity
data class SportHistoryMap(
    val id: Int,
    val SportHistoryId: Int,
    val map: Int
)
