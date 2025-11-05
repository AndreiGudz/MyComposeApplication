package com.example.mycomposeapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

fun createApiService(): ApiService {
    val myIp = "192.168.119.60"
    val emulatorIp = "10.0.2.2"
    val retrofit = Retrofit.Builder()
        .baseUrl("http://${emulatorIp}:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(ApiService::class.java)
}

interface ApiService {
    @GET("/")
    suspend fun getHelloWorld(): HelloWorldResponse

    @POST("/")
    suspend fun postHelloWorld(): HelloWorldResponse

    @POST("/sum")
    suspend fun calculateSum(@Body request: SumRequest): SumResponse

    @GET("/items")
    suspend fun getAllItems(): List<ItemResponse>

    @POST("/items/search")
    suspend fun searchItems(@Body request: ItemRequest): List<ItemResponse>
}

// Data classes для запросов и ответов
data class SumRequest(
    val number1: Float,
    val number2: Float
)

data class SumResponse(
    val number1: Float,
    val number2: Float,
    val sum: Float
)

data class ItemRequest(
    val name: String
)

data class ItemResponse(
    val id: Int,
    val name: String,
    val description: String
)

data class HelloWorldResponse(
    val message: String
)