package com.eaglesoft.imgur.business.interactors

import android.util.Log
import com.eaglesoft.imgur.business.data.cache.CacheDataSource
import com.eaglesoft.imgur.business.data.network.NetworkDataSource
import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetImages
constructor(
    private val cacheDataSource: CacheDataSource,
    private val networkDataSource: NetworkDataSource
) {
    private val TAG = "GetImages"

    /**
     * Show loading
     * Get images from network
     * Show List<Data>
     */
    suspend fun getOnlineImagesList(query: String?, page: Int?): Flow<DataState<List<Data?>?>> =
        flow {
            try {
                emit(DataState.Loading)
                val networkUsers = networkDataSource.get(query, page)
                if (!networkUsers.isNullOrEmpty())
                    emit(DataState.Success(networkUsers))
                else emit(DataState.Error(Exception("No images found!!!")))
            } catch (e: Exception) {
                Log.e(TAG, "execute: ", e)
                emit(DataState.Error(e))
            }
        }

    fun insertOrUpdateImage(images: Images?, comments: String): Flow<DataState<Int?>> = flow {
        try {
            emit(DataState.Loading)
            cacheDataSource.insertOrUpdate(images, comments)
            emit(DataState.Success(1))
        } catch (e: Exception) {
            emit(DataState.Error(e))
            Log.e(TAG, "updateImage: ", e)
        }
    }

    fun getCommentList(query: String?): Flow<DataState<List<Comments?>?>> = flow {
        try {
            emit(DataState.Loading)
            emit(DataState.Success(cacheDataSource.getCommentList(query)))
        } catch (e: Exception) {
            Log.e(TAG, "getCommentList: ", e)
            emit(DataState.Error(e))
        }
    }

}
















