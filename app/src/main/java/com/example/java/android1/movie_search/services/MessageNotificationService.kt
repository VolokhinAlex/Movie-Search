package com.example.java.android1.movie_search.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessageNotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("TAG_CHECKER", message.from.toString())
        val remoteMessage = message.data
        if (remoteMessage.isNotEmpty()) {
            onHandleDataMessage(remoteMessage.toMap())
            Log.e("TAG_CHECKER", remoteMessage.toString())
        }
    }

    private fun onHandleDataMessage(remoteData: Map<String, String>) {
        val title = remoteData[PUSH_KEY_TITLE]
        val message = remoteData[PUSH_KEY_MESSAGE]
        if (!title.isNullOrBlank() && !message.isNullOrBlank()) {
            onShowNotification(title, message)
        }
    }

    private fun onShowNotification(title: String, message: String) {
        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .apply {
                setSmallIcon(com.google.firebase.installations.R.drawable.notification_icon_background)
                setContentTitle(title)
                setContentText(message)
                priority = NotificationCompat.PRIORITY_MAX
            }

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            onCreateNotificationChannel(notificationManager)
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

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