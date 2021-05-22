package com.nassdk.wallapp.library.coreimpl.common.di

import com.nassdk.wallapp.library.coreapi.common.error.ErrorHandler
import com.nassdk.wallapp.library.coreapi.common.resourcemanager.ResourceManager
import com.nassdk.wallapp.library.coreimpl.common.error.ErrorHandlerImpl
import com.nassdk.wallapp.library.coreimpl.common.resourcemanager.ResourceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CommonModule {

    @Binds
    @Singleton
    abstract fun bindErrorHandler(impl: ErrorHandlerImpl): ErrorHandler

    @Binds
    @Singleton
    abstract fun bindResourceManager(impl: ResourceManagerImpl): ResourceManager
}