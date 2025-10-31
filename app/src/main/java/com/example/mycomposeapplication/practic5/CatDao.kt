package com.example.mycomposeapplication.practic5

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CatDao {
    @Query("SELECT * FROM cats")
    fun getAll(): LiveData<List<Cat>>

    @Query("SELECT * FROM cats WHERE name = :catName")
    fun getCat(catName: String): List<Cat>

    @Insert
    fun add(cat: Cat)

    @Delete
    fun delete(cat: Cat)

    @Query("DELETE FROM cats WHERE id = :catId")
    fun deleteById(catId: Long)

    @Update
    fun update(cat: Cat)

    @Query("UPDATE cats SET name = :name, breed = :breed, age = :age, likeCatnip = :likeCatnip WHERE id = :id")
    fun updateById(id: Long, name: String, breed: String, age: Int, likeCatnip: Boolean)

    @Query("UPDATE cats SET ownerId = :ownerId WHERE id = :catId")
    fun updateOwnerById(catId: Long, ownerId: Long)
}