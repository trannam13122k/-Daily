package com.example.daily.ui.Setting.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.daily.database.AppDatabase
import com.example.daily.model.AddModel
import com.example.daily.model.CollectionModel
import com.example.daily.repository.CollectionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: CollectionRepository

    val allContent: LiveData<List<AddModel>>

    init {
        val collectionDao = AppDatabase.getInstance(application).collectionDao()
        val addContentDao = AppDatabase.getInstance(application).addContentDao()
        repository = CollectionRepository(collectionDao, addContentDao)
        allContent = repository.allContent
    }

    fun insertContent(addModel: AddModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertContent(addModel)
    }
//    fun updateCollectionName(nameCollection:String)= viewModelScope.launch(Dispatchers.IO) {
//        repository.updateCollectionName(nameCollection)
//    }



}