package com.example.mycomposeapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Practic26(searchWordByDefault:String = "программ") {
    var searchWord by remember { mutableStateOf(searchWordByDefault) }
    var result by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Поле для ввода слова поиска
        OutlinedTextField(
            value = searchWord,
            onValueChange = { searchWord = it },
            label = { Text("Слово для поиска") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            MyButton("1. Проверить точное слово") {
                result = checkExactWord(sampleText, searchWord)
            }

            MyButton("2. Проверить слово с окончаниями") {
                result = checkWordWithEndings(sampleText, searchWord)
            }

            MyButton("3. Найти слова с общим корнем") {
                result = findWordsWithSameRoot(sampleText, searchWord)
            }

            MyButton("4. Посчитать слова длиннее 5 символов") {
                result = countLongWords(sampleText)
            }

            MyButton("5. Найти все даты") {
                result = findAllDates(sampleText)
            }

            MyButton("6. Найти все время") {
                result = findAllTimes(sampleText)
            }

            MyButton("7. Разделить по слову 'ответил'") {
                result = splitTextByWord(sampleText, "ответил")
            }

            MyButton("8. Найти ответы ребенка") {
                result = findChildResponses(sampleText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (result.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = result,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Исходный текст:",
            modifier = Modifier.fillMaxWidth()
        )
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = sampleText,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun MyButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text)
    }
}

// 1. Проверить, есть ли в тексте указанное слово
fun checkExactWord(text: String, word: String): String {
    val regex = "\\b${Regex.escape(word)}\\b".toRegex(RegexOption.IGNORE_CASE)
    val found = regex.containsMatchIn(text)
    return if (found) {
        "Слово '$word' найдено в тексте!"
    } else {
        "Слово '$word' не найдено в тексте."
    }
}

// 2. Проверить, есть ли в тексте указанное слово с учетом различных окончаний
fun checkWordWithEndings(text: String, word: String): String {
    val regex = "\\b${Regex.escape(word)}[а-яё]*?\\b".toRegex(RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    val foundWords = matches.map { it.value }.toList()

    return if (foundWords.isNotEmpty()) {
        "Найдены слова с корнем '$word': ${foundWords.joinToString(", ")}"
    } else {
        "Слова с корнем '$word' не найдены."
    }
}

// 3. Найти в тексте все слова с одинаковым корнем
fun findWordsWithSameRoot(text: String, root: String): String {
    val regex = "\\b[а-яё]*?${Regex.escape(root)}[а-яё]*?\\b".toRegex(RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    val words = matches.map { it.value }.distinct().toList()

    return if (words.isNotEmpty()) {
        "Слова с корнем '$root': ${words.joinToString(", ")}"
    } else {
        "Слова с корнем '$root' не найдены."
    }
}

// 4. Посчитать количество слов, длина которых более 5 символов
fun countLongWords(text: String): String {
    val regex = "\\b[а-яёa-z]{6,}\\b".toRegex(RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    val longWords = matches.map { it.value }.toList()

    return "Слов длиннее 5 символов: ${longWords.size}\n" +
            "Список: ${longWords.joinToString(", ")}"
}

// 5. Найти в тексте все даты
fun findAllDates(text: String): String {
    // Форматы:
    // DD месяц YYYY
    val format1 = """(\d{1,2}\s+(января|февраля|марта|апреля|мая|июня|июля|августа|сентября|октября|ноября|декабря)\s+\d{4})""".toRegex(RegexOption.IGNORE_CASE)
    // DD.MM.YYYY
    val format2 = """(\d{1,2}\.\d{1,2}\.\d{4})""".toRegex(RegexOption.IGNORE_CASE)
    // YYYY-MM-DD
    val format3 = """(\d{4}-\d{1,2}-\d{1,2})""".toRegex(RegexOption.IGNORE_CASE)

    val regex = """${format1.pattern}|${format2.pattern}|${format3.pattern}""".toRegex(RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    val dates = matches.map { it.value }.toList()

    return "Найденные даты: ${dates.joinToString(", ")}"
}

// 6. Найти в тексте все время
fun findAllTimes(text: String): String {
    // Формат: HH:MM
    val regex = """\b\d{1,2}:\d{2}\b""".toRegex(RegexOption.IGNORE_CASE)
    val matches = regex.findAll(text)
    val times = matches.map { it.value }.toList()

    return "Найденное время: ${times.joinToString(", ")}"
}

// 7. Разделить текст по определённому слову
fun splitTextByWord(text: String, word: String): String {
    val parts = text.split(Regex("\\b${Regex.escape(word)}\\b"))

    return "Текст разделен по слову '$word' на ${parts.size} частей:\n\n" +
            parts.mapIndexed { index, part ->
                "Часть ${index + 1}:\n\"${part.trim()}\""
            }.joinToString("\n\n")
}

// 8. Определить ответы ребёнка (упрощенная версия)
fun findChildResponses(text: String): String {
    // Ищем все фразы в кавычках, которые говорятся ребенком
    val regex = """"([^"]+)"[^.?!]*\b(реб[её]нок|малыш)[^.?!]*""".toRegex(RegexOption.IGNORE_CASE)

    val matches = regex.findAll(text)
    val responses = matches.map { it.groupValues[1] }.toList()

    return if (responses.isNotEmpty()) {
        "Ответы ребенка:\n" + responses.joinToString("\n- ", "- ")
    } else {
        "Ответы ребенка не найдены в тексте."
    }
}