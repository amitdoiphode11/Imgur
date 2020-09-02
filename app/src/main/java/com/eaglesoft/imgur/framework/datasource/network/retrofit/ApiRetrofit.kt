package com.eaglesoft.imgur.framework.datasource.network.retrofit

import com.eaglesoft.imgur.framework.datasource.network.model.BaseDataNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiRetrofit {

    @GET("search/1")
    suspend fun get(
        @Header("Authorization") auth: String? = "Client-ID 137cda6b5008a7c",
        @Query("q") q: String?,
        @Query("page") page: Int?
    ): BaseDataNetworkEntity?
}