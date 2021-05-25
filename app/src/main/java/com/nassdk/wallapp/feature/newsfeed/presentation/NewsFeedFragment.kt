package com.nassdk.wallapp.feature.newsfeed.presentation

import android.widget.Toast
import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.nassdk.wallapp.R
import com.nassdk.wallapp.databinding.ScreenNewsFeedBinding
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.header.HeaderViewImpl
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.newpost.NewPostViewImpl
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsViewImpl
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortViewImpl
import com.nassdk.wallapp.library.coreui.base.BaseFragment
import com.nassdk.wallapp.library.coreui.util.alert
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : BaseFragment(R.layout.screen_news_feed) {

    @Inject lateinit var store: NewsFeedStore

    private var _viewBinding: ScreenNewsFeedBinding? = null
    private val viewBinding get() = _viewBinding!!

    private val alertNetworkLost by lazy(LazyThreadSafetyMode.NONE) {
        alert(
            title = getString(R.string.screen_news_feed_attention_title),
            message = getString(R.string.screen_news_feed_sort_connection_lost_message)
        ) {
            positiveButton(text = getString(R.string.screen_news_feed_ok_button_title))
        }
    }

    private val alertNetworkRetrieved by lazy(LazyThreadSafetyMode.NONE) {
        alert(
            title = getString(R.string.screen_news_feed_attention_title),
            message = getString(R.string.screen_news_feed_sort_connection_retrieved_message)
        ) {
            positiveButton(text = getString(R.string.screen_news_feed_yes_button_title)) {
                store.accept(NewsFeedStore.Intent.UpdateScreen)
            }
            negativeButton(text = getString(R.string.screen_news_feed_cancel_button_title))
        }
    }

    override fun prepareUi() {

        _viewBinding = ScreenNewsFeedBinding.bind(requireView())

        bind(this@NewsFeedFragment.lifecycle.asMviLifecycle(), BinderLifecycleMode.START_STOP) {
            store.labels.bindTo { label ->
                when (label) {
                    is NewsFeedStore.Label.Error -> showError(error = label.message)
                    is NewsFeedStore.Label.NetworkStatusChanged -> onNetworkStatusChanged(
                        isConnected = label.isConnected
                    )
                }
            }
        }

        HeaderViewImpl(
            root = viewBinding.headerContainer
        ).bind(store = store, viewConnections = HeaderViewConnections)

        NewPostViewImpl(
            root = viewBinding.newPostContainer
        ).bind(store = store, viewConnections = NewPostViewConnections)

        SortViewImpl(
            root = viewBinding.sortContainer
        ).bind(store = store, viewConnections = SortViewConnections)

        PostsViewImpl(
            root = viewBinding.postsContainer
        ).bind(store = store, viewConnections = PostsViewConnections)
    }

    private fun showError(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
    }

    private fun onNetworkStatusChanged(isConnected: Boolean) {

        if (isConnected) alertNetworkRetrieved.show()
        else alertNetworkLost.show()
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        store.dispose()
        super.onDestroy()
    }
}