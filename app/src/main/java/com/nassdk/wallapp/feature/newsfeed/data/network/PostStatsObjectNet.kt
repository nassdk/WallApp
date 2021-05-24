package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class PostStatsObjectNet(
    @SerializedName("likes") val likes: StatNetModel?,
    @SerializedName("views") val views: StatNetModel?,
    @SerializedName("comments") val comments: StatNetModel?
)