package com.nassdk.wallapp.feature.newsfeed.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PostsDao {

    @Query(value = "SELECT * FROM ${PostEntity.TABLE_NAME}")
    suspend fun retrievePosts(): List<PostEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun storePosts(posts: List<PostEntity>)
}