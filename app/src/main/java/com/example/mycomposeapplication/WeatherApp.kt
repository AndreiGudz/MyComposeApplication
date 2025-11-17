package com.example.mycomposeapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun WeatherApp() {
    val context = LocalContext.current
    val weatherViewModel: WeatherViewModel = viewModel()
    val filesViewModel: FilesViewModel = viewModel()
    var cityName by remember { mutableStateOf("") }
    val weatherState by weatherViewModel.uiState.collectAsState()
    val fileContents by filesViewModel.fileContents.collectAsState()
    val savedWeatherData by filesViewModel.savedWeatherData.collectAsState()

    // Инициализация fileManager при первом запуске
    LaunchedEffect(Unit) {
        filesViewModel.setFileManager(context)
        filesViewModel.loadSavedWeatherData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поиск погоды
        TextField(
            value = cityName,
            onValueChange = { cityName = it },
            label = { Text("Введите город") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                weatherViewModel.searchWeather(cityName)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = cityName.isNotBlank()
        ) {
            Text("Получить погоду")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопки для работы с файлами
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = {
                filesViewModel.createFilesAndStructure()
            }) {
                Text("Создать файлы")
            }

            Button(onClick = {
                filesViewModel.scanAndDisplayFiles()
            }) {
                Text("Сканировать")
            }

            // Пункт 7: Кнопка "Очистить"
            Button(onClick = {
                filesViewModel.clearJsonFiles()
            }) {
                Text("Очистить JSON")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение текущей погоды
        when (weatherState) {
            is WeatherUiState.Loading -> {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is WeatherUiState.Success -> {
                val successState = weatherState as WeatherUiState.Success
                WeatherCard(
                    location = successState.location,
                    weather = successState.weather,
                    onSave = {
                        // Пункт 5: Сохраняем результат запроса в JSON
                        filesViewModel.addToSavedWeatherData(successState.weather)
                        filesViewModel.saveWeatherResponsesToJson(
                            filesViewModel.savedWeatherData.value + successState.weather
                        )
                    }
                )
            }
            is WeatherUiState.Error -> {
                Text(
                    text = (weatherState as WeatherUiState.Error).message,
                    color = MaterialTheme.colorScheme.error
                )
            }
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Пункт 4: Отображение содержимого файлов
        if (fileContents.isNotEmpty()) {
            Text("Содержимое файлов:", fontWeight = FontWeight.Bold)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(fileContents) { fileContent ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            text = fileContent,
                            modifier = Modifier.padding(8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Пункт 6: Отображение сохраненных JSON данных
        if (savedWeatherData.isNotEmpty()) {
            Text("Сохраненные данные погоды:", fontWeight = FontWeight.Bold)
            LazyColumn(modifier = Modifier.weight(1f)) {
                items(savedWeatherData) { weatherData ->
                    SavedWeatherCard(weatherData)
                }
            }
        }
    }
}

@Composable
fun WeatherCard(
    location: Location,
    weather: WeatherResponse,
    onSave: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${location.name}, ${location.country}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = WeatherUtils.getWeatherIcon(weather.current.weather_code),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Text(
                text = "Температура: ${weather.current.temperature_2m}°C",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                text = WeatherUtils.getWeatherDescription(weather.current.weather_code),
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onSave) {
                Text("Сохранить в JSON")
            }
        }
    }
}

@Composable
fun SavedWeatherCard(weatherData: WeatherResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = "Широта: ${weatherData.latitude}, Долгота: ${weatherData.longitude}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Температура: ${weatherData.current.temperature_2m}°C",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Погода: ${WeatherUtils.getWeatherDescription(weatherData.current.weather_code)}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Время: ${weatherData.current.time}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}