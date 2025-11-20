package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OwnerDao {
    @Query("SELECT * FROM owners")
    fun getAll(): LiveData<List<Owner>>

    @Query("SELECT * FROM owners WHERE name = :ownerName")
    fun getOwnerByName(ownerName: String): List<Owner>

    @Query("SELECT * FROM owners WHERE id = :ownerId")
    suspend fun getOwnerById(ownerId: Long): Owner

    @Insert
    fun add(owner: Owner)

    @Delete
    fun delete(owner: Owner)

    @Query("DELETE FROM owners WHERE id = :ownerId")
    fun deleteById(ownerId: Long)

    @Query("UPDATE owners SET name = :name, phone = :phone, email = :email WHERE id = :id")
    fun updateById(id: Long, name: String, phone: String, email: String)

    @Query("SELECT * FROM owners")
    suspend fun getAllOwnersWithAnimals(): List<OwnerWithAnimals>
}