package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import androidx.lifecycle.*
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.database.AppDatabase
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.network.InternetConnectivityChecker
import hu.bme.aut.android.trackio.repository.DbRepository
import hu.bme.aut.android.trackio.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    var time = 0L
    var distance = 0.0F
    var clearNeeded = false
    var currentWorkoutType = ActiveChallenge.SportType.WALKING
        set(value) {
            if (value != field) {
                if (time > 0) {
                    saveCurrentWorkout()
                    time = 0
                    distance = 0.0F
                    clearNeeded = true
                }
                field = value
            }
        }

    private val dbRepository: DbRepository
    private val networkRepository: NetworkRepository
    var activeWalkingChallengesFromDB: LiveData<List<ActiveChallenge>>
    var activeRunningChallengesFromDB: LiveData<List<ActiveChallenge>>
    var activeCyclingChallengesFromDB: LiveData<List<ActiveChallenge>>

    init {
        val databaseDAO = AppDatabase.getDatabase(application).databaseDAO()
        dbRepository = DbRepository(databaseDAO)
        networkRepository = NetworkRepository()
        if (InternetConnectivityChecker.isOnline()) {
            getActiveChallengesFromNetwork()
        }

        activeWalkingChallengesFromDB =
            dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.WALKING)
        activeRunningChallengesFromDB =
            dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.RUNNING)
        activeCyclingChallengesFromDB =
            dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.CYCLING)
    }

    fun addActiveChallenge(activeChallenges: ActiveChallenge) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addActiveChallenge(activeChallenges)
        }
    }

    fun deleteAllActiveChallenge() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteAllActiveChallenge()
        }
    }

    fun getActiveChallengesFromNetwork() {
        networkRepository.getActiveChallenges(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<List<ActiveChallenge?>?> {
                override fun onResponse(
                    call: Call<List<ActiveChallenge?>?>, response: Response<List<ActiveChallenge?>?>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            deleteAllActiveChallenge()
                            for (item in data) {
                                if (item != null) {
                                    addActiveChallenge(item)
                                }
                            }
                        }

                    }
                }

                override fun onFailure(call: Call<List<ActiveChallenge?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }

    fun saveCurrentWorkout() {
        val calories = when (currentWorkoutType) {
            ActiveChallenge.SportType.WALKING -> 40.25F * distance
            ActiveChallenge.SportType.RUNNING -> 62.5F * distance
            ActiveChallenge.SportType.CYCLING -> 32F * distance
        }

        networkRepository.finishTraining(
            SharedPrefConfig.getString(SharedPrefConfig.pref_token), Workout(
                0,
                Calendar.getInstance().timeInMillis,
                time,
                distance,
                distance / time * 1000.0F,
                calories,
                currentWorkoutType
            )
        )?.enqueue(object : Callback<Workout?> {
            override fun onResponse(
                call: Call<Workout?>, response: Response<Workout?>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null) {
                        addWorkout(data)
                    }
                }
            }

            override fun onFailure(call: Call<Workout?>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    private fun addWorkout(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addWorkout(workout)
        }
    }
}