package com.vp.favorites.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.favorites.service.FavoriteService
import com.vp.list.model.ListItem
import com.vp.list.viewmodel.SearchResult
import com.vp.persistence.entity.FavoriteMovieEntity
import com.vp.persistence.repository.FavoriteMovieRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class FavoriteListViewModel @Inject constructor(private val favoriteService: FavoriteService,
                                                private val favoriteMovieRepository: FavoriteMovieRepository) : ViewModel() {

    private val liveData = MutableLiveData<SearchResult>()
    private val aggregatedItems = mutableListOf<ListItem>()

    fun observeMovies() = liveData

    fun getFavoritesMovies() = favoriteMovieRepository.getAll()

    fun searchFavoriteMoviesById(favoriteMovieIds: List<FavoriteMovieEntity>) {
        for (movie in favoriteMovieIds) {
            favoriteService.getMovie(movie.id).enqueue(object : Callback<ListItem> {
                override fun onResponse(call: Call<ListItem>, response: Response<ListItem>) {

                    val result = response.body()

                    if (result != null) {
                        aggregatedItems.add(result)
                        liveData.value = SearchResult.success(aggregatedItems, aggregatedItems.size)
                    }
                }

                override fun onFailure(call: Call<ListItem>, t: Throwable) {
                    liveData.value = SearchResult.error()
                }
            })
        }
    }
}
