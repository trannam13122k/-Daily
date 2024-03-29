package com.example.daily.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.daily.database.dao.AddContentDao
import com.example.daily.database.dao.CollectionDao
import com.example.daily.database.dao.FavouriteDao
import com.example.daily.model.AddModel
import com.example.daily.model.CollectionModel
import com.example.daily.model.FavouriteModel


@Database(entities = [CollectionModel::class, AddModel::class, FavouriteModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao
    abstract fun addContentDao(): AddContentDao
    abstract fun favouriteDao() : FavouriteDao
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


