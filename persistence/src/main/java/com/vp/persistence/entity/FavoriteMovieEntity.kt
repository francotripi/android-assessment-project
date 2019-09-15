package com.vp.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FavoriteMovieEntity(@PrimaryKey val id: String)
