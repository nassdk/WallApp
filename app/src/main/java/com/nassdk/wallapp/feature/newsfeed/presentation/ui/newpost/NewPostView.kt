package com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost

import com.arkivanov.mvikotlin.core.view.MviView
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Model

interface NewPostView : MviView<Model, Event> {

    class Model
    class Event
}