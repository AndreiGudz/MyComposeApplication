package com.example.mycomposeapplication

import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationService : IntentService("NotificationService") {

    @SuppressLint("MissingPermission")
    override fun onHandleIntent(intent: Intent?) {
        when (intent?.action) {
            ACTION_START_TASK -> {
                // Имитация выполнения задачи
                Log.d("NotificationService", "Задача начата")

                // Создаем уведомление о выполнении задачи
                val notification = NotificationCompat.Builder(this, MainActivity.SERVICE_CHANNEL)
                    .setSmallIcon(android.R.drawable.ic_media_play)
                    .setContentTitle("Задача выполнена")
                    .setContentText("Сервис успешно выполнил задачу")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                with(NotificationManagerCompat.from(this)) {
                    notify(System.currentTimeMillis().toInt(), notification)
                }
            }
            ACTION_CANCEL_TASK -> {
                Log.d("NotificationService", "Задача отменена")

                val notification = NotificationCompat.Builder(this, MainActivity.SERVICE_CHANNEL)
                    .setSmallIcon(android.R.drawable.ic_media_pause)
                    .setContentTitle("Задача отменена")
                    .setContentText("Действие было отменено пользователем")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build()

                with(NotificationManagerCompat.from(this)) {
                    notify(System.currentTimeMillis().toInt(), notification)
                }
            }
        }
    }

    companion object {
        const val ACTION_START_TASK = "ACTION_START_TASK"
        const val ACTION_CANCEL_TASK = "ACTION_CANCEL_TASK"
    }
}