package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.daily.model.AddModel

@Dao
interface AddContentDao {
    @Insert
    fun insert(addModel: AddModel)

    @Query("SELECT * FROM `add`")
    fun getAllContentLiveData(): LiveData<List<AddModel>>

    @Query("SELECT COUNT(*) FROM collection WHERE nameCollection = :name")
    suspend fun countCollectionsByName(name: String): Int

    @Transaction
    suspend fun insertIfNotExists(addModel: AddModel) {
        val existingCount = countCollectionsByName(addModel.nameAdd)

        if (existingCount == 0) {
            insert(addModel)
        }
    }
    @Query("UPDATE collection SET nameCollection = :nameCollection")
    suspend fun updateCollectionName(nameCollection: String)

    @Query("UPDATE 'add' SET nameCollection = :nameCollection WHERE id = :id")
    suspend fun updateNameCollection(id: Long, nameCollection: String)

    @Query("SELECT * FROM 'add' WHERE nameCollection = :nameCollection")
    suspend fun getItemsByCollection(nameCollection: String): List<AddModel>
}