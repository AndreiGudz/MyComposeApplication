package com.example.mycomposeapplication

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme
import kotlin.getValue

/**
 * Практическая работа №32.
 * Тема: Оповещения с действиями.
 * 1. Добавить к оповещению кнопку;
 * 2. Добавить к оповещению поле для ввода текста;
 * 3. Добавить к оповещению уникальную вибрацию;
 * 4. Добавить возможность открыть приложение из оповещения из экрана блокировки.
 */

const val NOTIFICATION_ID = "notification_id"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getPermissionsIfNeed()
        createNotificationChannels()
        val notificationId = intent.getIntExtra(NOTIFICATION_ID, -1)
        if (notificationId != -1) {
            NotificationManagerCompat.from(this).cancel(notificationId)
        }

        setContent { MyContent() }
    }

    private fun getPermissionsIfNeed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permissions = listOf(Manifest.permission.POST_NOTIFICATIONS)
            requestPermissions(permissions)
        }

        val permissions = listOf(Manifest.permission.VIBRATE, Manifest.permission.USE_FULL_SCREEN_INTENT)
        requestPermissions(permissions)

    }

    private fun requestPermissions(permissions: List<String>) {
        val needPermissions = mutableListOf<String>()
        permissions.forEach {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                needPermissions.add(it)
            }
        }
        if (needPermissions.isNotEmpty())
            requestPermissions(needPermissions.toTypedArray(), 0)
    }

    // Функция для создания каналов уведомлений
    private fun createNotificationChannels() {
        val channelWithAction = NotificationChannel(
            TASK_1,
            "1. Добавить к оповещению кнопку;",
            NotificationManager.IMPORTANCE_DEFAULT
        )

        val channelWithVibration = NotificationChannel(
            TASK_3,
            "3. Добавить к оповещению уникальную вибрацию;",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            vibrationPattern = longArrayOf(
                0, 500, 100, 500, 100, 500, 100, 350, 100, 150, 100, 500,
                100, 350, 100, 150, 100, 650
            )
        }

        val lockScreenChannel = NotificationChannel(
            TASK_4,
            "4. Добавить возможность открыть приложение из оповещения из экрана блокировки.",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val channels = listOf(channelWithAction, channelWithVibration, lockScreenChannel)

        val notificationManager = getSystemService(NotificationManager::class.java)
//        channels.forEach {
//            notificationManager.deleteNotificationChannel(it.name as String?)
//
//        }
        notificationManager.createNotificationChannels(channels)
    }

    companion object {
        const val TASK_1 = "task1"
        const val TASK_3 = "task3"
        const val TASK_4 = "task4"
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