package com.example.glassappwidget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

class LauncherWidget : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(
                context.packageName,
                R.layout.glass_widget
            )

            setAppClick(
                context, views, R.id.btn_chatgpt,
                "com.openai.chatgpt", 101
            )

            setAppClick(
                context, views, R.id.btn_gemini,
                "com.google.android.apps.bard", 102
            )

            setAppClick(
                context, views, R.id.btn_mtmanager,
                "bin.mt.plus", 103
            )

            setAppClick(
                context, views, R.id.btn_apktool,
                "ru.maximoff.apktool", 104
            )

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    private fun setAppClick(
        context: Context,
        views: RemoteViews,
        viewId: Int,
        packageName: String,
        requestCode: Int
    ) {
        val launchIntent =
            context.packageManager.getLaunchIntentForPackage(packageName)
                ?: Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$packageName")
                )

        launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE
        )

        views.setOnClickPendingIntent(viewId, pendingIntent)
    }
}
