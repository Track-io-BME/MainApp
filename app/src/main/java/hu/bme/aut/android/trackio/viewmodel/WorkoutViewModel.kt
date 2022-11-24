package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.database.AppDatabase
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.repository.DbRepository
import hu.bme.aut.android.trackio.repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    var time = 0
    var distance = 0.0F
    var clearNeeded = false

    enum class WorkoutType {
        WALKING, RUNNING, CYCLING
    }
    var currentWorkoutType = WorkoutType.WALKING
        set(value) {
            if (value != field) {
                save()
                time = 0
                distance = 0.0F
                clearNeeded = true
                field = value
            }
        }

    //Challengekmegjelenítése
    private val dbRepository: DbRepository
    private val networkRepository: NetworkRepository
    var activeWalkingChallengesFromDB : LiveData<List<ActiveChallenge>>
    var activeRunningChallengesFromDB : LiveData<List<ActiveChallenge>>
    var activeCyclingChallengesFromDB : LiveData<List<ActiveChallenge>>

    init {
        val databaseDAO = AppDatabase.getDatabase(application).databaseDAO()
        dbRepository = DbRepository(databaseDAO)
        networkRepository = NetworkRepository()
        getActiveChallengesFromNetwork()
        activeWalkingChallengesFromDB = dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.WALKING)
        activeRunningChallengesFromDB = dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.RUNNING)
        activeCyclingChallengesFromDB = dbRepository.getActiveChallengeOfSportType(sportType = ActiveChallenge.SportType.CYCLING)
    }

    fun addActiveChallenge(activeChallenges: ActiveChallenge) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.addActiveChallenge(activeChallenges)
        }
    }

    fun deleteActiveChallenge(activeChallenges: ActiveChallenge) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.deleteActiveChallenge(activeChallenges)
        }
    }

    fun getActiveChallengesFromNetwork() {
        networkRepository.getActiveChallenges(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object :
                Callback<List<ActiveChallenge?>?> {
                override fun onResponse(
                    call: Call<List<ActiveChallenge?>?>,
                    response: Response<List<ActiveChallenge?>?>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            for (item in data) {
                                if (data != null) {
                                    if (item != null) {
                                        addActiveChallenge(item)
                                    }
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

    fun save() {
        Log.d("WorkoutViewModel", "SAVE")
        //TODO
    }
}