package com.vp.persistence.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vp.persistence.entity.FavoriteMovieEntity


@Dao
interface FavoriteMovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteMovie: FavoriteMovieEntity)

    @Query("SELECT * FROM favoriteMovieEntity")
    fun getAll(): LiveData<List<FavoriteMovieEntity>>
}
