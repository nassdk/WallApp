package com.nassdk.wallapp.library.coreimpl.network.error

import com.google.gson.annotations.SerializedName

data class BaseErrorNetModel(
    @SerializedName(value = "error") val error: ErrorNetModel
) {
    data class ErrorNetModel(
        @SerializedName(value = "error_code") val code: Int,
        @SerializedName(value = "error_msg") val message: String
    )
}