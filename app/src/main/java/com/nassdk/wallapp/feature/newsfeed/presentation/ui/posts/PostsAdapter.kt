package com.nassdk.wallapp.feature.newsfeed.presentation.ui.posts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.nassdk.wallapp.R
import com.nassdk.wallapp.databinding.*
import com.nassdk.wallapp.feature.newsfeed.domain.model.DataType
import com.nassdk.wallapp.feature.newsfeed.domain.model.PostModel
import com.nassdk.wallapp.feature.newsfeed.domain.model.PostType
import com.nassdk.wallapp.library.coreui.util.isVisible
import java.util.*

class PostsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val posts by lazy(LazyThreadSafetyMode.NONE) {
        arrayListOf<PostModel?>()
    }

    fun setPosts(posts: List<PostModel>) {

        this.posts.run {
            clear()
            addAll(posts)
        }
    }

    fun addPosts(posts: List<PostModel>) {

        this.posts.run {
            addAll(posts)
        }
    }

    fun clearData() = posts.clear()

    fun renderLoadingForNextPage(loading: Boolean) {

        when {
            loading && posts.contains(null) -> return
            loading -> {
                posts.add(null)
                notifyItemInserted(posts.count() - 1)
            }
            !loading && posts.contains(null) -> {
                posts.removeAll(Collections.singleton(null))
                notifyItemRemoved(posts.count() - 1)
            }
        }
    }

    override fun getItemViewType(position: Int) = posts[position]?.type?.index ?: PROGRESS

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        PostType.Plain.index -> PlainViewHolder(
            itemBinding = ItemPlainPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        PostType.PlainCover.index -> PlainCoverViewHolder(
            itemBinding = ItemPlainCoverPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        PostType.AudioCover.index -> AudioCoverViewHolder(
            itemBinding = ItemAudioCoverPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        PostType.Video.index -> VideoViewHolder(
            itemBinding = ItemVideoPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
        else -> ProgressViewHolder(
            itemBinding = ItemProgressBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    // Force unwrapp - Да, но элемент никогда не будет нуллом
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PlainViewHolder -> holder.bind(post = posts[position]!!)
            is PlainCoverViewHolder -> holder.bind(post = posts[position]!!)
            is AudioCoverViewHolder -> holder.bind(post = posts[position]!!)
            is VideoViewHolder -> holder.bind(post = posts[position]!!)
            is ProgressViewHolder -> holder.bind()
        }
    }

    override fun getItemCount() = posts.count()

    private inner class PlainViewHolder(
        private val itemBinding: ItemPlainPostBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(post: PostModel) {

            val postUrl =
                post.contents.firstOrNull { it.type is DataType.Image }?.data?.firstOrNull()

            val postText =
                post.contents.firstOrNull { it.type is DataType.Text }?.data?.firstOrNull()
                    .orEmpty()

            with(itemBinding) {

                name.text = post.authorName
                likes.text = post.postStats.likes.toString()
                comments.text = post.postStats.comments.toString()
                date.text = post.createAt

                text.setText(
                    text = postText,
                    type = TextView.BufferType.NORMAL
                )

                postImage.isVisible(postUrl != null)

                postUrl.let { url ->
                    postImage.load(url) {
                        transformations(
                            RoundedCornersTransformation(
                                radius = root.context.resources.getDimension(
                                    R.dimen.corner_radius_6
                                )
                            )
                        )
                    }
                }

                avatar.load(uri = post.authorImage) {
                    transformations(CircleCropTransformation())
                    placeholder(R.drawable.ic_avatar_placeholder)
                    error(R.drawable.ic_avatar_placeholder)
                }
            }
        }
    }

    private inner class PlainCoverViewHolder(
        private val itemBinding: ItemPlainCoverPostBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(post: PostModel) {

            val postUrl =
                post.contents.firstOrNull { it.type is DataType.Image }?.data?.firstOrNull()

            val postText =
                post.contents.firstOrNull { it.type is DataType.Text }?.data?.firstOrNull()
                    .orEmpty()

            with(itemBinding) {

                date.text = post.createAt
                name.text = post.authorName
                postDescription.text = postText
                views.text = post.postStats.views.toString()
                likes.text = post.postStats.likes.toString()
                comments.text = post.postStats.comments.toString()

                coverImage.isVisible(postUrl != null)

                postUrl.let { url ->
                    coverImage.load(url) {
                        transformations(
                            RoundedCornersTransformation(
                                radius = root.context.resources.getDimension(
                                    R.dimen.corner_radius_6
                                )
                            )
                        )
                    }
                }

                avatar.load(uri = post.authorImage) {
                    placeholder(R.drawable.ic_avatar_placeholder)
                    error(R.drawable.ic_avatar_placeholder)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    private inner class AudioCoverViewHolder(
        private val itemBinding: ItemAudioCoverPostBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(post: PostModel) {}
    }

    private inner class VideoViewHolder(
        private val itemBinding: ItemVideoPostBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(post: PostModel) {}
    }

    private inner class ProgressViewHolder(
        private val itemBinding: ItemProgressBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind() {}
    }

    companion object {
        private const val PROGRESS = 0
    }
}