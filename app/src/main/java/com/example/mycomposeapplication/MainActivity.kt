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
        setContent {
            MyComposeApplicationTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Practic13Content()
                    }
                }
            }
        }
    }
}

@Composable
fun Navigation() {
    val repeatTimes = 3
    val onClickListener =  {}

    Row(
    ) {
        repeat(repeatTimes) { repeatIndex ->
            Button(onClick = onClickListener) {

                Text(text = "Задание ${13 + repeatIndex}")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyComposeApplicationTheme {
        Practic13Content()
    }
}