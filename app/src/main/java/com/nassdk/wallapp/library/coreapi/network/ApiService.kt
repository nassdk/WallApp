package com.nassdk.wallapp.library.coreapi.network

import com.nassdk.wallapp.feature.newsfeed.data.network.NewsFeedResponseDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("posts")
    suspend fun loadNewsFeed(
        @Query("after") after: String? = null,
        @Query("orderBy") orderBy: String? = null,
    ): NewsFeedResponseDataModel
}