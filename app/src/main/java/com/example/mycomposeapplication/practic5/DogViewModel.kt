package com.example.mycomposeapplication.practic5

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DogViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DogViewModel(application) as T
    }
}

class DogViewModel(application: Application) : ViewModel() {

    val dogsList: LiveData<List<Dog>>
    private val repository: DogRepository

    // Состояние для формы добавления/редактирования
    var dogName by mutableStateOf("")
    var dogBreed by mutableStateOf("")
    var dogAge by mutableStateOf("")
    var dogSize by mutableStateOf("")
    var isTrained by mutableStateOf(false)
    var dogOwnerId by mutableStateOf("")

    init {
        val myDb = MyDatabase.getInstance(application)
        val dogDao = myDb.dogDao()
        repository = DogRepository(dogDao)
        dogsList = repository.dogList
    }

    fun changeName(value: String) {
        dogName = value
    }

    fun changeBreed(value: String) {
        dogBreed = value
    }

    fun changeAge(value: String) {
        dogAge = value
    }

    fun changeSize(value: String) {
        dogSize = value
    }

    fun changeIsTrained(value: Boolean) {
        isTrained = value
    }

    fun changeOwnerId(value: String) {
        dogOwnerId = value
    }

    fun addDog() {
        val age = dogAge.toIntOrNull() ?: 0
        val ownerId = dogOwnerId.toLongOrNull()

        repository.addDog(
            Dog(
                name = dogName,
                breed = dogBreed,
                age = age,
                size = dogSize,
                isTrained = isTrained,
                ownerId = ownerId
            )
        )

        // Очистка формы после добавления
        clearForm()
    }

    fun deleteDog(id: Long) {
        repository.deleteDog(id)
    }

    fun updateDog(id: Long) {
        val age = dogAge.toIntOrNull() ?: 0

        repository.updateDog(
            id = id,
            name = dogName,
            breed = dogBreed,
            age = age,
            size = dogSize,
            isTrained = isTrained
        )

        // Очистка формы после обновления
        clearForm()
    }

    fun updateDogOwner(dogId: Long) {
        val ownerId = dogOwnerId.toLongOrNull() ?: return
        repository.updateDogOwner(dogId, ownerId)
    }

    private fun clearForm() {
        dogName = ""
        dogBreed = ""
        dogAge = ""
        dogSize = ""
        isTrained = false
        dogOwnerId = ""
    }
}