package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.daily.model.CollectionModel

@Dao
interface CollectionDao {
    @Insert
    fun insert(collection: CollectionModel)

    @Query("SELECT * FROM collection")
    fun getAllCollectionLiveData(): LiveData<List<CollectionModel>>

//    @Query("DELETE  FROM collection WHERE nameCollection = :nameCollection")
//    fun delete(nameCollection: String) : Int
    @Delete
    fun delete(collection: CollectionModel)

    @Query("UPDATE collection SET nameCollection = :nameCollection")
    suspend fun update(nameCollection:  String)

}