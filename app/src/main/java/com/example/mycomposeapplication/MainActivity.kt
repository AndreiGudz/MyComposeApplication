package com.example.mycomposeapplication

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil3.compose.rememberAsyncImagePainter
import java.io.File
import java.security.Permissions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraBackgroundApp()
        }
    }
}

val permissions = arrayOf(Manifest.permission.CAMERA)
@Composable
fun CameraBackgroundApp() {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    var tempUri: Uri? = null

    // Лаунчер для камеры
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Фото успешно сделано, imageUri уже содержит ссылку на фото
            Log.d("cameraLauncher", "success $success")
            imageUri = tempUri
        }
    }

    // Лаунчер для разрешений
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { gotPermissions ->
        if (permissions.all { gotPermissions[it] == true }) {
            Toast.makeText(context, "Нажмите на кнопку ещё раз", Toast.LENGTH_SHORT).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Фон приложения
        if (imageUri != null) {
            Log.d("Image", "Generate image $imageUri")
            Image(
                painter = rememberAsyncImagePainter(imageUri),
                contentDescription = "Background",
                modifier = Modifier.fillMaxSize(),
            )
        }
        // Кнопка для вызова камеры
        Button(
            onClick = {

                when {
                    permissions.all {
                        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
                    } -> {
                        // Создаем временный файл для фото
                        val photoFile = File.createTempFile(
                            "photo_",
                            ".jpg",
                            context.externalCacheDir
                        )
                        tempUri = FileProvider.getUriForFile(
                            context,
                            "com.example.mycomposeapplication.provider",
                            photoFile
                        )
                        tempUri?.let {
                            Log.d("take photo", "tempUri $tempUri for $photoFile")
                            cameraLauncher.launch(it)
                        }
                    }
                    else -> {
                        permissionLauncher.launch(permissions)
                    }
                }
            },
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Сделать фото для фона")
        }
    }
}