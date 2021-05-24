package com.nassdk.wallapp.feature.newsfeed.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse
import com.nassdk.wallapp.feature.newsfeed.domain.usecase.LoadNewsFeedUseCase
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.*
import com.nassdk.wallapp.library.coreui.error.UiErrorHandler
import javax.inject.Inject

class NewsFeedStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val loadNewsFeedUseCase: LoadNewsFeedUseCase,
    private val errorHandler: UiErrorHandler
) {
    fun create(): NewsFeedStore =
        object : NewsFeedStore, Store<Intent, State, Label> by storeFactory.create(
            name = "NewsFeedStoreFactory",
            initialState = State(),
            bootstrapper = BootstrapperImpl(),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl()
        ) {}

    private class BootstrapperImpl : SuspendBootstrapper<Action>() {

        override suspend fun bootstrap() {
            dispatch(Action.LoadNewsFeed)
        }
    }

    private inner class ExecutorImpl : SuspendExecutor<Intent, Action, State, Result, Label>() {

        override suspend fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.LoadMore -> loadNextPage(prevState = getState.invoke())
                is Intent.SortNewsBy -> loadNewsFeed(orderBy = intent.sort)
            }
        }

        override suspend fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.LoadNewsFeed -> loadNewsFeed()
            }
        }

        private suspend fun loadNewsFeed(orderBy: String? = null) {

            dispatch(Result.Loading)

            try {
                val response = loadNewsFeedUseCase.invoke(orderBy = orderBy)
                dispatch(Result.PostsLoaded(response = response))
            } catch (throwable: Exception) {
                errorHandler.proceedError(
                    throwable = throwable,
                    errorListener = { message ->
                        publish(Label.Error(message = message))
                    }
                )
            } finally {
                dispatch(Result.StopLoading)
            }
        }

        private suspend fun loadNextPage(prevState: State) {

            if (prevState.cursor == null || prevState.loading || prevState.loadingNextPage) return

            dispatch(Result.LoadingMore)

            try {
                val response = loadNewsFeedUseCase.invoke(after = prevState.cursor)
                dispatch(Result.NextPageLoaded(response = response))
            } catch (throwable: Exception) {
                errorHandler.proceedError(
                    throwable = throwable,
                    errorListener = { message ->
                        publish(Label.Error(message = message))
                    }
                )
            } finally {
                dispatch(Result.StopLoadingMore)
            }
        }
    }

    private class ReducerImpl : Reducer<State, Result> {
        override fun State.reduce(result: Result) = when (result) {
            is Result.PostsLoaded -> copy(
                posts = result.response.posts,
                cursor = result.response.cursor
            )
            is Result.NextPageLoaded -> {
                copy(
                    nextPage = result.response.posts,
                    cursor = result.response.cursor
                )
            }
            Result.Loading -> copy(loading = true)
            Result.LoadingMore -> copy(loadingNextPage = true)
            Result.StopLoading -> copy(loading = false)
            Result.StopLoadingMore -> copy(loadingNextPage = false)
        }
    }

    sealed class Result {
        object Loading : Result()
        object StopLoading : Result()
        object LoadingMore : Result()
        object StopLoadingMore : Result()
        data class NextPageLoaded(val response: NewsFeedResponse) : Result()
        data class PostsLoaded(val response: NewsFeedResponse) : Result()
    }

    sealed class Action {
        object LoadNewsFeed : Action()
    }
}