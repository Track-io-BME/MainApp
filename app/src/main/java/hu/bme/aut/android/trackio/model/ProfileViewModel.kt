package hu.bme.aut.android.trackio.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.R

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val INVALID_VALUE = -1
    var username = MutableLiveData("USERNAME")
    var stepsGoal = MutableLiveData(INVALID_VALUE)
    var weightGoal = MutableLiveData(INVALID_VALUE.toFloat())
    var weight = MutableLiveData(INVALID_VALUE.toFloat())
    var height = MutableLiveData(INVALID_VALUE.toFloat())
    var gender = MutableLiveData(application.getString(R.string.male))
    var birthDate = MutableLiveData(INVALID_VALUE.toLong())

}