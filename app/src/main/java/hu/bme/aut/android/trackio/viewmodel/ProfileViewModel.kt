package hu.bme.aut.android.trackio.viewmodel

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.SharedPrefConfig
import hu.bme.aut.android.trackio.data.database.AppDatabase
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge
import hu.bme.aut.android.trackio.repository.DbRepository
import hu.bme.aut.android.trackio.repository.NetworkRepository

class ProfileViewModel(application: Application) :
    AndroidViewModel(application),
    SharedPreferences.OnSharedPreferenceChangeListener {

    private val dbRepository: DbRepository

    init {
        val databaseDAO = AppDatabase.getDatabase(application).databaseDAO()
        dbRepository = DbRepository(databaseDAO)
    }

    var username =
        MutableLiveData(SharedPrefConfig.getString(SharedPrefConfig.pref_username, "USERNAME"))
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
    var birthDate = MutableLiveData(SharedPrefConfig.getLong(SharedPrefConfig.pref_birth_date))

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        when (p1) {
            SharedPrefConfig.pref_username ->
                username.value = SharedPrefConfig.getString(SharedPrefConfig.pref_username)
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
                birthDate.value = SharedPrefConfig.getLong(SharedPrefConfig.pref_birth_date)
            else ->
                return
        }
    }


}