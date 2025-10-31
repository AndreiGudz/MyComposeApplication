package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerRepository(private val ownerDao: OwnerDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    val ownerList: LiveData<List<Owner>> = ownerDao.getAll()

    fun addOwner(owner: Owner) {
        coroutineScope.launch {
            ownerDao.add(owner)
        }
    }

    fun deleteOwner(id: Long) {
        coroutineScope.launch{
            ownerDao.deleteById(id)
        }
    }

    fun updateOwner(id: Long, name: String, phone: String, email: String) {
        coroutineScope.launch {
            ownerDao.updateById(id, name, phone, email)
        }
    }
}