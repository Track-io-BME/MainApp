package hu.bme.aut.android.trackio.data.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    @Delete
    suspend fun deleteActiveChallenge(activeChallenges: ActiveChallenge)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkout(workout: Workout)

    @Query("SELECT * FROM workout_table WHERE  sportType= :sportType ORDER BY id ASC")
    fun getWorkoutData(sportType: ActiveChallenge.SportType) : LiveData<List<Workout>>


}