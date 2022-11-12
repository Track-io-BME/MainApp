package hu.bme.aut.android.trackio.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.R

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val INVALID_VALUE = -1
    private var _username = MutableLiveData("USERNAME")
    var username : LiveData<String> = _username
    private var _stepsGoal = MutableLiveData(INVALID_VALUE)
    var stepsGoal : LiveData<Int> = _stepsGoal
    private var _weightGoal = MutableLiveData(INVALID_VALUE.toFloat())
    var weightGoal : LiveData<Float> = _weightGoal
    private var _weight = MutableLiveData(INVALID_VALUE.toFloat())
    var weight : LiveData<Float> = _weight
    private var _height = MutableLiveData(INVALID_VALUE.toFloat())
    var height : LiveData<Float> = _height
    private var _gender = MutableLiveData(application.getString(R.string.male))
    var gender : LiveData<String> = _gender
    private var _birthDate = MutableLiveData(INVALID_VALUE.toLong())
    var birthDate : LiveData<Long> = _birthDate

}