package com.nassdk.wallapp.feature.newsfeed.domain.usecase

import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse
import com.nassdk.wallapp.feature.newsfeed.domain.repository.NewsRepository
import javax.inject.Inject

class LoadNewsFeedUseCase @Inject constructor(
    private val repository: NewsRepository
) {
    suspend fun invoke(after: String? = null, orderBy: String? = null): NewsFeedResponse =
        repository.loadNewsFeed(
            after = after,
            orderBy = orderBy
        )
}