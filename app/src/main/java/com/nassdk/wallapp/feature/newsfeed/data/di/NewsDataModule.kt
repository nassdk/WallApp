package com.nassdk.wallapp.feature.newsfeed.data.di

import com.nassdk.wallapp.feature.newsfeed.data.repository.NewsRepositoryImpl
import com.nassdk.wallapp.feature.newsfeed.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class NewsDataModule {

    @Binds
    abstract fun bindNewsRepository(repositoryImpl: NewsRepositoryImpl): NewsRepository
}