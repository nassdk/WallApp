package com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts

import com.arkivanov.mvikotlin.core.view.MviView
import com.nassdk.wallapp.feature.newsfeed.domain.model.PostModel
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Model

interface PostsView : MviView<Model, Event> {

    data class Model(
        val loading: Boolean = false,
        val nextPageLoading: Boolean = false,
        val posts: List<PostModel>,
        val nextPage: List<PostModel>
    )

    sealed class Event {
        object LoadMore : Event()
    }
}