package hu.bme.aut.android.trackio.data

import android.content.Context
import android.content.SharedPreferences

object SharedPrefConfig {
    private const val PREFERENCE_NAME  = "hu.bme.aut.android.trackio"
    const val pref_signed_in   = "signedIn"
    const val pref_username    = "username"
    const val pref_gender      = "gender"
    const val pref_height      = "height"
    const val pref_weight      = "weight"
    const val pref_weight_goal = "weightGoal"
    const val pref_steps_goal  = "stepsGoal"
    const val pref_birth_date  = "birthDate"
    const val pref_token  = "token"
    const val pref_email  = "email"
    const val pref_expiry_date= "expiryDate"
    const val pref_password= "password"


    private lateinit var mPreferences: SharedPreferences
    private var mEditor: SharedPreferences.Editor? = null

    fun init(context: Context) {
        mPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    }

    private fun doEdit() {
        if (mEditor == null)
            mEditor = mPreferences.edit()
    }

    private fun doCommit() {
        if (mEditor != null) {
            mEditor?.commit()
            mEditor = null
        }
    }

    fun deleteAll(){
        mPreferences.edit().clear().commit()
    }
//
//    fun saveProfile(profile: UserProfile) {
//        doEdit()
//        mEditor?.putBoolean(pref_signed_in, true)
//        mEditor?.putString(pref_username, profile.username)
//        mEditor?.putString(pref_gender, profile.gender)
//        mEditor?.putFloat(pref_height, profile.height)
//        mEditor?.putFloat(pref_weight, profile.weight)
//        mEditor?.putFloat(pref_weight_goal, profile.weightGoal)
//        mEditor?.putFloat(pref_weight_goal, profile.weightGoal)
//        mEditor?.putInt(pref_steps_goal, profile.stepsGoal)
//        mEditor?.putLong(pref_birth_date, profile.birthDate)
//        doCommit()
//    }
//
//    fun loadProfile() : UserProfile? {
//        val data = mPreferences.all
//        val username   = data[pref_username]?.toString()                  ?: return null
//        val gender     = data[pref_username]?.toString()                  ?: return null
//        val height     = data[pref_username]?.toString()?.toFloatOrNull() ?: return null
//        val weight     = data[pref_username]?.toString()?.toFloatOrNull() ?: return null
//        val weightGoal = data[pref_username]?.toString()?.toFloatOrNull() ?: return null
//        val stepsGoal  = data[pref_username]?.toString()?.toIntOrNull()   ?: return null
//        val birthDate  = data[pref_height]?.toString()?.toLongOrNull()    ?: return null
//
//        return UserProfile(
//            username, gender, height, weight, weightGoal, stepsGoal, birthDate
//        )
//    }

    fun getString(prefKey: String, defaultValue: String = "") : String {
        return mPreferences.getString(prefKey, defaultValue).toString()
    }

    fun getInt(prefKey: String, defaultValue: Int = 0) : Int {
        return mPreferences.getInt(prefKey, defaultValue)
    }

    fun getFloat(prefKey: String, defaultValue: Float = 0F) : Float {
        return mPreferences.getFloat(prefKey, defaultValue)
    }

    fun getLong(prefKey: String, defaultValue: Long = 0) : Long {
        return mPreferences.getLong(prefKey, defaultValue)
    }

    fun getBoolean(prefKey: String, defaultValue: Boolean = false) : Boolean {
        return mPreferences.getBoolean(prefKey, defaultValue)
    }

    fun put(prefKey: String, value: String)  {
        doEdit()
        mEditor?.putString(prefKey, value)
        doCommit()
    }

    fun put(prefKey: String, value: Int) {
        doEdit()
        mEditor?.putInt(prefKey, value)
        doCommit()
    }

    fun put(prefKey: String, value: Float) {
        doEdit()
        mEditor?.putFloat(prefKey, value)
        doCommit()
    }

    fun put(prefKey: String, value: Long) {
        doEdit()
        mEditor?.putLong(prefKey, value)
        doCommit()
    }

    fun put(prefKey: String, value: Boolean) {
        doEdit()
        mEditor?.putBoolean(prefKey, value)
        doCommit()
    }

    fun clear() {
        doEdit()
        mEditor?.clear()
        doCommit()
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        mPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }
}