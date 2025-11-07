package com.example.mycomposeapplication

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<WeatherUiState>(WeatherUiState.Empty)
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun searchWeather(cityName: String) {
        if (cityName.isBlank()) return

        viewModelScope.launch {
            _uiState.value = WeatherUiState.Loading

            try {
                // 1. Получаем координаты города
                val locations = ApiClient.geocodingService.searchLocation(cityName)

                if (locations.results.isNullOrEmpty()) {
                    _uiState.value = WeatherUiState.Error("Город '$cityName' не найден")
                    return@launch
                }

                val location = locations.results.first()
                Log.d("weather", "$location")

                // 2. Получаем погоду по координатам
                val weather = ApiClient.weatherService.getWeather(
                    latitude = location.latitude,
                    longitude = location.longitude
                )

                Log.d("weather", "$weather")

                _uiState.value = WeatherUiState.Success(
                    location = location,
                    weather = weather
                )

            } catch (e: Exception) {
                _uiState.value = WeatherUiState.Error("Ошибка: ${e.message ?: "Неизвестная ошибка"}")
            }
        }
    }
}

sealed class WeatherUiState {
    object Empty : WeatherUiState()
    object Loading : WeatherUiState()
    data class Success(
        val location: Location,
        val weather: WeatherResponse
    ) : WeatherUiState()
    data class Error(val message: String) : WeatherUiState()
}