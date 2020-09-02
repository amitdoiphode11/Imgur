package com.eaglesoft.imgur.framework.datasource.cache.mappers

import com.eaglesoft.imgur.business.domain.models.Comments
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.util.EntityMapper
import com.eaglesoft.imgur.framework.datasource.cache.model.ImagesCacheEntity
import com.google.gson.Gson
import javax.inject.Inject

class CacheMapper
@Inject
constructor() : EntityMapper<ImagesCacheEntity, Images> {
    override fun mapFromEntity(entity: ImagesCacheEntity?): Images? {
        return Images(
            id = entity?.id,
            title = entity?.title,
            description = entity?.description,
            datetime = entity?.datetime,
            type = entity?.type,
            link = entity?.link,
            comment_count = entity?.comment_count,
            comment = commentMapFromEntity(entity?.comment)
        )
    }

    override fun mapToEntity(domainModel: Images?): ImagesCacheEntity? {
        return ImagesCacheEntity(
            //if id is null then it enters as null string
            id = domainModel?.id.toString(),
            title = domainModel?.title,
            description = domainModel?.description,
            datetime = domainModel?.datetime,
            type = domainModel?.type,
            link = domainModel?.link,
            comment_count = domainModel?.comment_count,
            comment = commentMapToEntity(domainModel?.comment)
        )
    }

    fun commentMapToEntity(comments: List<Comments?>?): String? {
        return Gson().toJson(comments)
    }

    fun commentMapFromEntity(comments: String?): List<Comments?>? {
        return listOf(Gson().fromJson(comments, Comments::class.java))
    }

}











