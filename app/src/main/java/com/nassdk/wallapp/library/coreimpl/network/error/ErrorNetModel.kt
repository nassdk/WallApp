package com.nassdk.wallapp.library.coreimpl.network.error

import com.google.gson.annotations.SerializedName

data class BaseErrorNetModel(
    @SerializedName(value = "errors") val errors: List<ErrorNetModel>
) {
    data class ErrorNetModel(
        @SerializedName(value = "code") val code: String,
        @SerializedName(value = "message") val message: String
    )
}