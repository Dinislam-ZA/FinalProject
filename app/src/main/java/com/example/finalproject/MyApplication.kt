package com.example.finalproject

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channelId = getString(R.string.main_notification_channel_task)
        val channelName = "Task notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val descriptionText = "This channel used to send task notifications"

        val channel = NotificationChannel(channelId, channelName, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }
}
