package com.example.daily.ui.fragment.categories.model

data class ContentHomeModel(
    val titleContent :String ="",
    val isCheck :String="",
    var listContent: List<String>?=null,
    val icon: Int,
)