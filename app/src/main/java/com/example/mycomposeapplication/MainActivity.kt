package com.example.mycomposeapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

/**
 * Практическая работа №32.
 * Тема: Оповещения с действиями.
 * 1. Добавить к оповещению кнопку;
 * 2. Добавить к оповещению поле для ввода текста;
 * 3. Добавить к оповещению уникальную вибрацию;
 * 4. Добавить возможность открыть приложение из оповещения из экрана блокировки.
 */

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
        createNotificationChannels()
        setContent { MyContent() }
    }

    // Функция для создания каналов уведомлений
    private fun createNotificationChannels() {
        // Канал 1: для базовых уведомлений
        val basicChannel = NotificationChannel(
            BASIC_CHANNEL,
            "Базовые уведомления",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Канал для простых уведомлений"
        }

        // Канал 2: для уведомлений, открывающих приложение
        val appChannel = NotificationChannel(
            APP_CHANNEL,
            "Уведомления приложения",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Канал для уведомлений, которые открывают приложение"
        }

        // Канал 3: для уведомлений с действиями
        val serviceChannel = NotificationChannel(
            SERVICE_CHANNEL,
            "Уведомления с действиями",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Канал для уведомлений с кнопками действий"
        }

        // Канал 4: для уведомлений на экране блокировки
        val lockScreenChannel = NotificationChannel(
            LOCK_SCREEN_CHANNEL,
            "Уведомления блокировки",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Канал для уведомлений на экране блокировки"
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannels(
            listOf(basicChannel, appChannel, serviceChannel, lockScreenChannel)
        )
    }

    companion object {
        const val BASIC_CHANNEL = "basic_channel"
        const val APP_CHANNEL = "app_channel"
        const val SERVICE_CHANNEL = "service_channel"
        const val LOCK_SCREEN_CHANNEL = "lock_screen_channel"
    }
}

@Composable
fun MyContent() {
    MyComposeApplicationTheme {
        Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NotificationContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}