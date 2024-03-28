package com.example.daily.ui.fragment.categories.model

data class ContentModelFireBase(
    val titleContent: String = "",
    val nameContent: String = "",
    val check: Boolean = true,
    var listContent: List<String>? = null,
    val icon: String = "",
)