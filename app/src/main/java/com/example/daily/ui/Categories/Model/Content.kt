package com.example.daily.ui.Categories.Model

data class Content(
    val titleContent :String ="",
    val isCheck :String="",
    var listContent: List<String>?=null,
    val icon: Int,
)