package com.example.daily.ui.Home.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.FavouriteModel
import com.example.daily.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: CollectionRepository

    val allFavourite: LiveData<List<FavouriteModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        val favouriteDao = AppDatabase.getInstance(application).favouriteDao()
        repository = CollectionRepository(collectionDao, addContentDao,favouriteDao)
        allFavourite = repository.allFavourite
    }

    fun insert(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(favourite)
    }
//    fun updateCollectionName(nameCollection:String)= viewModelScope.launch(Dispatchers.IO) {
//        repository.updateCollectionName(nameCollection)
//    }



}