package com.eaglesoft.imgur.framework.datasource.cache

import com.eaglesoft.imgur.framework.datasource.cache.database.UserDao
import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity

class UserDaoServiceImpl
constructor(
    private val userDao: UserDao
) : UserDaoService {

    override suspend fun insert(imagesEntity: ImagesCacheEntity?): Long? {
        return userDao.insert(imagesEntity)
    }

    override suspend fun get(): ImagesCacheEntity? {
        return userDao.get()
    }

    override suspend fun getCommentList(id:String?): String? {
        return userDao.getCommentList(id)
    }

    override suspend fun update(imagesEntity: ImagesCacheEntity?): Int? {
        return userDao.update(imagesEntity)
    }


}