package com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost

import android.view.LayoutInflater
import android.view.ViewGroup
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.nassdk.wallapp.databinding.ViewSearchBinding
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostView.Model

class NewPostViewImpl(
    root: ViewGroup
) : BaseMviView<Model, Event>() {

    private val viewBinding: ViewSearchBinding = ViewSearchBinding.inflate(
        LayoutInflater.from(root.context), root, true
    )
}