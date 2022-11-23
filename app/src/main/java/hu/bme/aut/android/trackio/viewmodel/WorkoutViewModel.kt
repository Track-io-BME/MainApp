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

    //Workouthoz
    enum class WorkoutType {
        WALKING, RUNNING, CYCLING
    }

    var distance = 0.0F
    var currentWorkoutType = WorkoutType.WALKING
    private lateinit var timer: Timer
    private val _time = MutableLiveData(0)
    val time: LiveData<Int> = _time
    private var _timerRunning = MutableLiveData(false)
    val timerRunning: LiveData<Boolean> = _timerRunning

    fun startStop() {
        if (_timerRunning.value == true) {
            stopTimer()
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        timer = Timer()
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    _time.postValue(_time.value?.plus(1))
                }
            },
            0,
            1000
        )
        _timerRunning.value = true
    }

    private fun stopTimer() {
        timer.cancel()
        timer.purge()
        _timerRunning.value = false
    }
}