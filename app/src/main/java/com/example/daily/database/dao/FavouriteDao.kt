package com.example.daily.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.daily.model.FavouriteModel

@Dao
interface FavouriteDao {
    @Insert
    fun insert(favourite: FavouriteModel)

    @Query("SELECT * FROM favourite")
    fun getAllFavouriteLiveData(): LiveData<List<FavouriteModel>>

    suspend fun insertIfNotExists(favourite: FavouriteModel) {
        val existingCount = countFavourite(favourite.nameFavourite)

        if (existingCount == 0) {
            insert(favourite)
        }
    }
    @Query("SELECT COUNT(*) FROM favourite WHERE nameFavourite = :name")
    suspend fun countFavourite(name: String): Int

    @Query("DELETE FROM favourite WHERE nameFavourite = :name")
    suspend fun deleteByName(name: String)

    @Delete
    suspend fun deleteFavourite(favourite: FavouriteModel)

    @Query("DELETE FROM favourite WHERE nameFavourite = :name")
    suspend fun deleteFavouriteByName(name: String)

}
