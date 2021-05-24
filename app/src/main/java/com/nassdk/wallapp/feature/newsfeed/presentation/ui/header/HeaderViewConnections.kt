package com.nassdk.wallapp.feature.newsfeed.presentation.ui.header

import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.Intent
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.State
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Model
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections

object HeaderViewConnections :
    ViewConnections<State, Intent, Model, Event> {

    override val stateToModel: (State) -> Model = {
        Model()
    }

    override val eventToIntent: (Event) -> Intent = {
        Intent.Idle
    }
}