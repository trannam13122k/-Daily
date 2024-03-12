package com.example.daily.ui.Categories.Model

data class ContentModelFireBase(
    val titleContent :String ="",
    val nameContent :String ="",
    val isKey :Boolean=true,
    var listContent: List<String>?=null,
    val icon: String="",
)