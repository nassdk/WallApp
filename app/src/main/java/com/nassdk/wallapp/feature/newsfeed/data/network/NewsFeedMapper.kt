package com.nassdk.wallapp.feature.newsfeed.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nassdk.wallapp.feature.newsfeed.data.database.PostEntity
import com.nassdk.wallapp.feature.newsfeed.domain.model.*
import com.nassdk.wallapp.library.coreui.util.convertDate
import java.lang.reflect.Type
import javax.inject.Inject

class NewsFeedMapper @Inject constructor(
    private val gson: Gson
) {

    fun map(model: NewsFeedResponseDataModel) = NewsFeedResponse(
        cursor = model.data.cursor,
        posts = mapPosts(posts = model.data.posts.orEmpty())
    )

    fun mapEntityToPost(entities: List<PostEntity>): List<PostModel> {

        //Не стал кастомные парсеры писать для sealed классов.
        val typeStatus: Type = object : TypeToken<PostStatObjectModel>() {}.type

        return entities.map { entity ->
            entity.run {
                PostModel(
                    id = id,
                    authorName = authorName,
                    createAt = createAt,
                    authorImage = authorImage,
                    type = PostType.Plain,
                    contents = emptyList(),
                    postStats = gson.fromJson(postStats, typeStatus)
                )
            }
        }
    }

    fun mapPostToEntity(posts: List<PostModel>) = posts.map { post ->
        post.run {
            PostEntity(
                id = id,
                authorName = authorName,
                createAt = createAt,
                authorImage = authorImage,
                postType = gson.toJson(type),
                contents = gson.toJson(contents),
                postStats = gson.toJson(postStats)
            )
        }
    }

    private fun mapPosts(posts: List<PostNetModel>) = posts.map { post ->
        post.run {
            PostModel(
                id = id,
                type = mapPostType(type = type),
                contents = mapContents(contents = contents.orEmpty()),
                authorName = author?.name ?: "Пользователь",
                authorImage = author?.photo?.let {
                    it.data?.extraSmall?.url ?: it.data?.small?.url ?: it.data?.original?.url
                }.orEmpty(),
                createAt = convertDate(dateInMilliseconds = createdAt),
                postStats = PostStatObjectModel(
                    likes = stats?.likes?.count ?: 0,
                    views = stats?.views?.count ?: 0,
                    comments = stats?.comments?.count ?: 0,
                )
            )
        }
    }

    private fun mapContents(contents: List<ContentNetModel>) = contents.map { content ->

        content.run {
            ContentModel(
                type = mapDataType(type = type),
                data = mapDataValue(type = type, dataModel = data)
            )
        }
    }

    private fun mapDataType(type: String?) = when (type) {
        DataType.Image.value -> DataType.Image
        DataType.Text.value -> DataType.Text
        else -> null
    }

    private fun mapPostType(type: String?) = when (type) {
        PostType.Video.name -> PostType.Video
        PostType.PlainCover.name -> PostType.PlainCover
        PostType.AudioCover.name -> PostType.AudioCover
        else -> PostType.Plain
    }

    private fun mapDataValue(type: String?, dataModel: PostDataNet?) = when (type) {
        DataType.Text.value -> listOf(dataModel?.value.orEmpty())
        DataType.Image.value -> listOf(
            dataModel?.original?.url ?: dataModel?.small?.url
            ?: dataModel?.extraSmall?.url.orEmpty()
        )
        else -> null
    }
}