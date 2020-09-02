package com.eaglesoft.imgur.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity

@Database(entities = [ImagesCacheEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun blogDao(): UserDao

    companion object {
        val DATABASE_NAME: String = "images_db"
    }


}