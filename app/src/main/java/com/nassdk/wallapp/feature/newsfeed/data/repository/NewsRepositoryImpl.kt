package com.nassdk.wallapp.feature.newsfeed.data.repository

import com.nassdk.wallapp.feature.newsfeed.data.network.NewsFeedMapper
import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse
import com.nassdk.wallapp.feature.newsfeed.domain.repository.NewsRepository
import com.nassdk.wallapp.library.coreapi.network.ApiService
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: NewsFeedMapper
) : NewsRepository {

    override suspend fun loadNewsFeed(after: String?, orderBy: String?): NewsFeedResponse =
        mapper.map(
            model = api.loadNewsFeed(after = after, orderBy = orderBy)
        )
}