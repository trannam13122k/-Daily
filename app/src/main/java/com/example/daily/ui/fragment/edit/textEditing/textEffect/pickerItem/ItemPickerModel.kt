package com.example.daily.ui.fragment.edit.textEditing.textEffect.pickerItem

class ItemPickerModel(
    val rs: String,
    val font: Int,
    val icon: Int,
    val text: String,
    val size: Int,
    val alignment: Int,
    val alignmentTop: Int,
    var textTransform:String

) {
    enum class TextTransform {
        NONE,
        UPPERCASE,
        LOWERCASE,
        CAPITALIZE
    }
}