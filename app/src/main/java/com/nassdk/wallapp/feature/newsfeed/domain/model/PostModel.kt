package com.nassdk.wallapp.feature.newsfeed.domain.model

data class PostModel(
    val id: String,
    val type: PostType,
    val contents: List<ContentModel>,
    val authorName: String,
    val createAt: String,
    val authorImage: String,
    val postStats: PostStatObjectModel
)