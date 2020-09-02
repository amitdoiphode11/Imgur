package com.eaglesoft.imgur.business.data.network

import com.eaglesoft.imgur.business.domain.models.Data

interface NetworkDataSource {

    suspend fun get(query: String?,page:Int?): List<Data?>?
}