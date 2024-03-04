package com.example.daily.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.daily.database.dao.CollectionDao
import com.example.daily.model.CollectionModel


@Database(entities = [CollectionModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "daily-database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}


