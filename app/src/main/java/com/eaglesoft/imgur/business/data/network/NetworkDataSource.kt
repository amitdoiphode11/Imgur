package com.eaglesoft.imgur.business.data.network

import androidx.paging.PagingSource
import com.eaglesoft.imgur.business.domain.models.Data

interface NetworkDataSource {

    suspend fun get(query: String?,page:Int?): List<Data?>?

    suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Data>
}