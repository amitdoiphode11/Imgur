package com.eaglesoft.imgur.di

import com.eaglesoft.imgur.business.data.cache.CacheDataSource
import com.eaglesoft.imgur.business.data.network.NetworkDataSource
import com.eaglesoft.imgur.business.interactors.GetImages
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object IntegratorsModule {

    @Singleton
    @Provides
    fun provideGetUsers(
        cacheDataSource: CacheDataSource,
        networkDataSource: NetworkDataSource
    ): GetImages{
        return GetImages(cacheDataSource, networkDataSource)
    }
}