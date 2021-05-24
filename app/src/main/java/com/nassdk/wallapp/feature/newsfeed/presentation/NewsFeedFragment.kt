package com.nassdk.wallapp.feature.newsfeed.presentation

import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.nassdk.wallapp.R
import com.nassdk.wallapp.databinding.ScreenNewsFeedBinding
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderViewImpl
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsViewImpl
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.search.SearchViewImpl
import com.nassdk.wallapp.library.coreui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : BaseFragment(R.layout.screen_news_feed) {

    @Inject
    lateinit var store: NewsFeedStore

    private val viewBinding: ScreenNewsFeedBinding by viewBinding()

    override fun prepareUi() {

        bind(this@NewsFeedFragment.lifecycle.asMviLifecycle(), BinderLifecycleMode.START_STOP) {
            store.labels.bindTo { label ->
                when (label) {
                    is NewsFeedStore.Label.Error -> showError(error = label.message)
                }
            }
        }

        HeaderViewImpl(
            root = viewBinding.heeaderContainer
        ).bind(store = store, viewConnections = HeaderViewConnections)

        SearchViewImpl(
            root = viewBinding.searchContainer
        ).bind(store = store, viewConnections = SearchViewConnections)

        PostsViewImpl(
            root = viewBinding.postsContainer
        ).bind(store = store, viewConnections = PostsViewConnections)
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }
}