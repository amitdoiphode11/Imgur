package com.eaglesoft.imgur.di

import android.content.Context
import androidx.room.Room
import com.eaglesoft.imgur.business.data.cache.CacheDataSource
import com.eaglesoft.imgur.business.data.cache.CacheDataSourceImpl
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.util.EntityMapper
import com.eaglesoft.imgur.framework.datasource.cache.UserDaoService
import com.eaglesoft.imgur.framework.datasource.cache.UserDaoServiceImpl
import com.eaglesoft.imgur.framework.datasource.cache.database.AppDatabase
import com.eaglesoft.imgur.framework.datasource.cache.database.UserDao
import com.eaglesoft.imgur.framework.datasource.cache.mappers.CacheMapper
import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideCacheMapper(): EntityMapper<ImagesCacheEntity, Images> {
        return CacheMapper()
    }

    @Singleton
    @Provides
    fun provideAppDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideUserDAO(appDatabase: AppDatabase): UserDao {
        return appDatabase.blogDao()
    }

    @Singleton
    @Provides
    fun provideUserDaoService(
        userDao: UserDao
    ): UserDaoService {
        return UserDaoServiceImpl(userDao)
    }

    @Singleton
    @Provides
    fun provideCacheDataSource(
        userDaoService: UserDaoService,
        cacheMapper: CacheMapper
    ): CacheDataSource {
        return CacheDataSourceImpl(userDaoService, cacheMapper)
    }
}