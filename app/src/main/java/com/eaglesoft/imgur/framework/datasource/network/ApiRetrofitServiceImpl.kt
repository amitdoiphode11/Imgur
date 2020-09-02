package com.eaglesoft.imgur.framework.datasource.network

import android.app.DownloadManager
import com.eaglesoft.imgur.framework.datasource.network.model.BaseDataNetworkEntity
import com.eaglesoft.imgur.framework.datasource.network.retrofit.ApiRetrofit

class ApiRetrofitServiceImpl
constructor(
    private val apiRetrofit: ApiRetrofit
) : ApiRetrofitService {
    private val TAG = "ApiRetrofitServiceImpl"

    override suspend fun get(query: String?, page: Int?): BaseDataNetworkEntity? {
        return apiRetrofit.get(q = query, page = page)
    }
}