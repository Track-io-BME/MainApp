package hu.bme.aut.android.trackio.model

import android.app.*
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import hu.bme.aut.android.trackio.R
import hu.bme.aut.android.trackio.data.roomentities.ActiveChallenge

object WorkoutNotificationHelper {
    private const val NOTIFICATION_CHANNEL_ID = "location_service_notifications"
    private const val NOTIFICATION_CHANNEL_NAME = "Location Service notifications"
    const val WORKOUT_NOTIFICATION_ID = 76198319
    private lateinit var application: Application

    fun init(application: Application) {
        this.application = application
    }

    fun getWorkoutNotification(workoutType: ActiveChallenge.SportType): Notification {
        createNotificationChannel()
            val notificationIcon = when(workoutType) {
                ActiveChallenge.SportType.WALKING -> R.drawable.blue_walk
                ActiveChallenge.SportType.RUNNING -> R.drawable.blue_run
                ActiveChallenge.SportType.CYCLING -> R.drawable.blue_bike
            }
        return Notification.Builder(
                application,
                NOTIFICATION_CHANNEL_ID
            )
                .setContentTitle(application.getString(R.string.notification_message))
                .setSmallIcon(notificationIcon)
                .setCategory(NotificationCompat.CATEGORY_WORKOUT)
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setOnlyAlertOnce(true)
                .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager: NotificationManager =
                application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}