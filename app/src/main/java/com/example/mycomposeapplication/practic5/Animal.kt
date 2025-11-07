package com.example.mycomposeapplication.practic5

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey

abstract class Animal(
    open val name: String,
    open val breed: String,
    open val ownerId: Long?,
    open var age: Int
){
    abstract fun introduceOneself(): String

    fun externalEvaluation() = "this is animal $name, breed $breed, $age years old " +
            "${if (ownerId != null) "with" else "without"} owner"
}

@Entity(
    tableName = "cats",
    foreignKeys = [ForeignKey(
        entity = Owner::class,
        parentColumns = ["id"],
        childColumns = ["ownerId"],
        onDelete = SET_NULL,
        onUpdate = CASCADE)
    ]
)
data class Cat(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    override var name: String,
    override val breed: String,
    override val ownerId: Long? = null,
    override var age: Int,
    val likeCatnip: Boolean
) : Animal(name, breed, ownerId, age) {

    override fun introduceOneself(): String {
        return "Meaw. I am cat $name, breed $breed, $age years old. " +
                "I ${if (likeCatnip) "like" else "don't like"} Catnip"
    }

}

@Entity(
    tableName = "dogs",
    foreignKeys = [ForeignKey(
        entity = Owner::class,
        parentColumns = ["id"],
        childColumns = ["ownerId"],
        onDelete = SET_NULL,
        onUpdate = CASCADE)
    ]
)
data class Dog(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    override var name: String,
    override val breed: String,
    override val ownerId: Long?,
    override var age: Int,
    val size: String,
    var isTrained: Boolean
) : Animal(name, breed, ownerId, age) {

    override fun introduceOneself(): String {
        return "Wow. I am $size dog $name, breed $breed, $age years old. " +
                "I am ${if (isTrained) "a trained" else "not a trained"} dog"
    }
}
