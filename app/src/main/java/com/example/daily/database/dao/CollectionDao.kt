package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.daily.model.CollectionModel


@Dao
interface CollectionDao {
    @Insert
    fun insert(collection: CollectionModel)

    @Query("SELECT * FROM collection")
    fun getAllCollectionLiveData(): LiveData<List<CollectionModel>>

    @Delete
    fun delete(collection: CollectionModel)

    @Query("UPDATE collection SET nameCollection = :nameCollection")
    suspend fun update(nameCollection:  String)


    @Query("SELECT COUNT(*) FROM collection WHERE nameCollection = :name")
    suspend fun countCollectionsByName(name: String): Int

    @Transaction
    suspend fun insertIfNotExists(collection: CollectionModel) {
        val existingCount = countCollectionsByName(collection.nameCollection)

        if (existingCount == 0) {
            insert(collection)
        }
    }

}