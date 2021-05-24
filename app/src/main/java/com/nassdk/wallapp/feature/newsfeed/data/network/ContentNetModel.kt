package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class ContentNetModel(
    @SerializedName("data") val data: PostDataNet?,
    @SerializedName("type") val type: String?,
    @SerializedName("values") val values: List<String>?
)