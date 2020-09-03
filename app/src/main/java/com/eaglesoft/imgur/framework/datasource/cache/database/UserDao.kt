package com.eaglesoft.imgur.framework.datasource.cache.database

import androidx.room.*
import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(imagesEntity: ImagesCacheEntity?): Long?

    @Query("SELECT * FROM images")
    suspend fun getList(): List<ImagesCacheEntity?>?

    @Query("SELECT * FROM images limit 1")
    suspend fun get(): ImagesCacheEntity?

    @Query("SELECT comment FROM images where id =:id")
    suspend fun getCommentList(id: String?): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(imagesEntity: ImagesCacheEntity?)

}