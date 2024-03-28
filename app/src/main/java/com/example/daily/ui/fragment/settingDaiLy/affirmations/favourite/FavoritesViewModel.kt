package com.example.daily.ui.fragment.settingDaiLy.affirmations.favourite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.FavouriteModel
import com.example.daily.repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RepositoryRoom

    val allFavourite: LiveData<List<FavouriteModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        val favouriteDao = AppDatabase.getInstance(application).favouriteDao()
        val editDao = AppDatabase.getInstance(application).editDao()
        repository = RepositoryRoom(collectionDao, addContentDao, favouriteDao, editDao)
        allFavourite = repository.allFavourite
    }

    fun insert(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(favourite)
    }

    fun deleteFavourite(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavourite(favourite)
    }
}