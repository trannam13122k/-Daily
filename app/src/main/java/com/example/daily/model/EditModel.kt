package com.example.daily.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "edit")
data class EditModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var imageBg: String,
    var imageColor :Int,
    var textColor : Int,
    var font :Int,
    var size :Int,
    var alignment :Int,
    var textTransform:String
)
