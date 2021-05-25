package com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort

import com.arkivanov.mvikotlin.core.view.MviView
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Model

interface SortView : MviView<Model, Event> {

    data class Model(
        val loading: Boolean,
        val hasConnection: Boolean
    )

    sealed class Event {
        data class LoadSortedNews(val orderBy: String?) : Event()
    }
}