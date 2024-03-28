package com.example.daily.ui.fragment.themes.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.AddModel
import com.example.daily.model.EditModel
import com.example.daily.model.FavouriteModel
import com.example.daily.repository.RepositoryRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RepositoryRoom

    val allEdit: LiveData<List<EditModel>>

    val allFavourite: LiveData<List<FavouriteModel>>

    val allAdd: LiveData<List<AddModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        val favouriteDao = AppDatabase.getInstance(application).favouriteDao()
        val editDao = AppDatabase.getInstance(application).editDao()
        repository = RepositoryRoom(collectionDao, addContentDao, favouriteDao, editDao)
        allEdit = repository.allEdit
        allFavourite = repository.allFavourite
        allAdd = repository.allAddContent
    }

    fun getEdit(): EditModel? = repository.edit()

    fun insertEdit(editModel: EditModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertEdit(editModel)
    }

    fun updateEdit(editModel: EditModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateEdit(editModel)
    }

    fun insert(favourite: FavouriteModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertFavourite(favourite)
    }


}