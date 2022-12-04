package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.UserDetails
import hu.bme.aut.android.trackio.data.database.AppDatabase
import hu.bme.aut.android.trackio.data.roomentities.UserWeight
import hu.bme.aut.android.trackio.repository.DbRepository
import hu.bme.aut.android.trackio.repository.NetworkRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class ProfileViewModel(application: Application) :
    AndroidViewModel(application),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val dbRepository: DbRepository
    private val networkRepository: NetworkRepository = NetworkRepository()

    init {
        val databaseDAO = AppDatabase.getDatabase(application).databaseDAO()
        dbRepository = DbRepository(databaseDAO)
        getUserDetails()
    }

    var username =
        MutableLiveData(
            "${SharedPrefConfig.getString(SharedPrefConfig.pref_first_name)} " +
                    SharedPrefConfig.getString(SharedPrefConfig.pref_last_name)
        )
    var gender = MutableLiveData(
        SharedPrefConfig.getString(
            SharedPrefConfig.pref_gender,
            application.getString(R.string.male)
        )
    )
    var height = MutableLiveData(SharedPrefConfig.getFloat(SharedPrefConfig.pref_height))
    var weight = MutableLiveData(SharedPrefConfig.getFloat(SharedPrefConfig.pref_weight))
    var weightGoal = MutableLiveData(SharedPrefConfig.getFloat(SharedPrefConfig.pref_weight_goal))
    var stepsGoal = MutableLiveData(SharedPrefConfig.getInt(SharedPrefConfig.pref_steps_goal))
    var birthDate = MutableLiveData(SharedPrefConfig.getString(SharedPrefConfig.pref_birth_date))

    override fun onSharedPreferenceChanged(
        p0: SharedPreferences?,
        p1: String?
    ) {
        when (p1) {
            SharedPrefConfig.pref_first_name ->
                username.value =
                    "${SharedPrefConfig.getString(SharedPrefConfig.pref_first_name)} " +
                            SharedPrefConfig.getString(SharedPrefConfig.pref_last_name)
            SharedPrefConfig.pref_last_name ->
                username.value =
                    "${SharedPrefConfig.getString(SharedPrefConfig.pref_first_name)} " +
                            SharedPrefConfig.getString(SharedPrefConfig.pref_last_name)
            SharedPrefConfig.pref_gender ->
                gender.value = SharedPrefConfig.getString(SharedPrefConfig.pref_gender)
            SharedPrefConfig.pref_height ->
                height.value = SharedPrefConfig.getFloat(SharedPrefConfig.pref_height)
            SharedPrefConfig.pref_weight ->
                weight.value = SharedPrefConfig.getFloat(SharedPrefConfig.pref_weight)
            SharedPrefConfig.pref_weight_goal ->
                weightGoal.value = SharedPrefConfig.getFloat(SharedPrefConfig.pref_weight_goal)
            SharedPrefConfig.pref_steps_goal ->
                stepsGoal.value = SharedPrefConfig.getInt(SharedPrefConfig.pref_steps_goal)
            SharedPrefConfig.pref_birth_date ->
                birthDate.value = SharedPrefConfig.getString(SharedPrefConfig.pref_birth_date)
            else ->
                return
        }
    }

    private fun getUserDetails() {
        networkRepository.getUserDetails(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<UserDetails?> {
                override fun onResponse(
                    call: Call<UserDetails?>,
                    response: Response<UserDetails?>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            SharedPrefConfig.put(
                                SharedPrefConfig.pref_birth_date,
                                SimpleDateFormat(
                                    "yyyy.MM.dd",
                                    Locale.getDefault()
                                ).format(data.birthDate)
                            )
                            SharedPrefConfig.put(SharedPrefConfig.pref_first_name, data.firstName)
                            SharedPrefConfig.put(SharedPrefConfig.pref_last_name, data.lastName)
                            SharedPrefConfig.put(SharedPrefConfig.pref_steps_goal, data.goalSteps)
                            SharedPrefConfig.put(SharedPrefConfig.pref_weight_goal, data.goalWeight)
                            SharedPrefConfig.put(SharedPrefConfig.pref_height, data.height)
                            if (data.sex == getApplication<Application>().getString(R.string.male)
                                    .lowercase()
                            ) {
                                SharedPrefConfig.put(
                                    SharedPrefConfig.pref_gender,
                                    getApplication<Application>().getString(R.string.male)
                                )
                            } else {
                                SharedPrefConfig.put(
                                    SharedPrefConfig.pref_gender,
                                    getApplication<Application>().getString(R.string.female)
                                )
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UserDetails?>, t: Throwable) {
                    t.printStackTrace()
                }
            })

        networkRepository.getUserWeight(SharedPrefConfig.getString(SharedPrefConfig.pref_token))
            ?.enqueue(object : Callback<List<UserWeight?>?> {
                override fun onResponse(
                    call: Call<List<UserWeight?>?>,
                    response: Response<List<UserWeight?>?>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            data.last()
                                ?.let {
                                    SharedPrefConfig.put(
                                        SharedPrefConfig.pref_weight,
                                        it.weight
                                    )
                                }
                        }
                    }
                }

                override fun onFailure(call: Call<List<UserWeight?>?>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}