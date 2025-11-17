package com.example.mycomposeapplication

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File

class ForFileManipulation(val context: Context) {
    val jsonFolder: File = File(context.filesDir, "json_folder")
    fun writeToInternalStorage(filename: String, data: String) {
        context.openFileOutput(filename, Context.MODE_PRIVATE).use { stream ->
            stream.write(data.toByteArray())
        }
        // writeToInternalStorage("my_internal_file.txt", "Hello Internal World!")
        // Файл будет находиться в: /data/data/your.package.name/files/filename.txt
    }

    fun writeToExternalStorageDocuments(
        filename: String,
        mimeType: String,
        data: String
    ) {
        val contentResolver = context.contentResolver

        // Определяем, куда мы хотим сохранить файл (в данном случае, в коллекцию Documents)
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
            put(MediaStore.MediaColumns.MIME_TYPE, mimeType) // e.g., "text/plain"
            put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/MyApp")
        }

        // Вставляем запись в MediaStore и получаем URI для нового файла
        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        uri?.let {
            contentResolver.openOutputStream(it)?.use { outputStream ->
                outputStream.write(data.toByteArray())
            }
        }
        // writeToExternalStorageDocuments("my_external_file.txt", "text/plain", "Hello External World!")
    }

    fun listFilesRecursively(directory: File = context.filesDir, resultList: MutableList<String>) {
        val files = directory.listFiles() ?: return // Если директории нет или она пуста, выходим

        files.forEach { file ->
            if (file.isDirectory) {
                // Рекурсивный вызов для поддиректории
                listFilesRecursively(file, resultList)
            } else {
                // Это файл, считываем его содержимое и добавляем в список
                val content = file.readText()
                resultList.add("Path: ${file.absolutePath}\nName: ${file.name}\nContent: $content")
            }
        }
    }

    fun createNestedFileStructure() {
        val baseDir = context.filesDir // /data/data/your.package.name/files

        // Создаем несколько папок в цикле
        val folders = listOf("FolderA", "FolderB", "FolderC")

        folders.forEach { folderName ->
            // Создаем основную папку
            val mainFolder = File(baseDir, folderName)
            if (!mainFolder.exists()) {
                mainFolder.mkdirs()
            }

            // Внутри каждой основной папки создаем подпапки
            val subFolders = List(3) {
                val subFolder = File(mainFolder, "SubFolder_$it")
                if (!subFolder.exists()) {
                    subFolder.mkdirs()
                }
                subFolder
            }

            // В каждой второй подпапке создаем текстовый файл
            subFolders.forEachIndexed { index, subFolder ->
                if (index % 2 == 0) {
                    val file = File(subFolder, "file_$index.txt")
                    file.writeText("This is content for file in $folderName")
                }
            }
        }
    }

    // 2. Функция для сохранения списка объектов в JSON файл во внутреннем хранилище
    fun saveWeatherResponseToJsonFile(weatherResponses: List<WeatherResponse>, filename: String) {
        val gson = Gson()
        val jsonString = gson.toJson(weatherResponses)
        val file = File(jsonFolder, filename)
        file.writeText(jsonString)
    }

    // 1. Функция для чтения JSON файла и парсинга его обратно в список объектов
    fun readWeatherResponseFromJsonFile(filename: String): List<WeatherResponse> {
        return try {
            val file = File(jsonFolder, filename)
            if (!file.exists()) {
                return emptyList()
            }
            val jsonString = file.readText()
            val gson = Gson()
            val type = object : TypeToken<List<WeatherResponse>>() {}.type
            gson.fromJson<List<WeatherResponse>>(jsonString, type) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    init {
        if (!jsonFolder.exists()) {
            jsonFolder.mkdirs()
        }
    }
}