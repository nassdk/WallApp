package com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.nassdk.wallapp.R
import com.nassdk.wallapp.databinding.ViewPostsBinding
import com.nassdk.wallapp.feature.newsfeed.domain.model.PostModel
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Event
import com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts.PostsView.Model
import com.nassdk.wallapp.library.coreui.util.PaginationListener
import com.nassdk.wallapp.library.coreui.util.applyAnimation
import com.nassdk.wallapp.library.coreui.util.isVisible

class PostsViewImpl(
    root: ViewGroup
) : BaseMviView<Model, Event>() {

    private val viewBinding: ViewPostsBinding = ViewPostsBinding.inflate(
        LayoutInflater.from(root.context), root, true
    )

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PostsAdapter()
    }

    init {
        initListing()
    }

    private fun initListing() {

        with(viewBinding) {

            val layoutManager = LinearLayoutManager(root.context, RecyclerView.VERTICAL, false)

            recyclerPosts.layoutManager = layoutManager
            recyclerPosts.adapter = adapter
            recyclerPosts.addItemDecoration(
                DividerItemDecoration(
                    root.context,
                    DividerItemDecoration.VERTICAL
                ).apply {
                    setDrawable(
                        ContextCompat.getDrawable(
                            root.context,
                            R.drawable.divider_space_white_height_12
                        )!!
                    )
                })
            recyclerPosts.addOnScrollListener(
                object : PaginationListener(layoutManager = layoutManager) {
                    override fun loadMoreItems() = dispatch(Event.LoadMore)
                }
            )
        }
    }

    override val renderer: ViewRenderer<Model> = diff {
        diff(get = Model::loading, set = ::renderLoading)
        diff(get = Model::posts, set = ::renderPosts)
        diff(get = Model::nextPage, set = ::renderNextPage)
        diff(get = Model::nextPageLoading, set = ::renderNextPageLoading)
    }

    private fun renderLoading(loading: Boolean) {

        with(viewBinding) {
            loadingView.root.isVisible(visible = loading)
            recyclerPosts.isVisible(visible = !loading)
        }
    }

    private fun renderPosts(posts: List<PostModel>) {
        adapter.setPosts(posts = posts)
        viewBinding.recyclerPosts.applyAnimation()
    }

    private fun renderNextPage(posts: List<PostModel>) {
        adapter.addPosts(posts = posts)
        viewBinding.recyclerPosts.applyAnimation()
    }

    private fun renderNextPageLoading(loading: Boolean) {
        adapter.renderLoadingForNextPage(loading = loading)
    }
}