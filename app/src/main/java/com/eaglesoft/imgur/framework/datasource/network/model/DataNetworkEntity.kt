package com.eaglesoft.imgur.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class DataNetworkEntity(
    @SerializedName("id") val id: String? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("datetime") val datetime: Int? = null,
    @SerializedName("images") val images: List<ImageNetworkEntity?>? = null
    /*,
    @SerializedName("cover") val cover: String,
    @SerializedName("cover_width") val cover_width: Int,
    @SerializedName("cover_height") val cover_height: Int,
    @SerializedName("account_url") val account_url: String,
    @SerializedName("account_id") val account_id: Int,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("layout") val layout: String,
    @SerializedName("views") val views: Int,
    @SerializedName("link") val link: String,
    @SerializedName("ups") val ups: Int,
    @SerializedName("downs") val downs: Int,
    @SerializedName("points") val points: Int,
    @SerializedName("score") val score: Int,
    @SerializedName("is_album") val is_album: Boolean,
    @SerializedName("vote") val vote: String,
    @SerializedName("favorite") val favorite: Boolean,
    @SerializedName("nsfw") val nsfw: Boolean,
    @SerializedName("section") val section: String,
    @SerializedName("comment_count") val comment_count: Int,
    @SerializedName("favorite_count") val favorite_count: Int,
    @SerializedName("topic") val topic: String,
    @SerializedName("topic_id") val topic_id: Int,
    @SerializedName("images_count") val images_count: Int,
    @SerializedName("in_gallery") val in_gallery: Boolean,
    @SerializedName("is_ad") val is_ad: Boolean,
    @SerializedName("tags") val tags: List<String>,
    @SerializedName("ad_type") val ad_type: Int,
    @SerializedName("ad_url") val ad_url: String,
    @SerializedName("in_most_viral") val in_most_viral: Boolean,
    @SerializedName("include_album_ads") val include_album_ads: Boolean,
    @SerializedName("ad_config") val ad_config: AdConfigNetworkEntity*/
)



