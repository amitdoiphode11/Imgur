package com.eaglesoft.imgur.business.data.cache

import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.models.Images

interface CacheDataSource {

    suspend fun insert(data: Images?): Long?

    suspend fun get(): Images?

    suspend fun getCommentList(id: String?): List<Comments?>?

    suspend fun update(data: Images?): Int?
}