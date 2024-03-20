package com.example.daily.ui.fragment.categories.model

import java.io.Serializable

data class Content(
    val titleContent :String ="",
    val isCheck :String="",
    var listContent: List<String>?=null,
    val icon: Int,
)
