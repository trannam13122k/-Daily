package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
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

    @Update
    fun updateContent(addModel: AddModel)

    @Delete
    fun deleteContent(addModel: AddModel)

    @Query("SELECT COUNT(*) FROM 'add' WHERE nameAdd = :name")
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

    @Query("UPDATE `add` SET nameAdd = :nameAdd, isFavourite = :isFavourite")
    suspend fun updateName(nameAdd: String, isFavourite: Boolean)

    @Query("UPDATE 'add' SET nameCollection = :nameCollection WHERE id = :id")
    suspend fun updateNameCollection(id: Long, nameCollection: String)

    @Query("UPDATE 'add' SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun updateFavourite(id: Long, isFavourite: Boolean)

    @Query("SELECT * FROM 'add' WHERE nameCollection = :nameCollection")
    suspend fun getItemsByCollection(nameCollection: String): List<AddModel>
}