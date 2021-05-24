package com.nassdk.wallapp.feature.newsfeed.presentation.ui.search

import com.arkivanov.mvikotlin.core.view.MviView
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchView.Model

interface SearchView : MviView<Model, Event> {

    class Model
    class Event
}