package com.nassdk.wallapp.feature.newsfeed.presentation.ui.search

import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.Intent
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.State
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchView.Model
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections

object SearchViewConnections :
    ViewConnections<State, Intent, Model, Event> {

    override val stateToModel: (State) -> Model = {
        Model()
    }

    override val eventToIntent: (Event) -> Intent = {
        Intent.Idle
    }
}