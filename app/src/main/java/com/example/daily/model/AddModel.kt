package com.example.daily.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "add")
data class AddModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nameAdd: String,
    var nameCollection: String,
    var isFavourite: Boolean,
    var day: String
) : Parcelable