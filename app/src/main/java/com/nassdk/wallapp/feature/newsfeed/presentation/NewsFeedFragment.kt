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
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortViewConnections
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.sort.SortViewImpl
import com.nassdk.wallapp.library.coreui.base.BaseFragment
import com.nassdk.wallapp.library.coreui.util.alert
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsFeedFragment : BaseFragment(R.layout.screen_news_feed) {

    @Inject
    lateinit var store: NewsFeedStore

    private val viewBinding: ScreenNewsFeedBinding by viewBinding()

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
            root = viewBinding.heeaderContainer
        ).bind(store = store, viewConnections = HeaderViewConnections)

        SearchViewImpl(
            root = viewBinding.searchContainer
        ).bind(store = store, viewConnections = SearchViewConnections)

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
}