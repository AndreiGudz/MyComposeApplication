package com.example.mycomposeapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@Composable
fun NotificationContent() {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Тест уведомлений",
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Пункт 1: Кнопка для базового уведомления
        MyButton("1. Базовое уведомление") {
            showBasicNotification(context)
        }

        // Пункт 2: Кнопка для уведомления, открывающего приложение
        MyButton("2. Открыть приложение") {
            showAppOpeningNotification(context)
        }

        // Пункт 3: Кнопка для уведомления с действием
        MyButton("3. Уведомление с действием") {
            showServiceNotification(context)
        }

        // Пункт 5: Кнопка для уведомления на экране блокировки
        MyButton("5. На экран блокировки") {
            showLockScreenNotification(context)
        }
    }
}

// Пункт 1: Функция для создания базового уведомления
@SuppressLint("MissingPermission")
fun showBasicNotification(context: android.content.Context) {
    val notification = NotificationCompat.Builder(context, MainActivity.BASIC_CHANNEL)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Простое уведомление")
        .setContentText("Это тестовое уведомление")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(1, notification)
    }
}

// Пункт 2: Функция для уведомления, открывающего приложение
@SuppressLint("MissingPermission")
fun showAppOpeningNotification(context: android.content.Context) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(context,0,intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, MainActivity.APP_CHANNEL)
        .setSmallIcon(android.R.drawable.ic_dialog_alert)
        .setContentTitle("Открыть приложение")
        .setContentText("Нажмите, чтобы открыть приложение")
        .setContentIntent(pendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(2, notification)
    }
}

// Пункт 3: Функция для уведомления с действием
@SuppressLint("MissingPermission")
fun showServiceNotification(context: android.content.Context) {
    val startTaskIntent  = Intent(context, NotificationService::class.java).apply {
        action = NotificationService.ACTION_START_TASK
    }

    val startTaskPendingIntent = PendingIntent.getService(context, 0, startTaskIntent ,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val cancelTaskIntent = Intent(context, NotificationService::class.java).apply {
        action = NotificationService.ACTION_CANCEL_TASK
    }

    val cancelTaskPendingIntent = PendingIntent.getService(context,1, cancelTaskIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, MainActivity.SERVICE_CHANNEL)
        .setSmallIcon(android.R.drawable.ic_media_play)
        .setContentTitle("Выполнить задачу")
        .setContentText("Нажмите для выполнения фоновой задачи")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .addAction(android.R.drawable.ic_media_play,"Выполнить", startTaskPendingIntent)
        .addAction(android.R.drawable.ic_media_pause,"Отменить", cancelTaskPendingIntent)
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(3, notification)
    }
}

// Пункт 5: Функция для уведомления на экране блокировки
@SuppressLint("MissingPermission")
fun showLockScreenNotification(context: android.content.Context) {
    val notification = NotificationCompat.Builder(context, MainActivity.LOCK_SCREEN_CHANNEL)
        .setSmallIcon(android.R.drawable.ic_lock_lock)
        .setContentTitle("Уведомление на блокировке")
        .setContentText("Это видно на заблокированном экране")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // Видимо на экране блокировки
        .setAutoCancel(true)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(4, notification)
    }
}

@Composable
fun MyButton(text: String, onClick: ()-> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = text)
    }
}