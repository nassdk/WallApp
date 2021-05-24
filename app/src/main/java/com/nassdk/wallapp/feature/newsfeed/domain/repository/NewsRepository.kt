package com.nassdk.wallapp.feature.newsfeed.domain.repository

import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse

interface NewsRepository {
    suspend fun loadNewsFeed(after: String? = null, orderBy: String? = null): NewsFeedResponse
}