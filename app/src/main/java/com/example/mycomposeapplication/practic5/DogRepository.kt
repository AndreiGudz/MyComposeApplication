package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogRepository(private val dogDao: DogDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val dogList: LiveData<List<Dog>> = dogDao.getAll()

    fun addDog(dog: Dog) {
        coroutineScope.launch {
            dogDao.add(dog)
        }
    }

    fun deleteDog(id: Long) {
        coroutineScope.launch {
            dogDao.deleteById(id)
        }
    }

    fun updateDog(id: Long, name: String, breed: String, age: Int, size: String, isTrained: Boolean) {
        coroutineScope.launch {
            dogDao.updateById(id, name, breed, age, size, isTrained)
        }
    }

    fun updateDog(dog: Dog) {
        coroutineScope.launch {
            dogDao.update(dog)
        }
    }

    fun updateDogOwner(dogId: Long, ownerId: Long) {
        coroutineScope.launch {
            dogDao.updateOwnerById(dogId, ownerId)
        }
    }

    fun removeDogOwner(dogId: Long) {
        coroutineScope.launch {
            dogDao.removeOwnerById(dogId)
        }
    }
}