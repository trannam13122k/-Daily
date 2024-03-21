package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.daily.model.EditModel

@Dao
interface EditDao {

    @Insert
    fun insert(editModel: EditModel)

    @Update
    fun update(editModel: EditModel)



    @Query("SELECT * FROM edit")
    fun getAllLiveData(): LiveData<List<EditModel>>

    @Query("SELECT * FROM edit")
    fun getAllData(): List<EditModel>
}