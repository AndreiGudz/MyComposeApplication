package com.example.mycomposeapplication

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import androidx.compose.foundation.layout.*

class MainViewModel : ViewModel() {
    // 1. Flow
    private val _simpleFlow = MutableStateFlow("Не запущено")
    val simpleFlow: StateFlow<String> = _simpleFlow

    // 2. SharedFlow
    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow: SharedFlow<String> = _sharedFlow.asSharedFlow()

    // 3. StateFlow
    private val _stateFlow = MutableStateFlow("Начальное значение")
    val stateFlow: StateFlow<String> = _stateFlow

    // 4. LiveData
    private val _liveData = MutableLiveData<String>("Не запущено")
    val liveData: LiveData<String> = _liveData

    // 5. MutableLiveData
    val mutableLiveData = MutableLiveData<String>("Не запущено")

    // 6. Channel
    val channel = Channel<String>()

    private var flowJob: Job? = null

    fun startGeneratingValues() {
        stopGeneratingValues()

        flowJob = viewModelScope.launch {
            for (i in 1..100) {
                delay(1000L)
                val value = "Значение: $i"

                // Обновляем каждый реактивный объект
                _simpleFlow.value = value
                _sharedFlow.emit(value)
                _stateFlow.value = value
                _liveData.value = value
                mutableLiveData.value = value
                channel.send(value)
            }
        }
    }

    fun stopGeneratingValues() {
        flowJob?.cancel()
        viewModelScope.launch {
            val message = "Остановлено"
            _simpleFlow.value = message
            _sharedFlow.emit(message)
            _stateFlow.value = message
            _liveData.value = message
            mutableLiveData.value = message
            channel.send(message)
        }
    }
}

@Composable
fun ReactiveObjectsDemo() {
    val viewModel: MainViewModel = viewModel()
    var localState by remember { mutableStateOf("Локальное состояние") }

    val flowValue by viewModel.simpleFlow.collectAsState()
    val sharedFlowValue by viewModel.sharedFlow.collectAsState("Ожидание...")
    val stateFlowValue by viewModel.stateFlow.collectAsState()
    val liveDataValue by viewModel.liveData.observeAsState("Ожидание...")
    val mutableLiveDataValue by viewModel.mutableLiveData.observeAsState("Ожидание...")

    val channelValue = remember { mutableStateOf("Ожидание...") }


    // Коллектор для Channel
    LaunchedEffect(key1 = viewModel.channel) {
        for (value in viewModel.channel) {
            channelValue.value = value
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Демонстрация реактивных объектов",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Кнопки управления
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.startGeneratingValues() }) {
                Text("Старт")
            }
            Button(onClick = { viewModel.stopGeneratingValues() }) {
                Text("Стоп")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение значений
        ReactiveItem("1. Flow:", flowValue)
        ReactiveItem("2. SharedFlow:", sharedFlowValue)
        ReactiveItem("3. StateFlow:", stateFlowValue)
        ReactiveItem("4. LiveData:", liveDataValue)
        ReactiveItem("5. MutableLiveData:", mutableLiveDataValue)
        ReactiveItem("6. Channel:", channelValue.value)

        // Демонстрация remember()
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    "7. remember() демонстрация",
                    style = MaterialTheme.typography.titleMedium
                )
                Text("Локальное состояние: $localState")
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { localState = "Обновлено: ${System.currentTimeMillis()}" }) {
                    Text("Обновить локальное состояние")
                }
            }
        }
    }
}

@Composable
fun ReactiveItem(title: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}