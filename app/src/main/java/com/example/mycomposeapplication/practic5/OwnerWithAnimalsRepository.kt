package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OwnerWithAnimalsRepository(private val ownerDao: OwnerDao) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _ownersWithAnimals = MutableLiveData<List<OwnerWithAnimals>>()

    val ownersWithAnimals: LiveData<List<OwnerWithAnimals>> = _ownersWithAnimals

    fun loadAllOwnersWithAnimals() {
        coroutineScope.launch {
            val ownersWithAnimals = ownerDao.getAllOwnersWithAnimals()
            _ownersWithAnimals.postValue(ownersWithAnimals)
        }
    }
}