package com.example.daily.ui.Setting.collections.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.CollectionModel
import com.example.daily.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CollectionsViewModel(application: Application) :AndroidViewModel(application) {
    private val repository: CollectionRepository

    val allCollections: LiveData<List<CollectionModel>>
    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        repository = CollectionRepository(collectionDao)
        allCollections = repository.allCollection

    }
    fun insertCollections(collection: CollectionModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(collection)
    }

    fun updateCollection(collection: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(collection)
    }
    fun deleteCollection(collection: CollectionModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(collection)
    }




}