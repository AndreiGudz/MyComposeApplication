package com.example.mycomposeapplication

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
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
        getPermissionsIfNeed()
        createNotificationChannels()
        setContent { MyContent() }
    }

    private fun getPermissionsIfNeed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat
                    .checkSelfPermission(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 0)
            }
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.VIBRATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.VIBRATE), 0)

        }
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
            vibrationPattern = LongArray(5) { (it * 200).toLong() }
            enableVibration(true)
        }

        val lockScreenChannel = NotificationChannel(
            TASK_4,
            "4. Добавить возможность открыть приложение из оповещения из экрана блокировки.",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannels(
            listOf(channelWithAction, channelWithVibration, lockScreenChannel)
        )
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

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}