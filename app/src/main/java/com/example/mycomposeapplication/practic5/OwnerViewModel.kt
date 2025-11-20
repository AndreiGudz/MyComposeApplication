package com.example.mycomposeapplication.practic5

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import kotlin.reflect.KClass

class OwnerViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return OwnerViewModel(application) as T
    }
}

class OwnerViewModel(application: Application) : ViewModel() {

    val ownersList: LiveData<List<Owner>>
    val ownersWithAnimals: LiveData<List<OwnerWithAnimals>>
        get() = repositoryWithAnimals.ownersWithAnimals
    private val repository: OwnerRepository
    private val repositoryWithAnimals: OwnerWithAnimalsRepository

    var editingOwnerId by mutableStateOf<Long?>(null)
    var ownerName by mutableStateOf("")
    var ownerPhone: String by mutableStateOf("")
    var ownerEmail: String by mutableStateOf("")


    init {
        val myDb = MyDatabase.getInstance(application)
        val ownerDao = myDb.ownerDao()
        repository = OwnerRepository(ownerDao)
        repositoryWithAnimals = OwnerWithAnimalsRepository(ownerDao)
        ownersList = repository.ownerList
    }

    fun loadOwnersWithAnimals() {
        repositoryWithAnimals.loadAllOwnersWithAnimals()
    }

    fun changeName(value: String){
        ownerName = value
    }

    fun changePhone(value: String){
        ownerPhone = value
    }

    fun changeEmail(value: String){
        ownerEmail = value
    }

    fun addOwner() {
        repository.addOwner(
            Owner(
                name = ownerName,
                phone = ownerPhone,
                email = ownerEmail
            )
        )
    }

    fun deleteOwner(id: Long) {
        repository.deleteOwner(id)
    }

    fun updateOwner(id: Long) {
        repository.updateOwner(id, ownerName, ownerPhone, ownerEmail)
        cancelEditing()
    }

    fun startEditing(owner: Owner) {
        editingOwnerId = owner.id
        ownerName = owner.name
        ownerPhone = owner.phone
        ownerEmail = owner.email
    }

    fun cancelEditing() {
        editingOwnerId = null
        clearForm()
    }

    private fun clearForm() {
        ownerName = ""
        ownerPhone = ""
        ownerEmail = ""
        editingOwnerId = null
    }
}