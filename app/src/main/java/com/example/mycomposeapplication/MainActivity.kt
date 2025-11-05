package com.example.mycomposeapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mycomposeapplication.ui.theme.MyComposeApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyContent() }
    }
}

@Composable
fun MyContent() {
    var answerText by remember { mutableStateOf("Здесь будет ответ") }

    MyComposeApplicationTheme {
        Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                Column(modifier = Modifier.fillMaxSize()) {
                    ApiButtons() { str ->
                        Log.d("api button", str)
                        answerText = str
                    }

                    Spacer(Modifier.padding(8.dp))

                    AnswerText(answerText)
                }
            }
        }
    }
}

@Composable
private fun AnswerText(answerText: String) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF00BCD4))
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = answerText)
    }
}

@Composable
fun ApiButtons(changeString: (String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val apiService = remember { createApiService() }

    suspend fun CoroutineScope.responseWrapper (
        loadingText: String = "Выполняется запрос",
        innerFunction: suspend CoroutineScope.() -> Unit
    ) {
        changeString("$loadingText: Ожидание...")
        try {
            innerFunction()
        } catch (e: Exception) {
            Log.e("api error", "${e.message}\n${e.stackTraceToString()}")
            changeString("Ошибка: ${e.message}")
        }
    }

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        ApiButton("Get запрос для Hello World") {
            coroutineScope.launch {
                responseWrapper("Get запрос для Hello World") {
                    val response = apiService.getHelloWorld()
                    changeString("GET: ${response.message}")
                }
            }
        }

        ApiButton("Post запрос для Hello World") {
            coroutineScope.launch {
                responseWrapper("Post запрос для Hello World") {
                    val response = apiService.postHelloWorld()
                    changeString("POST: ${response.message}")
                }
            }
        }

        ApiButton("Post запрос для сложения") {
            coroutineScope.launch {
                responseWrapper("Post запрос для сложения") {
                    val request = SumRequest(5.5f, 3.2f)
                    val response = apiService.calculateSum(request)
                    changeString("Сумма: ${response.sum} (${response.number1} + ${response.number2})")
                }
            }
        }

        ApiButton("Получить все items") {
            coroutineScope.launch {
                responseWrapper("Получить все items") {
                    val response = apiService.getAllItems()
                    changeString(
                        "Всего items: ${response.size}\n${
                        response.joinToString("\n") {
                            "${it.id}: ${it.name} - ${it.description}"
                        }
                    }")
                }
            }
        }

        ApiButton("Поиск items по имени") {
            coroutineScope.launch {
                responseWrapper("Поиск items по имени") {
                    val request = ItemRequest("Стол")
                    val response = apiService.searchItems(request)
                    changeString(
                        "Найдено items: ${response.size}\n ${
                            response.joinToString("\n")
                            {
                                "${it.id}: ${it.name} - ${it.description}"
                            }
                        }")
                }
            }
        }
    }
}

@Composable
fun ApiButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        onClick = onClick
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}