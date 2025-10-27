package com.example.mycomposeapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme

@Composable
fun Practic16Content() {
    var calculatorValue by remember { mutableStateOf("0") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Создать TextView с текстом "Hello Programmed-View!"
        item {
            Text(
                text = "1. Программно созданный текст:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            Text(
                text = "Hello Programmed-Compose!",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
                    .padding(16.dp),
                fontSize = 20.sp,
                color = Color(0xFF1976D2),
                textAlign = TextAlign.Center
            )
        }

        // 2. Создать циклом 10 TextView с текстом (от 0 до 9)
        item {
            Text(
                text = "2. 10 текстовых элементов циклом:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE8F5E8), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Создаем 10 Text элементов циклом
                repeat(10) { number ->
                    Text(
                        text = number.toString(),
                        modifier = Modifier
                            .background(Color(0xFFC8E6C9), RoundedCornerShape(4.dp))
                            .padding(8.dp),
                        fontSize = 16.sp,
                        color = Color(0xFF1B5E20),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 3. Создать 10 кнопок
        item {
            Text(
                text = "3. 10 кнопок циклом:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            var lastPressedButton by remember { mutableStateOf(-1) }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFFFF3E0), RoundedCornerShape(8.dp))
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Создаем 10 кнопок циклом
                repeat(10) { index ->
                    Button(
                        onClick = { lastPressedButton = index },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (lastPressedButton == index)
                                Color(0xFFFF9800)
                            else
                                Color(0xFF2196F3)
                        )
                    ) {
                        Text(text = "Кнопка ${index + 1}")
                    }
                }

                if (lastPressedButton != -1) {
                    Text(
                        text = "Нажата кнопка: ${lastPressedButton + 1}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFFFE0B2), RoundedCornerShape(4.dp))
                            .padding(8.dp),
                        textAlign = TextAlign.Center,
                        color = Color(0xFFE65100)
                    )
                }
            }
        }

        // 4. Создать калькулятор из кнопок (при помощи GridLayout)
        item {
            Text(
                text = "4. Калькулятор (GridLayout аналог):",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            CalculatorGridLayout(
                currentValue = calculatorValue,
                onValueChange = { calculatorValue = it }
            )
        }

        // 5. Создать калькулятор из кнопок циклом
        item {
            Text(
                text = "5. Калькулятор (созданный циклом):",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            CalculatorLoopLayout(
                currentValue = calculatorValue,
                onValueChange = { calculatorValue = it }
            )
        }

        // 6. Циклом применить стили ко всем кнопкам/TextView
        item {
            Text(
                text = "6. Стилизованные элементы циклом:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF1976D2)
            )
            StyledElements()
        }
    }
}

// 4. Калькулятор с GridLayout аналогом
@Composable
fun CalculatorGridLayout(
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    val buttons = listOf(
        "C", "±", "%", "/",
        "7", "8", "9", "×",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", "", ".", "="
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Дисплей калькулятора
        Text(
            text = currentValue,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp),
            fontSize = 24.sp,
            textAlign = TextAlign.End,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Сетка кнопок 5x4
        repeat(5) { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(4) { col ->
                    val index = row * 4 + col
                    if (index < buttons.size && buttons[index].isNotEmpty()) {
                        val buttonText = buttons[index]
                        val weight = if (buttonText == "0") 2f else 1f

                        Button(
                            onClick = {
                                when (buttonText) {
                                    "C" -> onValueChange("0")
                                    "=" -> calculateResult(currentValue, onValueChange)
                                    else -> {
                                        val newValue = if (currentValue == "0") {
                                            buttonText
                                        } else {
                                            currentValue + buttonText
                                        }
                                        onValueChange(newValue)
                                    }
                                }
                            },
                            modifier = Modifier
                                .weight(weight)
                                .height(60.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = when (buttonText) {
                                    "C", "±", "%" -> Color(0xFF9E9E9E)
                                    "/", "×", "-", "+", "=" -> Color(0xFFFF9800)
                                    else -> Color(0xFF2196F3)
                                }
                            )
                        ) {
                            Text(
                                text = buttonText,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    } else {
                        // Пустая ячейка для "0" которая занимает 2 колонки
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            if (row < 4) Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

// 5. Калькулятор созданный циклом
@Composable
fun CalculatorLoopLayout(
    currentValue: String,
    onValueChange: (String) -> Unit
) {
    val numberButtons = listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", "0")
    val operationButtons = listOf("+", "-", "×", "/", "=", "C")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        // Дисплей
        Text(
            text = currentValue,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(8.dp))
                .padding(16.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.End,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Числовые кнопки циклом
            Column(
                modifier = Modifier.weight(2f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                repeat(3) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(3) { col ->
                            val index = row * 3 + col
                            if (index < numberButtons.size - 1) {
                                CalculatorButton(
                                    text = numberButtons[index],
                                    onClick = {
                                        val newValue = if (currentValue == "0") {
                                            numberButtons[index]
                                        } else {
                                            currentValue + numberButtons[index]
                                        }
                                        onValueChange(newValue)
                                    },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
                // Кнопка 0
                Row {
                    CalculatorButton(
                        text = "0",
                        onClick = {
                            val newValue = if (currentValue == "0") {
                                "0"
                            } else {
                                currentValue + "0"
                            }
                            onValueChange(newValue)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Кнопки операций циклом
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                operationButtons.forEach { operation ->
                    CalculatorButton(
                        text = operation,
                        onClick = {
                            when (operation) {
                                "C" -> onValueChange("0")
                                "=" -> calculateResult(currentValue, onValueChange)
                                else -> {
                                    val newValue = if (currentValue == "0") {
                                        operation
                                    } else {
                                        currentValue + operation
                                    }
                                    onValueChange(newValue)
                                }
                            }
                        },
                        backgroundColor = when (operation) {
                            "C" -> Color(0xFFF44336)
                            "=" -> Color(0xFF4CAF50)
                            else -> Color(0xFFFF9800)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color = Color(0xFF2196F3),
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

// 6. Стилизованные элементы циклом
@Composable
fun StyledElements() {
    val colors = listOf(
        Color(0xFFF44336), Color(0xFFE91E63), Color(0xFF9C27B0), Color(0xFF673AB7),
        Color(0xFF3F51B5), Color(0xFF2196F3), Color(0xFF03A9F4), Color(0xFF00BCD4),
        Color(0xFF009688), Color(0xFF4CAF50), Color(0xFF8BC34A), Color(0xFFCDDC39)
    )

    val styles = listOf(
        "Обычный" to FontWeight.Normal,
        "Жирный" to FontWeight.Bold,
        "Тонкий" to FontWeight.Light,
        "Средний" to FontWeight.Medium
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFAFAFA), RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        // Стилизованные тексты циклом
        Text(
            text = "Стилизованные тексты:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        styles.forEachIndexed { index, (styleName, fontWeight) ->
            Text(
                text = "Текст в стиле: $styleName",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors[index % colors.size])
                    .padding(8.dp),
                color = Color.White,
                fontWeight = fontWeight,
                fontSize = (14 + index * 2).sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Стилизованные кнопки циклом
        Text(
            text = "Стилизованные кнопки:",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = Color(0xFF1976D2),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(4) { index ->
                Button(
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    shape = when (index) {
                        0 -> RoundedCornerShape(0.dp)
                        1 -> RoundedCornerShape(8.dp)
                        2 -> RoundedCornerShape(16.dp)
                        else -> RoundedCornerShape(50)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors[index + 8]
                    )
                ) {
                    Text(
                        text = "Btn ${index + 1}",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                }
                if (index < 3) Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

// Функция для вычисления результата (упрощенная)
private fun calculateResult(expression: String, onResult: (String) -> Unit) {
    try {
        // Простая логика вычисления (для демонстрации)
        val result = when {
            expression.contains("+") -> {
                val parts = expression.split("+")
                parts[0].toDouble() + parts[1].toDouble()
            }
            expression.contains("-") -> {
                val parts = expression.split("-")
                parts[0].toDouble() - parts[1].toDouble()
            }
            expression.contains("×") -> {
                val parts = expression.split("×")
                parts[0].toDouble() * parts[1].toDouble()
            }
            expression.contains("/") -> {
                val parts = expression.split("/")
                if (parts[1].toDouble() != 0.0) {
                    parts[0].toDouble() / parts[1].toDouble()
                } else {
                    throw ArithmeticException("Division by zero")
                }
            }
            else -> expression.toDouble()
        }
        onResult(result.toString())
    } catch (e: Exception) {
        onResult("Error")
    }
}

//@Preview(showBackground = true)
@Composable
fun Practic16Preview() {
    MyComposeApplicationTheme {
        Practic16Content()
    }
}