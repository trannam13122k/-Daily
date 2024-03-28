package com.example.daily.repository

import androidx.lifecycle.LiveData
import com.example.daily.database.dao.AddContentDao
import com.example.daily.database.dao.CollectionDao
import com.example.daily.database.dao.EditDao
import com.example.daily.database.dao.FavouriteDao
import com.example.daily.model.AddModel
import com.example.daily.model.CollectionModel
import com.example.daily.model.EditModel
import com.example.daily.model.FavouriteModel

class RepositoryRoom(
    private val collectionDao: CollectionDao,
    private val addContentDao: AddContentDao,
    private val favouriteDao: FavouriteDao,
    private val editDao: EditDao
) {

    val allCollection: LiveData<List<CollectionModel>> = collectionDao.getAllCollectionLiveData()

    val allContent: LiveData<List<AddModel>> = addContentDao.getAllContentLiveData()

    val allFavourite: LiveData<List<FavouriteModel>> = favouriteDao.getAllFavouriteLiveData()

    val allAddContent: LiveData<List<AddModel>> = addContentDao.getAllContentLiveData()

    val allEdit: LiveData<List<EditModel>> = editDao.getAllLiveData()

    suspend fun insert(collection: CollectionModel) {
        collectionDao.insertIfNotExists(collection)
    }

    fun edit(): EditModel? =
        if (editDao.getAllData().isNotEmpty()) editDao.getAllData().last() else null

    suspend fun update(collection: String) {
        collectionDao.update(collection)
    }

    fun delete(collection: CollectionModel) {
        collectionDao.delete(collection)
    }

    suspend fun insertContent(addModel: AddModel) {
        addContentDao.insertIfNotExists(addModel)
    }

    suspend fun updateCollectionName(nameCollection: String) {
        addContentDao.updateCollectionName(nameCollection)
    }

    suspend fun updateNameCollection(id: Long, nameCollection: String) {
        addContentDao.updateNameCollection(id, nameCollection)
    }

    suspend fun getItemsByCollection(nameCollection: String): List<AddModel> {
        return addContentDao.getItemsByCollection(nameCollection)
    }

    suspend fun insertFavourite(favourite: FavouriteModel) {
        favouriteDao.insertIfNotExists(favourite)
    }

    fun updateContent(addModel: AddModel) {
        addContentDao.updateContent(addModel)
    }

    fun deleteContent(addModel: AddModel) {
        addContentDao.deleteContent(addModel)
    }

    suspend fun deleteFavouriteByName(name: String) {
        favouriteDao.deleteByName(name)
    }

    fun insertEdit(editModel: EditModel) {
        editDao.insert(editModel)
    }

    fun updateEdit(editModel: EditModel) {
        editDao.update(editModel)
    }

    suspend fun deleteFavourite(favourite: FavouriteModel) {
        favouriteDao.deleteFavourite(favourite)
    }
    suspend fun deleteFavourite(name: String) {
        favouriteDao.deleteFavouriteByName(name)
    }

    suspend fun updateName(name:String, isFavourite:Boolean){
        addContentDao.updateName(name,isFavourite)
    }
}