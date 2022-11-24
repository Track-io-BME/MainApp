package hu.bme.aut.android.trackio.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.trackio.data.roomentities.*

@Database(
    entities = [
        ActiveChallenge::class,
        DailyHistory::class,
        FinishedChallenges::class,
        Workout::class,
        UserWeight::class
    ], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun databaseDAO(): DatabaseDAO

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context) : AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database")
                    .build()
                INSTANCE= instance
                return instance
            }
        }
    }
}