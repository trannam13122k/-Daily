package com.example.daily.repository

import android.util.Log
import com.example.daily.ui.fragment.categories.model.ContentModelFireBase
import com.example.daily.ui.fragment.themes.themBackground.background.model.ThemesModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RepositoryFireBase {
    private val db = Firebase.firestore

    fun getAllThemes(callback: (List<ThemesModel>) -> Unit) {
        db.collection("background")
            .get()
            .addOnSuccessListener { result ->
                val themesList = mutableListOf<ThemesModel>()
                for (document in result) {
                    val theme = document.toObject(ThemesModel::class.java)
                    themesList.add(theme)
                }
                callback(themesList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
    fun getThemesByTitle(title :String,callback: (List<ThemesModel>, String) -> Unit) {
        db.collection("background")
            .whereEqualTo("TitleBg", title)
            .get()
            .addOnSuccessListener { result ->
                val themesList = mutableListOf<ThemesModel>()
                for (document in result) {
                    Log.d(TAG, "getThemesByTitle: "+ document.toString())
                    val theme = document.toObject(ThemesModel::class.java)
                    themesList.add(theme)
                }
                callback(themesList,title)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getAllContent(callback: (List<ContentModelFireBase>) -> Unit) {
        db.collection("content")
            .get()
            .addOnSuccessListener { result ->
                val contentList = mutableListOf<ContentModelFireBase>()
                for (document in result) {
                    val content = document.toObject(ContentModelFireBase::class.java)
                    contentList.add(content)
                }
                callback(contentList)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    fun getContentByTitle(titleContent :String,callback: (List<ContentModelFireBase>, String) -> Unit) {
        db.collection("content")
            .whereEqualTo("titleContent", titleContent)
            .get()
            .addOnSuccessListener { result ->
                val themesList = mutableListOf<ContentModelFireBase>()
                for (document in result) {
                    val theme = document.toObject(ContentModelFireBase::class.java)
                    themesList.add(theme)
                }
                callback(themesList,titleContent)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }

    companion object {
        private const val TAG = "BackgroundRepository"
    }
}