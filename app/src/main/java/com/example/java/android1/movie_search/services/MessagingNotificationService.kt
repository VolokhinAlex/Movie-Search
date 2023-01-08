package com.example.java.android1.movie_search.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.java.android1.movie_search.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        val remoteMessage = message.data
        if (remoteMessage.isNotEmpty()) {
            onHandleDataMessage(remoteMessage.toMap())
        }
    }

    /**
     * Method for getting the title and text from the map
     */

    private fun onHandleDataMessage(remoteData: Map<String, String>) {
        val title = remoteData[PUSH_KEY_TITLE]
        val message = remoteData[PUSH_KEY_MESSAGE]
        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            onShowNotification(title, message)
        }
    }

    /**
     * Method for displaying notifications
     */

    private fun onShowNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .apply {
                setSmallIcon(R.mipmap.ic_launcher_round)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_DEFAULT
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onCreateNotificationChannel(notificationManager)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    /**
     * A method for creating a notification channel. Works only on Android 8.0 Oreo and above.
     * The channels below will not be used and the usual notifications will be used
     */

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onCreateNotificationChannel(manager: NotificationManager) {
        val name = "Movie Notification Channel"
        val descriptionText = "Movie Notification Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        manager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    companion object {
        private const val PUSH_KEY_TITLE = "title"
        private const val PUSH_KEY_MESSAGE = "message"
        private const val CHANNEL_ID = "movie club notification"
        private const val NOTIFICATION_ID = 1
    }
}