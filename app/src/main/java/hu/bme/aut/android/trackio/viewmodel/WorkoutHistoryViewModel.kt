package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WorkoutHistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()

    init {
        getWeekWorkoutHistory()
    }

    fun getWeekWorkoutHistory(): LiveData<List<Workout?>?> {
        val weekWorkout = MutableLiveData<List<Workout?>?>()
        networkRepository.getWeekWorkoutHistory(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<List<Workout?>?> {
                override fun onResponse(
                    call: Call<List<Workout?>?>, response: Response<List<Workout?>?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            weekWorkout.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<List<Workout?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return weekWorkout
    }

    fun getMonthWorkoutHistory(): LiveData<List<Workout?>?> {
        val monthWorkout = MutableLiveData<List<Workout?>?>()
        networkRepository.getMonthWorkoutHistory(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<List<Workout?>?> {
                override fun onResponse(
                    call: Call<List<Workout?>?>, response: Response<List<Workout?>?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            monthWorkout.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<List<Workout?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return monthWorkout
    }

    fun getAllWorkoutHistory(): LiveData<List<Workout?>?> {
        val allWorkout = MutableLiveData<List<Workout?>?>()
        networkRepository.getAllWorkoutHistory(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<List<Workout?>?> {
                override fun onResponse(
                    call: Call<List<Workout?>?>, response: Response<List<Workout?>?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            allWorkout.value = response.body()
                        }
                    }
                }

                override fun onFailure(call: Call<List<Workout?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return allWorkout
    }
}