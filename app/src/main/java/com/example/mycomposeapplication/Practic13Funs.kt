package com.example.mycomposeapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

val texts = listOf(
    "Первая надпись",
    "Вторая надпись",
    "Третья надпись",
    "Четвертая надпись",
    "Пятая надпись"
)

@Composable
fun Practic13Content() {
    var textState by remember { mutableStateOf("Hello World!") }

    Image(
        painter = painterResource(R.drawable.background_img),
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
        contentDescription = "Фон"
    )

    Column {
        Text(
            text = textState,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1976D2)
        )

        Spacer(modifier = Modifier.height(16.dp))

        AddWithDelayTexts()

        Spacer(modifier = Modifier.height(16.dp))

        RowWithButtons { str -> { textState = str } }

        Spacer(modifier = Modifier.height(16.dp))

        Questions()

        Spacer(modifier = Modifier.height(16.dp))

        SwitchesFromGif()
    }
}

@Composable
fun RowWithButtons(onClickCreator: (String) -> () -> Unit) {
    val buttonOnClick = onClickCreator("Текст изменён кнопкой")
    val iconButtonOnClick = onClickCreator("Текст изменён кнопкой с картинкой")

    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = buttonOnClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2196F3)
            )
        ) {
            Text(
                text = "Изменить текст",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        IconButton(
            onClick = iconButtonOnClick,
            modifier = Modifier.background(Color(0xFF4CAF50))

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.wrapContentWidth()
            ) {
                Icon(
                    painter = painterResource(android.R.drawable.ic_menu_edit),
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun Questions() {
    var showResults by remember { mutableStateOf(false) }
    val answersState = remember { mutableStateListOf(false, false, false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {


        // 4. Опрос с несколькими вариантами ответа
        Text(
            text = "Опрос: Что такое Jetpack Compose?",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1976D2)
        )

        // Варианты ответов
        val questions = listOf(
            "Декларативный UI фреймворк",
            "Императивный подход к UI",
            "Современный способ создания UI в Android"
        )
        val correctAnswers = listOf(true, false, true)

        questions.forEachIndexed { index, question ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = answersState[index],
                        onValueChange = { answersState[index] = it }
                    )
                    .padding(start = 8.dp)
            ) {
                Checkbox(
                    checked = answersState[index],
                    onCheckedChange = { answersState[index] = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color(0xFF1976D2)
                    )
                )
                Text(
                    text = question,
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color(0xFF1976D2)
                )
            }
        }

        Button(
            onClick = { showResults = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF1976D2)
            )
        ) {
            Text("Проверить ответы")
        }

        if (showResults) {
            val isCorrect = answersState.mapIndexed { index, answer ->
                answer == correctAnswers[index]
            }.all { it }

            Text(
                text = if (isCorrect) "✅ Все ответы правильные!" else "❌ Есть ошибки в ответах",
                fontSize = 16.sp,
                color = if (isCorrect) Color(0xFF388E3C) else Color(0xFFD32F2F),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SwitchesFromGif() {
    val switchesState = remember { mutableStateListOf(false, false, false) }
    Column {
        switchesState.forEachIndexed { index, isChecked ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .toggleable(
                        value = isChecked,
                        onValueChange = {
                            handleSwitchSelection(index, switchesState)
                        }
                    )
                    .padding(start = 16.dp)
            ) {
                Switch(
                    checked = isChecked,
                    onCheckedChange = {
                        handleSwitchSelection(index, switchesState)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color(0xFF2196F3),
                        checkedTrackColor = Color(0x1E03A9F4)
                    )
                )
                Text(
                    text = "Переключатель ${index + 1}",
                    modifier = Modifier.padding(start = 8.dp),
                    color = Color(0xFF455A64)
                )
            }
        }
    }
}

@Composable
fun AddWithDelayTexts() {
    Column(
        modifier = Modifier.background(Color.Red)
    ) {
        var textsCount: Int by remember { mutableIntStateOf(0) }

        LaunchedEffect(Unit) {
            repeat(texts.size) {
                delay(1000)
                textsCount++
            }
        }

        repeat(textsCount) {
            Text(texts[it])
        }
    }
}

// Функция для обработки выбора переключателей
private fun handleSwitchSelection(selectedIndex: Int, switchesState: MutableList<Boolean>) {
    val currentState = switchesState.toList()
    val isCurrentlySelected = currentState[selectedIndex]

    if (!isCurrentlySelected) {
        // Если выбираем новый переключатель
        val selectedCount = currentState.count { it }
        if (selectedCount >= 2) {
            // Находим следующий по порядку активный переключатель для выключения
            var indexToDisable = (selectedIndex + 1) % 3
            while (!currentState[indexToDisable]) {
                indexToDisable = (indexToDisable + 1) % 3
            }
            switchesState[indexToDisable] = false
        }
    }

    switchesState[selectedIndex] = !isCurrentlySelected
}