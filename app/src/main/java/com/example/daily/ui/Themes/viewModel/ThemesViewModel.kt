package com.example.daily.ui.Themes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.daily.repository.BackgroundRepository
import com.example.daily.ui.Themes.Model.ThemesModel

class ThemesViewModel (): ViewModel() {
    private val repository = BackgroundRepository()
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
}

