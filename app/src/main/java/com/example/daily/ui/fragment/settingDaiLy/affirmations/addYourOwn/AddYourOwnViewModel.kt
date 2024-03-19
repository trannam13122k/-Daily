package com.example.daily.ui.fragment.settingDaiLy.affirmations.addYourOwn

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.AddModel
import com.example.daily.model.FavouriteModel
import com.example.daily.repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddYourOwnViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RepositoryRoom

    val allContent: LiveData<List<AddModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        val favouriteDao = AppDatabase.getInstance(application).favouriteDao()
        val editDao = AppDatabase.getInstance(application).editDao()
        repository = RepositoryRoom(collectionDao, addContentDao, favouriteDao,editDao)
        allContent = repository.allContent
    }

    fun insertContent(addModel: AddModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertContent(addModel)
    }

    fun updateCollectionName(nameCollection:String)= viewModelScope.launch(Dispatchers.IO) {
        repository.updateCollectionName(nameCollection)
    }
    fun insertFavourite(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(favourite)
    }
    fun updateContent(addModel: AddModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateContent(addModel)
    }
    fun deleteContent(addModel: AddModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteContent(addModel)
    }

    fun deleteFavourite(name: String) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteFavouriteByName(name)
    }

}