package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class AuthorNetModel(
    @SerializedName("name") val name: String?,
    @SerializedName("photo") val photo: ContentNetModel?
)