package com.eaglesoft.imgur.business.data.network

import androidx.paging.PagingSource
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.framework.datasource.network.ApiRetrofitService
import com.eaglesoft.imgur.framework.datasource.network.mappers.NetworkMapper
import retrofit2.HttpException
import java.io.IOException

class NetworkDataSourceImpl
constructor(
    private val apiRetrofitService: ApiRetrofitService,
    private val networkMapper: NetworkMapper
) : NetworkDataSource {
    private val TAG = "NetworkDataSourceImpl"
    private val initialPageIndex: Int = 1
    override suspend fun get(query: String?, page: Int?): List<Data?>? {
        val resultData = apiRetrofitService.get(query, page)
        return if (resultData?.status == 200)
            networkMapper.mapFromEntityList(resultData.data)
        else arrayListOf()
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>?): PagingSource.LoadResult<Int, Data> {
        val position = params?.key ?: initialPageIndex

        return try {
            val response = apiRetrofitService.get("", position)
            PagingSource.LoadResult.Page(
                data = response?.data as List<Data>,
                prevKey = if (position == initialPageIndex) null else position - 1,
                nextKey = if (response.data.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return PagingSource.LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return PagingSource.LoadResult.Error(exception)
        } catch (e: Exception) {
            return PagingSource.LoadResult.Error(e)
        }

    }

}