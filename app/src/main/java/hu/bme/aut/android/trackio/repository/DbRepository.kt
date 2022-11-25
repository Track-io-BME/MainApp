package hu.bme.aut.android.trackio.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import hu.bme.aut.android.trackio.data.database.DatabaseDAO
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge

class DbRepository(private val databaseDAO : DatabaseDAO) {

    fun getActiveChallengeOfSportType(sportType: ActiveChallenge.SportType) : LiveData<List<ActiveChallenge>> {
        return databaseDAO.getActiveChallenges(sportType)
    }

    fun getAllActiveChallenge() : LiveData<List<ActiveChallenge>>{
        return databaseDAO.readAllData()
    }

    fun deleteAllActiveChallenge(){
        databaseDAO.deleteAllActiveChallenges()
    }

    suspend fun addActiveChallenge(activeChallenges: ActiveChallenge){
        databaseDAO.addActiveChallenge(activeChallenges)
    }

    suspend fun deleteActiveChallenge(activeChallenges: ActiveChallenge){
        databaseDAO.deleteActiveChallenge(activeChallenges)
    }
}