package com.nassdk.wallapp.feature.newsfeed.data.network

import com.nassdk.wallapp.feature.newsfeed.domain.model.*
import com.nassdk.wallapp.library.coreui.util.convertDate
import javax.inject.Inject

class NewsFeedMapper @Inject constructor() {

    fun map(model: NewsFeedResponseDataModel) = NewsFeedResponse(
        cursor = model.data.cursor,
        posts = mapPosts(posts = model.data.posts.orEmpty())
    )

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
        DataType.Tag.value -> DataType.Tag
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
        DataType.Tag.value -> dataModel?.values
        DataType.Text.value -> listOf(dataModel?.value.orEmpty())
        DataType.Image.value -> listOf(
            dataModel?.original?.url ?: dataModel?.small?.url
            ?: dataModel?.extraSmall?.url.orEmpty()
        )
        else -> null
    }
}