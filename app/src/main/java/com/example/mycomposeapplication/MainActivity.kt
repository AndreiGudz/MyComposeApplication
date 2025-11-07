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
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel


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

@Composable
fun WeatherApp(viewModel: WeatherViewModel = viewModel()) {
    var cityInput by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Поле поиска
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = cityInput,
                onValueChange = { cityInput = it },
                label = { Text("Название города") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Button(
                onClick = { viewModel.searchWeather(cityInput) },
                enabled = cityInput.isNotBlank()
            ) {
                Text("Найти")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Отображение состояния
        when (val state = uiState) {
            is WeatherUiState.Empty -> {
                PlaceholderText("Введите город для поиска погоды")
            }
            is WeatherUiState.Loading -> {
                LoadingIndicator()
            }
            is WeatherUiState.Error -> {
                ErrorMessage(state.message)
            }
            is WeatherUiState.Success -> {
                WeatherContent(state.location, state.weather)
            }
        }
    }
}

@Composable
fun WeatherContent(location: Location, weather: WeatherResponse) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            CurrentWeatherCard(location, weather)
        }

        item {
            Text(
                "Ближайшие часы:",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (weather.hourly != null)
            itemsIndexed(weather.hourly.time.take(30)) { index, time ->
                HourlyWeatherItem(
                    time = time,
                    temperature = weather.hourly.temperature_2m[index],
                    weatherCode = weather.hourly.weather_code[index]
                )
            }
        else {
            item {
                Text("Погода на ближайшие часы не найдена")
            }
        }
    }
}

@Composable
fun CurrentWeatherCard(location: Location, weather: WeatherResponse) {
    Card {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                "${location.name}, ${location.country}",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    WeatherUtils.getWeatherIcon(weather.current.weather_code),
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        "${weather.current.temperature_2m}°C",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(WeatherUtils.getWeatherDescription(weather.current.weather_code))
                }
            }
        }
    }
}

@Composable
fun HourlyWeatherItem(time: String, temperature: Double, weatherCode: Int) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(WeatherUtils.formatTime(time))
            Text(WeatherUtils.getWeatherIcon(weatherCode))
            Text("${temperature}°C", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("❌", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(message)
        }
    }
}

@Composable
fun PlaceholderText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyContent()
}