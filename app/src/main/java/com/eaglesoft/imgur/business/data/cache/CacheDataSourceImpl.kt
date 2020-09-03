package com.eaglesoft.imgur.business.data.cache

import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.framework.datasource.cache.UserDaoService
import com.eaglesoft.imgur.framework.datasource.cache.mappers.CacheMapper

class CacheDataSourceImpl
constructor(
    private val userDaoService: UserDaoService,
    private val cacheMapper: CacheMapper
) : CacheDataSource {

    override suspend fun insert(data: Images?): Long? {
        return userDaoService.insert(cacheMapper.mapToEntity(data))
    }


    override suspend fun get(): Images? {
        return cacheMapper.mapFromEntity(userDaoService.get())
    }


    override suspend fun getCommentList(id: String?): List<Comments?>? {
        return cacheMapper.commentMapFromEntity(userDaoService.getCommentList(id))
    }

    override suspend fun insertOrUpdate(data: Images?, comments: String?) {
        val tempComments: MutableList<Comments> = data?.comment as MutableList<Comments>
        tempComments.add(Comments(comments.toString()))
        data.comment = tempComments
        return userDaoService.insertOrUpdate(cacheMapper.mapToEntity(data))
    }

}
