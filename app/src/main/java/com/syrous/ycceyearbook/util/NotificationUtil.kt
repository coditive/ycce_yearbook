package com.syrous.ycceyearbook.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.syrous.ycceyearbook.R

private val NOTIFICATION_ID = 41414141

fun sendNotification(applicationContext: Context, messageTitle: String, messageBody: String) {
    val builder = NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.yearbook_channel)
        )
            .setContentTitle(messageTitle)
            .setContentText(messageBody)
            .setAutoCancel(true)

        getNotificationManager(applicationContext).notify(NOTIFICATION_ID, builder.build())
}

private fun getNotificationManager(applicationContext: Context): NotificationManager {
        return ContextCompat.getSystemService(applicationContext,
            NotificationManager::class.java) as NotificationManager
}
