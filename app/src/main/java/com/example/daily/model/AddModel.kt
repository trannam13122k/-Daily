package com.example.daily.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "add")
data class AddModel (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameAdd: String,
    val nameCollection :String,
    var isFavourite : Boolean)