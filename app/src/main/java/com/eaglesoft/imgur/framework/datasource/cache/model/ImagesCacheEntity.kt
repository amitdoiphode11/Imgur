package com.eaglesoft.imgur.framework.datasource.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "images")
class ImagesCacheEntity(
    @PrimaryKey(autoGenerate = false)
    var id: String,
    val title: String?,
    val description: String?,
    val datetime: Int?,
    val type: String?,
    val link: String?,
    val comment_count: String?,
    val comment: String?
)



