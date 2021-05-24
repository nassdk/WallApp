package com.nassdk.wallapp.feature.newsfeed.presentation

import com.arkivanov.mvikotlin.core.store.Store
import com.nassdk.wallapp.feature.newsfeed.domain.model.PostModel
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.*

interface NewsFeedStore : Store<Intent, State, Label> {

    sealed class Intent {
        object LoadMore : Intent()
        data class SortNewsBy(val sort: String) : Intent()
    }

    data class State(
        var posts: List<PostModel> = emptyList(),
        var loading: Boolean = false,
        var nextPage: List<PostModel> = emptyList(),
        var loadingNextPage: Boolean = false,
        var cursor: String? = ""
    )

    sealed class Label {
        data class Error(val message: String) : Label()
    }
}