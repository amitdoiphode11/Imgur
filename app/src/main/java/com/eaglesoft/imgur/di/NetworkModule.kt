package com.eaglesoft.imgur.di

import com.eaglesoft.imgur.business.data.network.NetworkDataSource
import com.eaglesoft.imgur.business.data.network.NetworkDataSourceImpl
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.util.EntityMapper
import com.eaglesoft.imgur.framework.datasource.network.ApiRetrofitService
import com.eaglesoft.imgur.framework.datasource.network.ApiRetrofitServiceImpl
import com.eaglesoft.imgur.framework.datasource.network.retrofit.ApiRetrofit
import com.eaglesoft.imgur.framework.datasource.network.mappers.NetworkMapper
import com.eaglesoft.imgur.framework.datasource.network.model.DataNetworkEntity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideNetworkMapper(): EntityMapper<DataNetworkEntity, Data>{
        return NetworkMapper()
    }

    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        run {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()
        }

    @Singleton
    @Provides
    fun provideRetrofit(gson:  Gson, okHttpClient: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl("https://api.imgur.com/3/gallery/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiRetrofit {
        return retrofit
            .build()
            .create(ApiRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofitService(
        apiRetrofit: ApiRetrofit
    ): ApiRetrofitService{
        return ApiRetrofitServiceImpl(apiRetrofit)
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(
        apiRetrofitService: ApiRetrofitService,
        networkMapper: NetworkMapper
    ): NetworkDataSource{
        return NetworkDataSourceImpl(apiRetrofitService, networkMapper)
    }

}
