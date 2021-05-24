package com.nassdk.wallapp.feature.newsfeed.presentation.ui.header

import com.arkivanov.mvikotlin.core.view.MviView
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Model

interface HeaderView : MviView<Model, Event> {

    class Model
    class Event
}