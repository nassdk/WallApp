package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class NewsFeedResponseDataModel(
    @SerializedName("data") val data: NewsFeedResponseNet,
) {
    data class NewsFeedResponseNet(
        @SerializedName("items") val posts: List<PostNetModel>?,
        @SerializedName("cursor") val cursor: String?
    )
}