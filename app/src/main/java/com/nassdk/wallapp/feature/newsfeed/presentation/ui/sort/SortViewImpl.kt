package com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.nassdk.wallapp.databinding.ViewSortBinding
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortView.Model
import com.nassdk.wallapp.library.coreui.util.attachOnTabSelectedListener
import com.nassdk.wallapp.library.coreui.util.isVisible
import com.nassdk.wallapp.library.coreui.util.toggleSelection

class SortViewImpl(
    root: ViewGroup
) : BaseMviView<Model, Event>() {

    private val viewBinding: ViewSortBinding = ViewSortBinding.inflate(
        LayoutInflater.from(root.context), root, true
    )

    private val autoTransition by lazy(LazyThreadSafetyMode.NONE) {
        AutoTransition()
    }

    private var lastPosition = 0

    init {
        viewBinding.sortTabLayout.attachOnTabSelectedListener { position ->

            if (position == lastPosition) return@attachOnTabSelectedListener

            lastPosition = position

            dispatch(
                Event.LoadSortedNews(
                    orderBy = when (position) {
                        1 -> "mostCommented"
                        2 -> "createdAt"
                        3 -> "mostPopular"
                        else -> null
                    }
                )
            )
        }
    }

    override val renderer: ViewRenderer<Model> = diff {
        diff(get = Model::loading, set = ::renderLoadingState)
        diff(get = Model::hasConnection, set = ::renderConnectionState)
    }

    private fun renderLoadingState(loading: Boolean) =
        viewBinding.sortTabLayout.toggleSelection(enabled = !loading)

    private fun renderConnectionState(isConnected: Boolean) {

        with(viewBinding) {
            TransitionManager.beginDelayedTransition(root, autoTransition)
            sortTabLayout.isVisible(isConnected)
        }
    }
}