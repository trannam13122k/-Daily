package com.example.daily.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.daily.broadcast.AlarmReceiver
import com.example.daily.R
import com.example.daily.ui.activity.MainActivity
import com.example.daily.util.KeyWord
import java.util.*

class NotificationService : Service() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        startForegroundService()
        scheduleNotification()
    }

    private fun startForegroundService() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = KeyWord.foregroundServiceChannel
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(R.string.foregroundService.toString())
            .setContentText(R.string.running.toString())
            .setSmallIcon(R.drawable.icon_app)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle()
                .bigLargeIcon(null))
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, R.string.foregroundServiceChannel.toString(), NotificationManager.IMPORTANCE_DEFAULT
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        startForeground(1, notificationBuilder)
    }

    @SuppressLint("SuspiciousIndentation")
    private fun scheduleNotification() {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.action = KeyWord.myAction

        pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_MUTABLE  or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }
}


