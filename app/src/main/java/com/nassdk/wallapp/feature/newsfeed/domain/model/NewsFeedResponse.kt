package com.nassdk.wallapp.feature.newsfeed.domain.model

data class NewsFeedResponse(
    val posts: List<PostModel>,
    val cursor: String?
)