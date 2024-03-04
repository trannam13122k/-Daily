package com.example.daily.repository

import androidx.lifecycle.LiveData
import com.example.daily.database.dao.CollectionDao
import com.example.daily.model.CollectionModel

class CollectionRepository(private val collectionDao: CollectionDao) {

    val allCollection : LiveData<List<CollectionModel>> = collectionDao.getAllCollectionLiveData()

    fun insert(collection : CollectionModel){
        collectionDao.insert(collection)
    }

    suspend fun update(collection: String){
        collectionDao.update(collection)
    }

    suspend fun delete(collection: CollectionModel){
        collectionDao.delete(collection)
    }


}