package com.nassdk.wallapp.feature.newsfeed.presentation.di

import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.nassdk.wallapp.feature.newsfeed.domain.usecase.LoadNewsFeedUseCase
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStoreFactory
import com.nassdk.wallapp.library.coreui.error.UiErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object NewsFeedModule {

    @Provides
    fun provideNewsFeedStore(
        loadNewsFeedUseCase: LoadNewsFeedUseCase,
        errorHandler: UiErrorHandler
    ) = NewsFeedStoreFactory(
        storeFactory = DefaultStoreFactory,
        loadNewsFeedUseCase = loadNewsFeedUseCase,
        errorHandler = errorHandler
    ).create()
}