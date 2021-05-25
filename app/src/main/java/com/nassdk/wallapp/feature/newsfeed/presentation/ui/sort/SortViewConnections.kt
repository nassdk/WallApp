package com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort

import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.Intent
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Model
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections

object SortViewConnections :
    ViewConnections<NewsFeedStore.State, Intent, Model, Event> {

    override val stateToModel: (NewsFeedStore.State) -> Model = { state ->
        Model(
            loading = state.loading || state.loadingNextPage,
            hasConnection = state.hasConnection
        )
    }

    override val eventToIntent: (Event) -> Intent = { event ->
        when (event) {
            is Event.LoadSortedNews -> Intent.SortNewsBy(sort = event.orderBy)
        }
    }
}