package com.eaglesoft.imgur.framework.datasource.cache

import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity

interface UserDaoService {

    suspend fun insert(imagesEntity: ImagesCacheEntity?): Long?

    suspend fun get(): ImagesCacheEntity?

    suspend fun getCommentList(id: String?): String?

    suspend fun update(imagesEntity: ImagesCacheEntity?): Int?
}