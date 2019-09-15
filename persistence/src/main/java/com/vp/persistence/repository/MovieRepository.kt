package com.vp.persistence.repository

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.vp.persistence.entity.FavoriteMovieEntity
import com.vp.persistence.database.MovieRoomDatabase


class FavoriteMovieRepository(private val movieRoomDatabase: MovieRoomDatabase) {

    fun insert(favoriteMovie: FavoriteMovieEntity) {
        AsyncTask.execute { movieRoomDatabase.favoriteMovieDao().insert(favoriteMovie) }
    }

    fun getAll(): LiveData<List<FavoriteMovieEntity>> = movieRoomDatabase.favoriteMovieDao().getAll()
}
