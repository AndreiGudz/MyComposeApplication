package com.example.mycomposeapplication.practic5

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Cat::class), (Dog::class), (Owner::class)], version = 1)
abstract class MyDatabase: RoomDatabase() {

    abstract fun catDao(): CatDao
    abstract fun dogDao(): DogDao
    abstract fun ownerDao(): OwnerDao

    // реализуем синглтон
    companion object {

        private var INSTANCE: MyDatabase? = null
        fun getInstance(context: Context): MyDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyDatabase::class.java,
                        "My_database"

                    ).fallbackToDestructiveMigration(true).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}