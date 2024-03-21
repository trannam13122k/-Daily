package com.example.daily.ui.fragment.themes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daily.database.dao.EditDao
import com.example.daily.model.EditModel
import com.example.daily.repository.RepositoryFireBase
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel

class ThemesViewModel (): ViewModel() {
    private val repository = RepositoryFireBase()

    private val _themes = MutableLiveData<List<ThemesModel>>()
    val themes: LiveData<List<ThemesModel>> = _themes

    fun fetchAllThemes() {
        repository.getAllThemes { themesList ->
            _themes.postValue(themesList)
        }
    }

    fun getThemesByTitle(title: String, callback: (List<ThemesModel>, String) -> Unit) {
        repository.getThemesByTitle(title) { themesList, title ->
            callback(themesList, title)
        }
    }
    fun getThemesByRanDom(check: Boolean, callback: (List<ThemesModel>, Boolean) -> Unit) {
        repository.getThemesByRanDom(check) { themesList, check ->
            callback(themesList, check)
        }
    }

}

