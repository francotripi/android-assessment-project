package com.vp.favorites.di

import com.vp.favorites.service.FavoriteService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class FavoriteListNetworkModule {

    @Provides
    internal fun providesSearchService(retrofit: Retrofit): FavoriteService {
        return retrofit.create(FavoriteService::class.java)
    }
}
