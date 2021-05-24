package com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts

import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.Intent
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.State
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Model
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections

object PostsViewConnections : ViewConnections<State, Intent, Model, Event> {

    override val stateToModel: (State) -> Model = { state ->
        Model(
            posts = state.posts,
            loading = state.loading,
            nextPageLoading = state.loadingNextPage,
            nextPage = state.nextPage
        )
    }


    override val eventToIntent: (Event) -> Intent = { event ->
        when (event) {
            Event.LoadMore -> Intent.LoadMore
        }
    }
}