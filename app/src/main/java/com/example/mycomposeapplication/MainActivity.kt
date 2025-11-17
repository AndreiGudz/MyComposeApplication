package com.example.mycomposeapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

/**
 * Практическая работа №28
 * Тема: Работа с файлами в Android
 * 1. Создать текстовый файл во внутреннем хранилище приложения;
 * 2. Создать текстовый файл во внешнем хранилище;
 * 3. Создать во внутреннем хранилище несколько папок при помощи цикла, внутри - несколько папок или файлов (могут быть папки и без файлов совсем);
 * 4. Вывести содержимое всех файлов из пункта 3 с путем расположения и названием на экран при помощи рекурсивной функции;
 * 5. Сохранить результаты нескольких http запросов в файл формата json;
 * 6. Считать сразу после запуска приложения содержимое этих файлов и вывести на экран в виде списка;
 * 7. Добавить кнопку "Очистить", которая удаляет все файлы формата Json из пункта 5.
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyContent() }
    }
}

@Composable
fun MyContent() {
    MyComposeApplicationTheme {
        Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                WeatherApp()
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}