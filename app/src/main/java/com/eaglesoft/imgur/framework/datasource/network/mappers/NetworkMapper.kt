package com.eaglesoft.imgur.framework.datasource.network.mappers

import com.eaglesoft.imgur.business.domain.models.Data
import com.eaglesoft.imgur.business.domain.models.Images
import com.eaglesoft.imgur.business.domain.util.EntityMapper
import com.eaglesoft.imgur.framework.datasource.network.model.DataNetworkEntity
import com.eaglesoft.imgur.framework.datasource.network.model.ImageNetworkEntity
import javax.inject.Inject

class NetworkMapper
@Inject
constructor() : EntityMapper<DataNetworkEntity, Data> {

    override fun mapFromEntity(entity: DataNetworkEntity?): Data? {
        return Data(
            id = entity?.id,
            title = entity?.title,
            description = entity?.description,
            datetime = entity?.datetime,
            images = imagesMapFromEntityList(entity?.images)
        )
    }

    override fun mapToEntity(domainModel: Data?): DataNetworkEntity? {
        return DataNetworkEntity(
            id = domainModel?.id,
            title = domainModel?.title,
            description = domainModel?.description,
            datetime = domainModel?.datetime,
            images = imagesMapToEntityList(domainModel?.images)
        )
    }


    fun mapFromEntityList(entities: List<DataNetworkEntity?>?): List<Data?>? {
        return entities?.map { mapFromEntity(it) }
    }

    private fun imagesMapFromEntityList(entities: List<ImageNetworkEntity?>?): List<Images?>? {
        return entities?.map { imageMapFromEntity(it) }
    }

    private fun imageMapFromEntity(imageEntity: ImageNetworkEntity?): Images? {
        return Images(
            id = imageEntity?.id,
            title = imageEntity?.title,
            description = imageEntity?.description,
            datetime = imageEntity?.datetime,
            type = imageEntity?.type,
            link = imageEntity?.link,
            comment_count = imageEntity?.comment_count,
            comment = arrayListOf()
        )
    }

    private fun imagesMapToEntityList(entities: List<Images?>?): List<ImageNetworkEntity?>? {
        return entities?.map { imageMapToEntity(it) }
    }

    private fun imageMapToEntity(imageEntity: Images?): ImageNetworkEntity? {
        return ImageNetworkEntity(
            id = imageEntity?.id,
            title = imageEntity?.title,
            description = imageEntity?.description,
            datetime = imageEntity?.datetime,
            type = imageEntity?.type,
            link = imageEntity?.link,
            comment_count = imageEntity?.comment_count
        )
    }


    companion object {
        private const val TAG = "NetworkMapper"
    }


}