package com.eaglesoft.imgur.framework.datasource.network.model

import com.google.gson.annotations.SerializedName

data class AdConfigNetworkEntity(
	@SerializedName("safeFlags") val safeFlags : List<String>,
	@SerializedName("highRiskFlags") val highRiskFlags : List<String>,
	@SerializedName("unsafeFlags") val unsafeFlags : List<String>,
	@SerializedName("wallUnsafeFlags") val wallUnsafeFlags : List<String>,
	@SerializedName("showsAds") val showsAds : Boolean
)