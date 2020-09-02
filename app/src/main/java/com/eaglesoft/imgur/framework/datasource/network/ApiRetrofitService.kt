package com.eaglesoft.imgur.framework.datasource.network

import com.eaglesoft.imgur.framework.datasource.network.model.BaseDataNetworkEntity

interface ApiRetrofitService {

    suspend fun get(query: String?, page: Int?): BaseDataNetworkEntity?
}