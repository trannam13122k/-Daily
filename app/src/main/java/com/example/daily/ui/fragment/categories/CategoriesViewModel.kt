package com.example.daily.ui.fragment.categories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daily.repository.RepositoryFireBase
import com.example.daily.ui.fragment.categories.model.ContentModelFireBase


class CategoriesViewModel(): ViewModel() {
    private val repository = RepositoryFireBase()
    private val _themes = MutableLiveData<List<ContentModelFireBase>>()
    val themes: LiveData<List<ContentModelFireBase>> = _themes

    fun fetchAllThemes() {
        repository.getAllContent { themesList ->
            _themes.postValue(themesList)
        }
    }

    fun getContentByTitle(title: String, callback: (List<ContentModelFireBase>, String) -> Unit) {
        repository.getContentByTitle(title){ themesList, title ->
            callback(themesList, title)
        }
    }
}