package com.example.mycomposeapplication

import android.content.res.Resources
import android.util.TypedValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

/**
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
 */
@Composable
fun Practic14Content() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Расположить 4 элемента в разных углах экрана
        Text(
            text = "1. Элементы в разных углах экрана:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        CornerElements()

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Расположить 4 элемента по центру: сверху, справа, снизу, слева
        Text(
            text = "2. Элементы вокруг центра:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        CenterSurroundElements()

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Границы разных цветов и форм
        Text(
            text = "3. Границы разных цветов и форм:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        BorderElements()

        Spacer(modifier = Modifier.height(8.dp))

        // 4. Внутренние и внешние отступы
        Text(
            text = "4. Внутренние и внешние отступы:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        PaddingElements()

        Spacer(modifier = Modifier.height(8.dp))

        // 5. Разница между start|end и left|right
        Text(
            text = "5. Start/End vs Left/Right:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        StartEndVsLeftRight()

        Spacer(modifier = Modifier.height(8.dp))

        // 6. Разные единицы измерения
        Text(
            text = "6. Разные единицы измерения:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        DifferentUnits()

        Spacer(modifier = Modifier.height(8.dp))

        // 7. Модификаторы текста
        Text(
            text = "7. Модификаторы текста:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        TextModifiers()

        Spacer(modifier = Modifier.height(8.dp))

        // 8. Цвета текста и фона
        Text(
            text = "8. Цвета текста и фона:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        TextAndBackgroundColors()

        Spacer(modifier = Modifier.height(8.dp))

        // 9. Размер и шрифт текста
        Text(
            text = "9. Размер и шрифт текста:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        TextSizeAndFont()

        Spacer(modifier = Modifier.height(8.dp))

        // 10. Положение текста внутри элемента
        Text(
            text = "10. Положение текста внутри элемента:",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color(0xFF1976D2)
        )
        TextAlignmentInBox()
    }
}

// 1. Элементы в разных углах
@Composable
fun CornerElements() {
    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(Color(0xFFE3F2FD))
            .border(1.dp, Color(0xFF1976D2))
    ) {
        Text(
            text = "↖ Сверху-слева",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp)
                .background(Color(0xFFBBDEFB))
                .padding(4.dp),
            color = Color(0xFF0D47A1)
        )

        Text(
            text = "↗ Сверху-справа",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .background(Color(0xFFBBDEFB))
                .padding(4.dp),
            color = Color(0xFF0D47A1)
        )

        Text(
            text = "↙ Снизу-слева",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
                .background(Color(0xFFBBDEFB))
                .padding(4.dp),
            color = Color(0xFF0D47A1)
        )

        Text(
            text = "↘ Снизу-справа",
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(8.dp)
                .background(Color(0xFFBBDEFB))
                .padding(4.dp),
            color = Color(0xFF0D47A1)
        )
    }
}

// 2. Элементы вокруг центра
@Composable
fun CenterSurroundElements() {
    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .background(Color(0xFFE8F5E8))
            .border(1.dp, Color(0xFF388E3C))
    ) {
        Text(
            text = "↑ Сверху",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(8.dp)
                .background(Color(0xFFC8E6C9))
                .padding(4.dp),
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "→ Справа",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(8.dp)
                .background(Color(0xFFC8E6C9))
                .padding(4.dp),
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "↓ Снизу",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(Color(0xFFC8E6C9))
                .padding(4.dp),
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "← Слева",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(8.dp)
                .background(Color(0xFFC8E6C9))
                .padding(4.dp),
            color = Color(0xFF1B5E20)
        )

        Text(
            text = "● Центр",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(8.dp)
                .background(Color(0xFFA5D6A7))
                .padding(4.dp),
            color = Color(0xFF1B5E20),
            fontWeight = FontWeight.Bold
        )
    }
}

// 3. Границы разных цветов и форм
@Composable
fun BorderElements() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        // Прямоугольная граница
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(3.dp, Color.Red, RectangleShape)
                .background(Color(0xFFFFEBEE))
        ) {
            Text(
                text = "□",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
        }

        // Скругленная граница
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(3.dp, Color.Green, RoundedCornerShape(16.dp))
                .background(Color(0xFFE8F5E8), RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "○",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
        }

        // Круглая граница
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(3.dp, Color.Blue, CircleShape)
                .background(Color(0xFFE3F2FD), CircleShape)
        ) {
            Text(
                text = "●",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
        }

        // Срезанные углы
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(3.dp, Color(0xFFFF9800), CutCornerShape(12.dp))
                .background(Color(0xFFFFF3E0), CutCornerShape(12.dp))
        ) {
            Text(
                text = "◇",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
        }
    }
}

// 4. Внутренние и внешние отступы
@Composable
fun PaddingElements() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(2.dp, Color(0xFF7B1FA2))
            .padding(16.dp) // Внутренний отступ
            .background(Color(0xFFE1BEE7))
    ) {
        Text(
            text = "Внешний отступ (margin)",
            color = Color(0xFF4A148C),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Этот текст имеет внутренние отступы (padding) со всех сторон",
            color = Color(0xFF4A148C),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFCE93D8))
                .padding(12.dp) // Внутренний отступ
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "Слева",
                modifier = Modifier.padding(end = 16.dp), // Внешний отступ справа
                color = Color(0xFF4A148C)
            )
            Text(
                text = "Справа",
                modifier = Modifier.padding(start = 16.dp), // Внешний отступ слева
                color = Color(0xFF4A148C)
            )
        }
    }
}

// 5. Разница между start|end и left|right
@Composable
fun StartEndVsLeftRight() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Start/End (учитывают RTL - Right-to-Left):",
            color = Color(0xFFD32F2F),
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFFFCDD2))
                .padding(start = 32.dp, end = 16.dp)
        ) {
            Text(
                text = "Start/End Padding",
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color(0xFFC2185B)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Left/Right (игнорируют RTL):",
            color = Color(0xFF1976D2),
            fontSize = 14.sp
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color(0xFFBBDEFB))
                .padding(start = 32.dp, end = 16.dp)
        ) {
            Text(
                text = "Left/Right Padding",
                modifier = Modifier.align(Alignment.CenterStart),
                color = Color(0xFF1976D2)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "• Start/End адаптируются под направление текста (LTR/RTL)",
            fontSize = 12.sp,
            color = Color(0xFF757575)
        )
        Text(
            text = "• Left/Right всегда фиксированы",
            fontSize = 12.sp,
            color = Color(0xFF757575)
        )
    }
}

// 6. Разные единицы измерения
@Composable
fun DifferentUnits() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // DP (Density-independent Pixels)
        Text(
            text = "16.dp - Density-independent Pixels",
            modifier = Modifier.padding(16.dp),
            fontSize = 14.sp,
            color = Color(0xFF1976D2)
        )

        // SP (Scale-independent Pixels)
        Text(
            text = "18.sp - Scale-independent Pixels",
            fontSize = 18.sp,
            color = Color(0xFF388E3C)
        )

        // Размеры в разных единицах
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp) // DP
                    .background(Color(0xFFFF9800))
            ) {
                Text(
                    text = "50.dp",
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            // Для других единиц (mm, in, pt) обычно используются dp в Compose
            // но можно конвертировать если нужно

            val mm = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_MM, 1f, Resources.getSystem().displayMetrics)
            val inch = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_IN, 1f, Resources.getSystem().displayMetrics)
            val pt = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PT, 1f, Resources.getSystem().displayMetrics)
            val dp = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1f, Resources.getSystem().displayMetrics)
            val mmSize = (20 * mm / dp).dp
            val inSize = (inch / dp).dp
            val ptSize = (29 * pt / dp).dp // около 10 мм
            Box(
                modifier = Modifier
                    .size(mmSize)
                    .background(Color(0xFF2196F3))
            ) {
                Text(
                    text = "20.mm",
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .size(inSize)
                    .background(Color(0xFF4CAF50))
            ) {
                Text(
                    text = "1.in",
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            Box(
                modifier = Modifier
                    .size(ptSize)
                    .background(Color(0xFF9C27B0))
            ) {
                Text(
                    text = "29.pt",
                    fontSize = 10.sp,
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }
        }
    }
}

// 7. Модификаторы текста
@Composable
fun TextModifiers() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Подчеркивание
        Text(
            text = "Подчеркнутый текст",
            textDecoration = TextDecoration.Underline,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Зачеркнутый
        Text(
            text = "Зачеркнутый текст",
            textDecoration = TextDecoration.LineThrough,
            color = Color(0xFFD32F2F),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Курсив
        Text(
            text = "Текст курсивом",
            fontStyle = FontStyle.Italic,
            color = Color(0xFF388E3C),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Жирный
        Text(
            text = "Жирный текст",
            fontWeight = FontWeight.Bold,
            color = Color(0xFF7B1FA2),
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Комбинированные стили
        Text(
            buildAnnotatedString {
                append("Разные ")
                withStyle(style = SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold)) {
                    append("стили")
                }
                append(" в ")
                withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append("одной")
                }
                append(" строке")
            },
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

// 8. Цвета текста и фона
@Composable
fun TextAndBackgroundColors() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Простые цвета
        Text(
            text = "Красный текст на белом фоне",
            color = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Белый текст на цветном фоне
        Text(
            text = "Белый текст на синем фоне",
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Blue)
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Градиентный фон
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(Color(0xFF80FF00), Color(0xFFFFC700), Color(0xFFFF2222))
                    )
                )
        ) {
            Text(
                text = "Текст на градиентном фоне",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// 9. Размер и шрифт текста
@Composable
fun TextSizeAndFont() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Разные размеры
        Text(
            text = "12.sp - Маленький текст",
            fontSize = 12.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "16.sp - Обычный текст",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "24.sp - Крупный текст",
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "32.sp - Очень крупный текст",
            fontSize = 32.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Разные стили шрифта (в Compose обычно используются системные шрифты)
        Text(
            text = "Обычный стиль шрифта",
            fontSize = 16.sp,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "Жирный стиль шрифта",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 2.dp)
        )
        Text(
            text = "Тонкий стиль шрифта",
            fontSize = 16.sp,
            fontWeight = FontWeight.Light,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}

// 10. Положение текста внутри элемента
@Composable
fun TextAlignmentInBox() {
    Column(modifier = Modifier.fillMaxWidth()) {
        // Сетка 3x3 с разным выравниванием
        val alignments = listOf(
            Alignment.TopStart to "↖",
            Alignment.TopCenter to "↑",
            Alignment.TopEnd to "↗",
            Alignment.CenterStart to "←",
            Alignment.Center to "●",
            Alignment.CenterEnd to "→",
            Alignment.BottomStart to "↙",
            Alignment.BottomCenter to "↓",
            Alignment.BottomEnd to "↘"
        )

        // Создаем сетку 3x3
        for (row in 0..2) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (col in 0..2) {
                    val index = row * 3 + col
                    val (alignment, symbol) = alignments[index]

                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFFE1BEE7))
                            .border(1.dp, Color(0xFF7B1FA2)),
                        contentAlignment = alignment
                    ) {
                        Text(
                            text = symbol,
                            fontSize = 20.sp,
                            color = Color(0xFF4A148C),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            if (row < 2) Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Выравнивание текста в Text
        Column {
            Text(
                text = "Выравнивание влево (Start)",
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD))
                    .padding(8.dp),
                color = Color(0xFF1976D2)
            )

            Text(
                text = "Выравнивание по центру (Center)",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E8))
                    .padding(8.dp),
                color = Color(0xFF388E3C)
            )

            Text(
                text = "Выравнивание вправо (End)",
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFEBEE))
                    .padding(8.dp),
                color = Color(0xFFD32F2F)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Practic14Preview() {
    MyComposeApplicationTheme {
        Practic14Content()
    }
}