package com.nassdk.wallapp.library.coreimpl.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nassdk.wallapp.feature.newsfeed.data.database.PostEntity
import com.nassdk.wallapp.feature.newsfeed.data.database.PostsDao

@Database(
    entities = [PostEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
}