package com.example.mycomposeapplication.practic5

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerWithAnimals(
    @Embedded
    val owner: Owner,
    @Relation(entity = Cat::class, parentColumn = "id", entityColumn = "ownerId")
    val cats: List<Cat>,
    @Relation(entity = Dog::class, parentColumn = "id", entityColumn = "ownerId")
    val dogs: List<Dog>
)