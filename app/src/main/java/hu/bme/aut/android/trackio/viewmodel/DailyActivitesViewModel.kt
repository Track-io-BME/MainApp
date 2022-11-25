package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.data.roomentities.Workout
import hu.bme.aut.android.trackio.repository.NetworkRepository
import hu.bme.aut.android.trackio.ui.workoutmenu.ActiveChallengesAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DailyActivitesViewModel(application: Application) : AndroidViewModel(application) {
    private val networkRepository: NetworkRepository = NetworkRepository()
    init {
        getCompletedChallenges()
    }

    fun getCompletedChallenges() : LiveData<List<ActiveChallenge?>?>{
        val completedChallenge = MutableLiveData<List<ActiveChallenge?>?>()
        networkRepository.getCompletedChallenges(SharedPrefConfig.getString(SharedPrefConfig.pref_token))?.enqueue(object :
            Callback<List<ActiveChallenge?>?>{
            override fun onResponse(
                call: Call<List<ActiveChallenge?>?>,
                response: Response<List<ActiveChallenge?>?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        completedChallenge.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<List<ActiveChallenge?>?>, t: Throwable) {
                t.printStackTrace()
            }

        })
        return completedChallenge
    }


}