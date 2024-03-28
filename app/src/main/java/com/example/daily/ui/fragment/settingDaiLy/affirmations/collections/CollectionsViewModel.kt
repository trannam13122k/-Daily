package com.example.daily.ui.fragment.settingDaiLy.affirmations.collections

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.AddModel
import com.example.daily.model.CollectionModel
import com.example.daily.model.FavouriteModel
import com.example.daily.repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RepositoryRoom

    val allCollections: LiveData<List<CollectionModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        val favouriteDao = AppDatabase.getInstance(application).favouriteDao()
        val editDao = AppDatabase.getInstance(application).editDao()
        repository = RepositoryRoom(collectionDao, addContentDao, favouriteDao, editDao)
        allCollections = repository.allCollection
    }

    fun insertCollections(collection: CollectionModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(collection)
    }

    fun deleteCollection(collection: CollectionModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(collection)
    }

    fun updateNameCollection(id: Long, nameCollection: String) {
        viewModelScope.launch {
            repository.updateNameCollection(id, nameCollection)
        }
    }

    fun getItemsByCollection(nameCollection: String): LiveData<List<AddModel>> {
        return liveData {
            emit(repository.getItemsByCollection(nameCollection))
        }
    }

    fun insertFavourite(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(favourite)
    }

    fun updateContent(addModel: AddModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateContent(addModel)
    }

    fun deleteFavourite(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavouriteByName(name)
    }


}
