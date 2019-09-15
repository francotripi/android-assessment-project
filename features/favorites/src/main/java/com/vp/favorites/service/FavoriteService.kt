package com.vp.favorites.service

import com.vp.list.model.ListItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FavoriteService {
    @GET("/")
    fun getMovie(@Query("i") imdbID: String): Call<ListItem>
}
