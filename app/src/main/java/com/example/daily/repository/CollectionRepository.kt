package com.example.daily.repository

import androidx.lifecycle.LiveData
import com.example.daily.database.dao.AddContentDao
import com.example.daily.database.dao.CollectionDao
import com.example.daily.model.AddModel
import com.example.daily.model.CollectionModel
import com.example.daily.ui.Home.ContentModel

class CollectionRepository(
    private val collectionDao: CollectionDao,
    private val addContentDao: AddContentDao) {

    val allCollection : LiveData<List<CollectionModel>> = collectionDao.getAllCollectionLiveData()

    val allContent :LiveData<List<AddModel>> =addContentDao.getAllContentLiveData()

    suspend fun insert(collection : CollectionModel){
        collectionDao.insertIfNotExists(collection)
    }

    suspend fun update(collection: String){
        collectionDao.update(collection)
    }
    suspend fun countCollectionsByName(name: String): Int {
        return collectionDao.countCollectionsByName(name)
    }

    suspend fun delete(collection: CollectionModel){
        collectionDao.delete(collection)
    }

    suspend fun insertContent(addModel: AddModel){
        addContentDao.insertIfNotExists(addModel)
    }
    suspend fun updateCollectionName(nameCollection: String){
        addContentDao.updateCollectionName(nameCollection)
    }
    suspend fun updateNameCollection(id: Long, nameCollection: String) {
        addContentDao.updateNameCollection(id, nameCollection)
    }

    suspend fun getItemsByCollection(nameCollection: String): List<AddModel> {
        return addContentDao.getItemsByCollection(nameCollection)
    }
}