package com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost

import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.Intent
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.State
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Model
import com.nassdk.wallapp.library.coreui.mvi.ViewConnections

object NewPostViewConnections :
    ViewConnections<State, Intent, Model, Event> {

    override val stateToModel: (State) -> Model = {
        Model()
    }

    override val eventToIntent: (Event) -> Intent = {
        Intent.Idle
    }
}