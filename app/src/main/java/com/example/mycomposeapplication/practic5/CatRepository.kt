package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CatRepository(private val catDao: CatDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val catList: LiveData<List<Cat>> = catDao.getAll()

    fun addCat(cat: Cat) {
        coroutineScope.launch {
            catDao.add(cat)
        }
    }

    fun deleteCat(id: Long) {
        coroutineScope.launch {
            catDao.deleteById(id)
        }
    }

    fun updateCat(id: Long, name: String, breed: String, age: Int, likeCatnip: Boolean) {
        coroutineScope.launch {
            catDao.updateById(id, name, breed, age, likeCatnip)
        }
    }

    fun updateCat(cat: Cat) {
        coroutineScope.launch {
            catDao.update(cat)
        }
    }

    fun updateCatOwner(catId: Long, ownerId: Long) {
        coroutineScope.launch {
            catDao.updateOwnerById(catId, ownerId)
        }
    }

    fun removeCatOwner(catId: Long) {
        coroutineScope.launch {
            catDao.removeOwnerById(catId)
        }
    }
}