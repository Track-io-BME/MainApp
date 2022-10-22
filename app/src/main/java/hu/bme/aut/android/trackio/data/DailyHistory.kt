package hu.bme.aut.android.trackio.data

data class DailyHistory(
    val id: Int,
    val UserId: Int,
    val date: Long,
    val steps: Int,
    val distance: Float,
    val calories: Int
)
