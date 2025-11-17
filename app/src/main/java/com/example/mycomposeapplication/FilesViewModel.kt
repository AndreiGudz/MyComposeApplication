package com.example.mycomposeapplication

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.File

class FilesViewModel : ViewModel() {
    private val _fileContents = MutableStateFlow<List<String>>(emptyList())
    val fileContents: StateFlow<List<String>> = _fileContents.asStateFlow()

    private val _savedWeatherData = MutableStateFlow<List<WeatherResponse>>(emptyList())
    val savedWeatherData: StateFlow<List<WeatherResponse>> = _savedWeatherData.asStateFlow()

    private lateinit var fileManager: ForFileManipulation

    fun setFileManager(context: android.content.Context) {
        fileManager = ForFileManipulation(context)
    }

    // Пункт 1, 2, 3: Создание файлов и структуры папок
    fun createFilesAndStructure() {
        // 1. Создать текстовый файл во внутреннем хранилище
        fileManager.writeToInternalStorage("internal_test.txt", "Это тестовый файл внутреннего хранилища")

        // 2. Создать текстовый файл во внешнем хранилище
        fileManager.writeToExternalStorageDocuments(
            "external_test.txt",
            "text/plain",
            "Это тестовый файл внешнего хранилища"
        )

        // 3. Создать структуру папок
        fileManager.createNestedFileStructure()
    }

    // Пункт 4: Сканировать и вывести содержимое файлов
    fun scanAndDisplayFiles() {
        val fileList = mutableListOf<String>()
        fileManager.listFilesRecursively(resultList = fileList)
        _fileContents.value = fileList
    }

    // Пункт 5: Сохранить результаты HTTP запросов в JSON
    fun saveWeatherResponsesToJson(weatherResponses: List<WeatherResponse>) {
        fileManager.saveWeatherResponseToJsonFile(weatherResponses, "weather_data.json")
    }

    // Пункт 6: Загрузить сохраненные JSON файлы при запуске
    fun loadSavedWeatherData() {
        val loadedData = fileManager.readWeatherResponseFromJsonFile("weather_data.json")
        _savedWeatherData.value = loadedData
    }

    // Пункт 7: Очистить JSON файлы
    fun clearJsonFiles() {
        try {
            val jsonFile = File(fileManager.jsonFolder, "weather_data.json")
            if (jsonFile.exists()) {
                jsonFile.delete()
            }
            _savedWeatherData.value = emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Обновление списка сохраненных данных
    fun addToSavedWeatherData(weatherResponse: WeatherResponse) {
        _savedWeatherData.update { currentList ->
            currentList + weatherResponse
        }
    }
}