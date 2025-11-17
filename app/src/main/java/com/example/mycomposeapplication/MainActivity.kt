package com.example.mycomposeapplication

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : ComponentActivity() {
    private var currentImageUri: Uri? = null

    // Лаунчер для камеры
    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Обновляем состояние через runOnUiThread так как мы вне Compose
            runOnUiThread {
                // Здесь мы не можем напрямую обновить compose состояние,
                // поэтому будем перезагружать активность или использовать другой подход
                // Для простоты перезагрузим активность
                recreate()
            }
        }
    }

    // Лаунчер для разрешений
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            // Если разрешение получено, открываем камеру
            openCamera()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Восстанавливаем URI из сохраненного состояния
        if (savedInstanceState != null) {
            currentImageUri = savedInstanceState.getParcelable("imageUri")
        }

        setContent {
            MyComposeApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MyContent(
                            currentImageUri = currentImageUri,
                            onTakePhoto = {
                                checkPermissionsAndOpenCamera()
                            }
                        )
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentImageUri?.let { uri ->
            outState.putParcelable("imageUri", uri)
        }
    }

    private fun checkPermissionsAndOpenCamera() {
        // Проверяем разрешение на камеру
        when {
            checkSelfPermission(Manifest.permission.CAMERA) == android.content.pm.PackageManager.PERMISSION_GRANTED -> {
                // Разрешение уже есть, открываем камеру
                openCamera()
            }
            else -> {
                // Запрашиваем разрешение
                requestPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        // Создаем временный файл для фотографии
        val imageUri = createImageFile(this)
        currentImageUri = imageUri
        takePictureLauncher.launch(imageUri)
    }

    private fun createImageFile(context: Context): Uri {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir = context.getExternalFilesDir(android.os.Environment.DIRECTORY_PICTURES)
        val imageFile = File.createTempFile(
            imageFileName,  /* prefix */
            ".jpg",         /* suffix */
            storageDir      /* directory */
        )


        return androidx.core.content.FileProvider.getUriForFile(
            context,
            "com.example.mycomposeapplication.fileprovider",
            imageFile
        )
    }
}

@Composable
fun BoxScope.MyContent(
    currentImageUri: Uri?,
    onTakePhoto: () -> Unit
) {
    // Фон приложения - фотография или цвет по умолчанию
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (currentImageUri != null) {
            // Показываем фотографию как фон
            AsyncImage(
                model = currentImageUri,
                contentDescription = "Фон приложения",
                modifier = Modifier.fillMaxSize()
            )
        }

        // Кнопка для вызова камеры
        Button(
            onClick = onTakePhoto,
            modifier = Modifier.align(Alignment.Center)
        ) {
            Text("Сделать фото для фона")
        }
    }
}