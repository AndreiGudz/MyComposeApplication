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

    var editingDogId by mutableStateOf<Long?>(null)
    var dogName by mutableStateOf("")
    var dogBreed by mutableStateOf("")
    var dogAge by mutableStateOf("")
    var dogSize by mutableStateOf("")
    var isTrained by mutableStateOf(false)
    var dogOwnerId by mutableStateOf("")

    val sizeValues = listOf("Small", "Medium", "Large")

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

        clearForm()
    }

    fun deleteDog(id: Long) {
        repository.deleteDog(id)
    }

    fun updateDog(id: Long) {
        val age = dogAge.toIntOrNull() ?: 0
        if (!sizeValues.contains(dogSize))
            dogSize = ""

        repository.updateDog(
            id = id,
            name = dogName,
            breed = dogBreed,
            age = age,
            size = dogSize,
            isTrained = isTrained
        )

        updateDogOwner(id)

        cancelEditing()
    }

    fun updateDogOwner(dogId: Long) {
        val ownerId = dogOwnerId.toLongOrNull() ?: return
        repository.updateDogOwner(dogId, ownerId)
    }

    fun startEditing(dog: Dog) {
        editingDogId = dog.id
        dogName = dog.name
        dogBreed = dog.breed
        dogAge = dog.age.toString()
        dogSize = dog.size
        isTrained = dog.isTrained
        dogOwnerId = dog.ownerId?.toString() ?: ""
    }

    fun cancelEditing() {
        editingDogId = null
        clearForm()
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