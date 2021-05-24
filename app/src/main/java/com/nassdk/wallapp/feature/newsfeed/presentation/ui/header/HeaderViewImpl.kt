package com.nassdk.wallapp.feature.newsfeed.presentation.ui.header

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.nassdk.wallapp.databinding.ViewHeaderBinding
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderView.Model

class HeaderViewImpl(
    root: ViewGroup
) : BaseMviView<Model, Event>() {

    private val viewBinding: ViewHeaderBinding = ViewHeaderBinding.inflate(
        LayoutInflater.from(root.context), root, true
    )
}