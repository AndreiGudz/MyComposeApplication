package com.example.mycomposeapplication

import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val current: CurrentWeather,
//    val hourly: HourlyWeather?
)

data class CurrentWeather(
    val temperature_2m: Double,
    val weather_code: Int,
    val time: String
)

//data class HourlyWeather(
//    val time: List<String>,
//    val weather_code: List<Int>,
//    val temperature_2m: List<Double>
//)

data class GeocodingResponse(
    val results: List<Location>
)

data class Location(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String
)

object WeatherUtils {

    fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Ясно"
            1 -> "Преимущественно ясно"
            2 -> "Переменная облачность"
            3 -> "Пасмурно"
            45, 48 -> "Туман"
            51, 53, 55 -> "Морось"
            56, 57 -> "Ледяная морось"
            61, 63, 65 -> "Дождь"
            66, 67 -> "Ледяной дождь"
            71, 73, 75 -> "Снег"
            77 -> "Снежные зерна"
            80, 81, 82 -> "Ливень"
            85, 86 -> "Снегопад"
            95 -> "Гроза"
            96, 99 -> "Гроза с градом"
            else -> "Неизвестно"
        }
    }

    fun getWeatherIcon(code: Int): String {
        return when (code) {
            0 -> "☀️"
            1, 2 -> "⛅"
            3 -> "☁️"
            45, 48 -> "🌫️"
            51, 53, 55, 56, 57 -> "🌧️"
            61, 63, 65, 66, 67 -> "🌧️"
            71, 73, 75, 77, 85, 86 -> "❄️"
            80, 81, 82 -> "⛈️"
            95, 96, 99 -> "⛈️"
            else -> "🌈"
        }
    }

    fun formatTime(timeString: String): String {
        return try {
            val dateTime = LocalDateTime.parse(timeString)
            val formatter = DateTimeFormatter.ofPattern("dd-EEE HH:mm", Locale.getDefault())
            dateTime.format(formatter)
        } catch (e: Exception) {
            timeString.substring(11, 16)
        }
    }
}