package com.nassdk.wallapp.feature.newsfeed.domain.model

sealed class DataType(val value: String) {
    object Text : DataType(value = "TEXT")
    object Image : DataType(value = "IMAGE")
    object Tag : DataType(value = "TAGS")
}