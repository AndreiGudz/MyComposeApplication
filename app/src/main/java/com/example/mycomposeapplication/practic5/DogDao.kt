package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DogDao {
    @Query("SELECT * FROM dogs")
    fun getAll(): LiveData<List<Dog>>

    @Query("SELECT * FROM dogs WHERE name = :dogName")
    fun getDog(dogName: String): List<Dog>

    @Insert
    fun add(dog: Dog)

    @Delete
    fun delete(dog: Dog)

    @Query("DELETE FROM dogs WHERE id = :dogId")
    fun deleteById(dogId: Long)

    @Update
    fun update(dog: Dog)

    @Query("UPDATE dogs SET name = :name, breed = :breed, age = :age, size = :size, isTrained = :isTraned WHERE id = :id")
    fun updateById(id: Long, name: String, breed: String, age: Int, size: String, isTraned: Boolean)

    @Query("UPDATE dogs SET ownerId = :ownerId WHERE id = :dogId")
    fun updateOwnerById(dogId: Long, ownerId: Long)
}