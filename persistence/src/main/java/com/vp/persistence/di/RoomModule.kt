package com.vp.persistence.di

import android.app.Application
import dagger.Module
import androidx.room.Room
import com.vp.persistence.dao.FavoriteMovieDao
import com.vp.persistence.database.MovieRoomDatabase
import com.vp.persistence.repository.FavoriteMovieRepository
import dagger.Provides
import javax.inject.Singleton


@Module
class RoomModule(application: Application) {

    private val movieRoomDatabase: MovieRoomDatabase = Room.databaseBuilder(
            application,
            MovieRoomDatabase::class.java,
            "movie-database").build()

    @Singleton
    @Provides
    internal fun providesRoomDatabase(): MovieRoomDatabase {
        return movieRoomDatabase
    }

    @Singleton
    @Provides
    internal fun providesMovieDao(): FavoriteMovieDao {
        return movieRoomDatabase.favoriteMovieDao()
    }

    @Singleton
    @Provides
    internal fun providesMovieRepository(): FavoriteMovieRepository {
        return FavoriteMovieRepository(movieRoomDatabase)
    }
}
