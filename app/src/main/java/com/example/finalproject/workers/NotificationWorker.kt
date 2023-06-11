package com.example.finalproject.workers

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.finalproject.MainActivity
import com.example.finalproject.R

class NotificationWorker(appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        // Здесь вы можете вызвать функцию, которая отправляет уведомление.
        showNotification("Задача выполнена")

        return Result.success()
    }

    private fun showNotification(message: String) {
        val notificationId = inputData.getInt("task_id",0)
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.sendNotification(notificationId, message, applicationContext)
    }
}

fun NotificationManager.sendNotification(id: Int,messageBody: String, applicationContext: Context) {
    val intent = Intent(applicationContext, MainActivity::class.java)
    val NOTIFICATION_ID = id
    val pendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.main_notification_channel_task)
    )
        .setSmallIcon(R.drawable.app_icon_small)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(messageBody)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    notify(NOTIFICATION_ID, builder.build())
}