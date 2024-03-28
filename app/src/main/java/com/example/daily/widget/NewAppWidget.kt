package com.example.daily.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import com.example.daily.R
import com.example.daily.database.Preferences
import com.example.daily.ui.activity.MainActivity
import com.example.daily.util.DataB
import com.example.daily.util.KeyWord


class NewAppWidget : AppWidgetProvider() {

    private lateinit var preferences: Preferences

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == KeyWord.actionOne) {
            updateRandomTextView(context)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        appWidgetIds.forEach { appWidgetId ->
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            views.setInt(R.id.widgetLayout, KeyWord.setBackgroundColor, Color.MAGENTA)
            preferences = Preferences.getInstance(context)
            var mListQuestion = preferences.getList(KeyWord.myListKey)

            if (mListQuestion != null) {
                if (mListQuestion.isNotEmpty()) {
                    val randomItem = mListQuestion.random()
                    views.setTextViewText(R.id.widgetTextView, randomItem)
                } else {
                    views.setTextViewText(R.id.widgetTextView, KeyWord.noItemsAvailable)
                }
            } else {
                mListQuestion = DataB.listDataLocal
                val randomItem = mListQuestion.random()
                views.setTextViewText(R.id.widgetTextView, randomItem)
            }
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_MUTABLE
            )
            views.setOnClickPendingIntent(R.id.widgetLayout, pendingIntent)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }


    private fun updateRandomTextView(context: Context?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val appWidgetIds = appWidgetManager.getAppWidgetIds(
            ComponentName(context!!, NewAppWidget::class.java)
        )

        appWidgetIds.forEach { appWidgetId ->
            val views = RemoteViews(context.packageName, R.layout.new_app_widget)
            preferences = Preferences.getInstance(context)
            var mListQuestion = preferences.getList(KeyWord.myListKey)
            if (mListQuestion != null) {
                mListQuestion = DataB.listDataLocal
                if (mListQuestion.isNotEmpty()) {
                    val randomItem = mListQuestion.random()
                    views.setTextViewText(R.id.widgetTextView, randomItem)
                } else {
                    views.setTextViewText(R.id.widgetTextView, KeyWord.noItemsAvailable)
                }
            } else {
                mListQuestion = DataB.listDataLocal
                val randomItem = mListQuestion.random()
                views.setTextViewText(R.id.widgetTextView, randomItem)
            }
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
