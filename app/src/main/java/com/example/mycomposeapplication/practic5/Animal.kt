package com.example.mycomposeapplication.practic5

abstract class Animal(
    open val name: String,
    open val type: String,
    open val breed: String,
    open val ownerId: Long?,
    open var age: Int
){
    protected abstract fun deth(): Boolean
}

data class Cat(
    val id: Long = 0,
    override var name: String,
    override val type: String,
    override val breed: String,
    override val ownerId: Long?,
    override var age: Int,
    val likesCatnip: String
) : Animal(name, type, breed, ownerId, age) {
    override fun deth(): Boolean {
        return false
    }
}

data class Dog(
    val id: Long = 0,
    override var name: String,
    override val type: String,
    override val breed: String,
    override val ownerId: Long?,
    override var age: Int,
    val size: String,
    var isTrained: Boolean
) : Animal(name, type, breed, ownerId, age) {

    override fun deth(): Boolean {
        return age > 15
    }
}
