package com.example.mycomposeapplication

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

/**
 * Практическая работа №13.
 * Тема: Компоненты Views
 * 1. Вывести Hello world!;
 * 2. Вывести 5 надписей по очереди, с паузой в 1 секунду;
 * 3. Добавить кнопку. Менять надписи по нажатию кнопки;
 * 4. Добавить кнопку с картинкой с тем же функционалом;
 * 5. Поменять цвета у всех элементов;
 * 6. Добавить картинку на задний фон;
 * 7. Сделать опрос с несколькими вариантами ответа. Выводить правильно ли выбраны ответы или нет;
 * 8. Сделать программу, которая эмулирует работу этой gif.
 *
 * Практическая работа №14.
 * Тема: Расположение элементов Views
 * 1. Расположить 4 элемента в разных углах экрана;
 * 2. Расположить 4 элемента по центру: сверху, справа, снизу. слева;
 * 3. Добавить к элементам границы разных цветов и форм;
 * 4. Добавить к элементам внутренние и внешние отступы;
 * 5. Показать разницу между start|end и left|right;
 * 6. Установить размер/отступ элемента/текста, установив его при помощи px, pt, dp, sp, mm и in;
 * 7. Добавить к тексту модификаторы (подчеркивание, курсив, выделение);
 * 8. Изменить цвет текста и заднего фона;
 * 9. Изменить размер шрифта и сам шрифт;
 * 10. Изменить положение текста внутри элемента (в разных углах элемента).
 *
 * Практическая работа №15.
 * Тема: Layouts
 * 1. Создать приложение, используя AbsoluteLayout. Продемонстрировать недостатки;
 * 2. Создать приложение, используя FrameLayout;
 * 3. Создать приложение, используя LinearLayout (вертикальное и горизонтальное расположение элементов);
 * 4. Создать приложение, используя RelativeLayout;
 * 5. Создать приложение, используя TableLayout. В приложении обязательно должна быть хотя бы одна объединенная по горизонтали ячейка, одна по вертикали, и одна объединенная и по строкам и по столбцам.
 * 6. Создать приложение, при помощи ConstraintLayout;
 * 7. Создать приложение, при помощи TabLayout;
 * 8. Создать приложение, которое будет пролистываться вниз (в высоту будет больше, чем размер экрана)
 * 9. Создать приложение с разными типами верстки.
 *
 * Практическая работа №16.
 * Тема: Программное создание элементов Views
 * В ходе выполнения работы ЗАПРЕЩАЕТСЯ трогать файлы xml.
 * 1. Создать TextView с текстом "Hello Programmed-View!";
 * 2. Создать циклом 10 TextView с текстом (от 0 до 9);
 * 3. Создать 10 кнопок;
 * 4. Создать калькулятор из кнопок (при помощи GridLayout);
 * 5. Создать калькулятор из кнопок циклом;
 * 6. Циклом применить стили ко всем кнопкам/TextView.
 */

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyContent() }
    }
}

var globalPage = 13
@Composable
fun MyContent() {
    var page: Int by remember { mutableIntStateOf(globalPage) }

    MyComposeApplicationTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                Navigation(13, 4) { pageIndex ->
                    {
                        page = 13 + pageIndex
                        globalPage = page
                    }
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                .padding(innerPadding)
            ) {
                when (page) {
                    13 -> Practic13Content()
                    14 -> Practic14Content()
                    15 ->
                        Toast.makeText(
                            LocalContext.current,
                            "Различные контейнеры были использованы в других фрагментах",
                            Toast.LENGTH_SHORT
                        ).show()
                    16 -> Practic16Content()
                    else -> Practic13Content()
                }
            }
        }
    }
}

@Composable
fun Navigation(
    startNumber: Int = 13,
    repeatTimes: Int = 4,
    modifier: Modifier = Modifier,
    onClickListenerCreator: (Int) -> () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFF8BC34A)),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        repeat(repeatTimes) { repeatIndex ->
            val onClickListener = onClickListenerCreator(repeatIndex)
            Button(
                onClick = onClickListener,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3)
                )
            ) {
                Text(text = "№ ${startNumber + repeatIndex}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    MyComposeApplicationTheme {
        MyContent()
    }
}