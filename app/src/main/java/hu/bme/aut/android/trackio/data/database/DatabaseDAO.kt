package hu.bme.aut.android.trackio.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout


@Dao
interface DatabaseDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActiveChallenge(activeChallenges: ActiveChallenge)

    @Query("SELECT * FROM activeChallenges_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<ActiveChallenge>>

    @Query("SELECT * FROM activeChallenges_table WHERE sportType= :sportType ORDER BY id ASC")
    fun getActiveChallenges(sportType: ActiveChallenge.SportType) : LiveData<List<ActiveChallenge>>

    @Query("DELETE FROM activeChallenges_table")
    fun deleteAllActiveChallenges()

    @Query("DELETE FROM dailyhistory_table")
    fun deleteAllDailyHistory()

    @Query("DELETE FROM userweight_table")
    fun deleteAllUserWeight()

    @Query("DELETE FROM workout_table")
    fun deleteWorkout()

    @Delete
    suspend fun deleteActiveChallenge(activeChallenges: ActiveChallenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Query("SELECT * FROM workout_table WHERE  sportType= :sportType ORDER BY id ASC")
    fun getWorkoutData(sportType: ActiveChallenge.SportType) : LiveData<List<Workout>>



}