package com.vp.persistence.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vp.persistence.dao.FavoriteMovieDao
import com.vp.persistence.entity.FavoriteMovieEntity


@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun favoriteMovieDao(): FavoriteMovieDao
}
