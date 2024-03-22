package com.example.daily.util

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import com.example.daily.R
import com.example.daily.database.Preferences
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewAppWidget : AppWidgetProvider() {
//
//    override fun onUpdate(
//        context: Context,
//        appWidgetManager: AppWidgetManager,
//        appWidgetIds: IntArray
//    ) {
//
//        for (appWidgetId in appWidgetIds) {
//            updateAppWidget(context, appWidgetManager, appWidgetId)
//        }
//    }
//
//    override fun onEnabled(context: Context) {
//        // Enter relevant functionality for when the first widget is created
//    }
//
//    override fun onDisabled(context: Context) {
//        // Enter relevant functionality for when the last widget is disabled
//    }
//}
//
//internal fun updateAppWidget(
//    context: Context,
//    appWidgetManager: AppWidgetManager,
//    appWidgetId: Int
//) {
//    val widgetText = context.getString(R.string.appwidget_text)
//    val views = RemoteViews(context.packageName, R.layout.new_app_widget)
//    views.setTextViewText(R.id.appwidget_text, widgetText)
//
//    appWidgetManager.updateAppWidget(appWidgetId, views)
//}

private lateinit var preferences: Preferences

        override fun onReceive(context: Context?, intent: Intent?) {
            super.onReceive(context, intent)

//            intent?.action?.let {
//                when (it) {
//                    "ACTION_OPEN_WEBSITE" -> {
//                        Intent(
//                            Intent.ACTION_VIEW,
//                            Uri.parse("https://www.youtube.com/@codingwithcat")
//                        ).apply {
//                            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            context?.startActivity(this)
//                        }
//                    }
//                    else -> null
//                }
//            }
            if (intent?.action == "ACTION_OPEN_WEBSITE") {
                val openWebsiteIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@codingwithcat"))
                openWebsiteIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context?.startActivity(openWebsiteIntent)
            }

        }

        override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetIds: IntArray
        ) {

            appWidgetIds?.forEach { appWidgetId ->

                val views = RemoteViews(context.packageName, R.layout.new_app_widget)
                views.setInt(R.id.widgetLayout, "setBackgroundColor", R.color.color_sixteen)

//                Intent(context, javaClass).apply {
//                    action = "ACTION_OPEN_WEBSITE"
//                    val pendingIntent = PendingIntent.getBroadcast(context, 999, this, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
//                    views.setOnClickPendingIntent(R.id.clickButton, pendingIntent)
//
//                    val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
//                    views.setTextViewText(R.id.appwidget_text, "Thời gian hiện tại: $currentTime")
//                }


//                val intent = Intent(context, javaClass)
//                intent.action = "ACTION_OPEN_WEBSITE"
//                val pendingIntent = PendingIntent.getBroadcast(context, 999, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
//
//                views.setOnClickPendingIntent(R.id.clickButton, pendingIntent)
//
//                val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
//                views.setTextViewText(R.id.appwidget_text, "Thời gian hiện tại: $currentTime")
                preferences = Preferences.getInstance(context)

                var lits : List<String>? = null


                lits= DataB.listDataLocal

                Log.e("bbbbbb", "onUpdate: +${lits}" )

                appWidgetManager?.updateAppWidget(appWidgetId, views)
            }
        }

}