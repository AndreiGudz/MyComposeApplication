package com.example.mycomposeapplication.practic5

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CatViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CatViewModel(application) as T
    }
}

class CatViewModel(application: Application) : ViewModel() {

    val catsList: LiveData<List<Cat>>
    val ownersList: LiveData<List<Owner>>
    private val repository: CatRepository
    private val ownerRepository: OwnerRepository
    var editingCatId by mutableStateOf<Long?>(null)
    var catName by mutableStateOf("")
    var catBreed by mutableStateOf("")
    var catAge by mutableStateOf("")
    var likeCatnip by mutableStateOf(false)
    var catOwnerId by mutableStateOf("")

    init {
        val myDb = MyDatabase.getInstance(application)
        val catDao = myDb.catDao()
        val ownerDao = myDb.ownerDao()
        ownerRepository = OwnerRepository(ownerDao)
        repository = CatRepository(catDao)
        catsList = repository.catList
        ownersList = ownerRepository.ownerList
    }

    fun changeName(value: String) {
        catName = value
    }

    fun changeBreed(value: String) {
        catBreed = value
    }

    fun changeAge(value: String) {
        catAge = value
    }

    fun changeLikeCatnip(value: Boolean) {
        likeCatnip = value
    }

    fun changeOwnerId(value: String) {
        catOwnerId = value
    }

    fun addCat() {
        val age = catAge.toIntOrNull() ?: 0
        val ownerId = catOwnerId.toLongOrNull()

        repository.addCat(
            Cat(
                name = catName,
                breed = catBreed,
                age = age,
                likeCatnip = likeCatnip,
                ownerId = ownerId
            )
        )

        clearForm()
    }

    fun deleteCat(id: Long) {
        repository.deleteCat(id)
    }

    fun updateCat(id: Long) {
        val age = catAge.toIntOrNull() ?: 0

        repository.updateCat(
            id = id,
            name = catName,
            breed = catBreed,
            age = age,
            likeCatnip = likeCatnip
        )

        updateCatOwner(id)
        cancelEditing()
    }

    fun updateCatOwner(catId: Long) {
        val ownerId = if (catOwnerId.isNotEmpty()) catOwnerId.toLongOrNull() else null
        if (ownerId != null) {
            repository.updateCatOwner(catId, ownerId)
        } else {
            repository.removeCatOwner(catId)
        }
    }

    fun startEditing(cat: Cat) {
        editingCatId = cat.id
        catName = cat.name
        catBreed = cat.breed
        catAge = cat.age.toString()
        likeCatnip = cat.likeCatnip
        catOwnerId = cat.ownerId?.toString() ?: ""
    }

    fun cancelEditing() {
        editingCatId = null
        clearForm()
    }

    private fun clearForm() {
        catName = ""
        catBreed = ""
        catAge = ""
        likeCatnip = false
        catOwnerId = ""
    }

    fun getOwnerNameById(ownerId: Long?): String {
        if (ownerId == null) return "No Owner"
        val owners = ownersList.value ?: return "No Owner"
        return owners.find { it.id == ownerId }?.name ?: "No Owner"
    }
}