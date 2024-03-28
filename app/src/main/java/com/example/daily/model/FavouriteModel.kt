package com.example.daily.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favourite")
data class FavouriteModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameFavourite: String,
    var nameCollection: String,
    var isFavourite : Boolean,
    var day :String)