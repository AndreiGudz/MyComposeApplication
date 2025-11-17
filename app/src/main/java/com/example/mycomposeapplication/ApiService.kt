package com.example.mycomposeapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    @GET("v1/search")
    suspend fun searchLocation(
        @Query("name") cityName: String,
        @Query("count") count: Int = 1,
        @Query("language") language: String = "ru"
    ): GeocodingResponse
}

interface WeatherApiService {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
//        @Query("hourly") hourly: String = "temperature_2m,weather_code",
        @Query("current") current: String = "temperature_2m,weather_code",
        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}

object ApiClient {
    private const val GEOCODING_BASE_URL = "https://geocoding-api.open-meteo.com/"
    private const val WEATHER_BASE_URL = "https://api.open-meteo.com/"

    val geocodingService: GeocodingApiService by lazy {
        Retrofit.Builder()
            .baseUrl(GEOCODING_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeocodingApiService::class.java)
    }

    val weatherService: WeatherApiService by lazy {
        Retrofit.Builder()
            .baseUrl(WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApiService::class.java)
    }
}
