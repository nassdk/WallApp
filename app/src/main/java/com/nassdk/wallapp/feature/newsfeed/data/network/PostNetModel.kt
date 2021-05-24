package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class PostNetModel(
    @SerializedName("id") val id: String,
    @SerializedName("type") val type: String?,
    @SerializedName("contents") val contents: List<ContentNetModel>?,
    @SerializedName("author") val author: AuthorNetModel?,
    @SerializedName("createdAt") val createdAt: Long?,
    @SerializedName("stats") val stats: PostStatsObjectNet?,
)