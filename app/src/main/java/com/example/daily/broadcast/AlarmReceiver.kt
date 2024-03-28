package com.example.daily.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.app.*
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.daily.R
import com.example.daily.database.Preferences
import com.example.daily.util.DataB
import com.example.daily.util.KeyWord
import com.example.daily.util.Utils
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    private lateinit var preferences: Preferences

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == KeyWord.myAction) {
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            if (notificationManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        Utils.CHANNEL_ID,
                        "Notification",
                        NotificationManager.IMPORTANCE_HIGH
                    )
                    channel.description = KeyWord.lovely
                    notificationManager.createNotificationChannel(channel)
                }

                val randomMessage = getRandomMessage(context)

                val contentView = RemoteViews(context.packageName, R.layout.notification_layout)
                contentView.setTextViewText(R.id.notification_title, "Daily")
                contentView.setTextViewText(R.id.notification_message, randomMessage)

                val builder = NotificationCompat.Builder(context, Utils.CHANNEL_ID)
                    .setSmallIcon(R.drawable.icon_app)
                    .setCustomContentView(contentView)
                    .setColor(Color.RED)
                    .setCategory(NotificationCompat.CATEGORY_ALARM)

                notificationManager.notify(getNotificationId(), builder.build())
            }
        }
    }

    private fun getNotificationId(): Int {
        return System.currentTimeMillis().toInt()
    }

    private fun getRandomMessage(context: Context): String {
        preferences = Preferences.getInstance(context)
        var mListquestion = preferences.getList(KeyWord.myListKey)
        if (mListquestion == null) {
            mListquestion = DataB.listDataLocal
        }
        val random = Random()
        val randomIndex = random.nextInt(mListquestion.size)
        return mListquestion[randomIndex]
    }
}