package com.example.daily.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "collection")
data class CollectionModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameCollection: String,
)
