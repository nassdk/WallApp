package com.nassdk.wallapp.feature.newsfeed.data.repository

import com.nassdk.wallapp.feature.newsfeed.data.database.PostsDao
import com.nassdk.wallapp.feature.newsfeed.data.network.NewsFeedMapper
import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse
import com.nassdk.wallapp.feature.newsfeed.domain.repository.NewsRepository
import com.nassdk.wallapp.library.coreapi.network.ApiService
import com.nassdk.wallapp.library.coreimpl.network.connection.NetworkStatusPublisher
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: NewsFeedMapper,
    private val networkStatus: NetworkStatusPublisher,
    private val postsDao: PostsDao
) : NewsRepository {

    override suspend fun loadNewsFeed(after: String?, orderBy: String?): NewsFeedResponse =
        if (networkStatus.getCurrentStatus() == true)
            mapper.map(model = api.loadNewsFeed(after = after, orderBy = orderBy))
                .also {
                    postsDao.storePosts(posts = mapper.mapPostToEntity(posts = it.posts))
                }
        else NewsFeedResponse(posts = mapper.mapEntityToPost(entities = postsDao.retrievePosts()))
}