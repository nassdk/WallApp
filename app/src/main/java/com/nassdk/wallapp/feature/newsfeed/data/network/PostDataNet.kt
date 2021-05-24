package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.annotations.SerializedName

data class PostDataNet(
    @SerializedName("value") val value: String?,
    @SerializedName("values") val values: List<String>?,
    @SerializedName("extraSmall") val extraSmall: ImageNetModel?,
    @SerializedName("small") val small: ImageNetModel?,
    @SerializedName("original") val original: ImageNetModel?,
) {
    data class ImageNetModel(
        @SerializedName("url") val url: String?
    )
}