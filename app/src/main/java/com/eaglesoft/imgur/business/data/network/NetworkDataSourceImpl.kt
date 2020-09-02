package com.eaglesoft.imgur.business.data.network

import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.framework.datasource.network.ApiRetrofitService
import com.eaglesoft.imgur.framework.datasource.network.mappers.NetworkMapper

class NetworkDataSourceImpl
constructor(
    private val apiRetrofitService: ApiRetrofitService,
    private val networkMapper: NetworkMapper
) : NetworkDataSource {
    private val TAG = "NetworkDataSourceImpl"
    override suspend fun get(query: String?, page: Int?): List<Data?>? {
        val resultData = apiRetrofitService.get(query, page)
        return if (resultData?.status == 200)
            networkMapper.mapFromEntityList(resultData.data)
        else arrayListOf()
    }
}