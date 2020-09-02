package com.eaglesoft.imgur.framework.datasource.network.model

data class BaseDataNetworkEntity(
    val data: List<DataNetworkEntity?>? = null,
    val success: Boolean,
    val status: Int
)