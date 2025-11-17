package com.example.mycomposeapplication

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
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
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.RemoteInput

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
        MyButton("1. Добавить к оповещению кнопку;") {
            showNotificationWithAction(context)
        }

        MyButton("2-3. Уведомление с вводом текста и уведомлением") {
            showNotificationWithInputAndVibrate(context)
        }


    }
}

@SuppressLint("MissingPermission")
fun showNotificationWithAction(context: android.content.Context) {
    val intent = Intent(context, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val pendingIntent = PendingIntent.getActivity(context,0,intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, MainActivity.TASK_1)
        .setSmallIcon(android.R.drawable.ic_dialog_info)
        .setContentTitle("Уведомление с действием")
        .setContentText("Это уведомление с действием открывающим приложение")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)
        .addAction(android.R.drawable.star_on, "Открыть", pendingIntent)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(1, notification)
    }
}

@SuppressLint("MissingPermission")
fun showNotificationWithInputAndVibrate(context: android.content.Context) {
    val remoteInput: RemoteInput? = RemoteInput.Builder(ReplyReceiver.KEY_TEXT_REPLY)
        .setLabel("Введите ваш ответ...")
        .build()

    val intentReply = Intent(context, ReplyReceiver::class.java)
    val pendingIntentReply = PendingIntent.getBroadcast(
        context,
        1,
        intentReply,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val action: NotificationCompat.Action = NotificationCompat.Action.Builder(
        android.R.drawable.ic_dialog_email,
        "Ответить",
        pendingIntentReply
    ).addRemoteInput(remoteInput)
        .build()

    val notification = NotificationCompat.Builder(context, MainActivity.TASK_3)
        .setSmallIcon(android.R.drawable.ic_dialog_email)
        .setContentTitle("Ввести текст")
        .setContentText("Введите текст и он появится снизу")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
        .addAction(action)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(2, notification)
    }
}

@SuppressLint("MissingPermission")
private fun showFullScreenNotification(context: android.content.Context) {
    val fullScreenIntent = Intent(context, MainActivity::class.java)
    val fullScreenPendingIntent = PendingIntent.getActivity(
        context,
        2,
        fullScreenIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(context, MainActivity.TASK_4)
        .setContentTitle("Срочное уведомление!")
        .setContentText("Тапните, чтобы открыть приложение")
        .setSmallIcon(android.R.drawable.ic_dialog_alert)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setFullScreenIntent(fullScreenPendingIntent, true)
        .setCategory(NotificationCompat.CATEGORY_ALARM)
        .build()

    with(NotificationManagerCompat.from(context)) {
        notify(3, notification)
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