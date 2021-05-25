package com.nassdk.wallapp.feature.newsfeed.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nassdk.wallapp.feature.newsfeed.data.database.PostEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class PostEntity(

    @PrimaryKey @ColumnInfo(name = "postId")
    val id: String,

    @ColumnInfo(name = "postType")
    val postType: String,

    @ColumnInfo(name = "contents")
    val contents: String,

    @ColumnInfo(name = "authorName")
    val authorName: String,

    @ColumnInfo(name = "createAt")
    val createAt: String,

    @ColumnInfo(name = "authorImage")
    val authorImage: String,

    @ColumnInfo(name = "postStats")
    val postStats: String

) {
    companion object {
        const val TABLE_NAME = "NEWS_FEED_POST_ENTITY_NAME"
    }
}
