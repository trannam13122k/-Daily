package com.example.daily.ui.fragment.categories.model

data class Content(
    val titleContent :String ="",
    val check :String="",
    var listContent: List<String>?=null,
    val icon: Int,
)
