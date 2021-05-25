package com.nassdk.wallapp.library.coreimpl.database.di

import android.content.Context
import androidx.room.Room
import com.nassdk.wallapp.library.coreimpl.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        AppDataBase::class.java,
        "WallAppDatabase"
    ).build()
}