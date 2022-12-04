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

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()

    init {
        getTop3Workout()
    }

    fun getTop3Workout(): LiveData<List<Workout?>?> {
        val top3Workout = MutableLiveData<List<Workout?>?>()
        networkRepository.getTop3Workout(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object :
                Callback<List<Workout?>?> {
                override fun onResponse(
                    call: Call<List<Workout?>?>,
                    response: Response<List<Workout?>?>
                ) {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            top3Workout.value = response.body()
                        }
                    }

                }

                override fun onFailure(call: Call<List<Workout?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        return top3Workout
    }
}