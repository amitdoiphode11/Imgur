package com.eaglesoft.imgur.business.domain.models

data class Data(
    val id: String? = null,
    val title: String? = null,
    val description: String? = null,
    val datetime: Int? = null,
    val images: List<Images?>? = null,
    var isLoader: Boolean? = null,
    var isRetry: Boolean? = null
    /*,
   val cover: String,
    val cover_width: Int,
    val cover_height: Int,
    val account_url: String,
    val account_id: Int,
    val privacy: String,
    val layout: String,
    val views: Int,
    val link: String,
    val ups: Int,
    val downs: Int,
    val points: Int,
    val score: Int,
    val is_album: Boolean,
    val vote: String,
    val favorite: Boolean,
    val nsfw: Boolean,
    val section: String,
    val comment_count: Int,
    val favorite_count: Int,
    val topic: String,
    val topic_id: Int,
    val images_count: Int,
    val in_gallery: Boolean,
    val is_ad: Boolean,
    val tags: List<String>,
    val ad_type: Int,
    val ad_url: String,
    val in_most_viral: Boolean,
    val include_album_ads: Boolean,
    val ad_config: AdConfigNetworkEntity*/
)




