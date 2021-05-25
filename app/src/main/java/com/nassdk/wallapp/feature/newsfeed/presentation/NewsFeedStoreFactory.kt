package com.nassdk.wallapp.feature.newsfeed.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendBootstrapper
import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.nassdk.wallapp.feature.newsfeed.domain.model.NewsFeedResponse
import com.nassdk.wallapp.feature.newsfeed.domain.usecase.LoadNewsFeedUseCase
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedStore.*
import com.nassdk.wallapp.library.coreimpl.network.connection.NetworkStatusPublisher
import com.nassdk.wallapp.library.coreui.error.UiErrorHandler
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class NewsFeedStoreFactory @Inject constructor(
    private val storeFactory: StoreFactory,
    private val loadNewsFeedUseCase: LoadNewsFeedUseCase,
    private val networkStatusPublisher: NetworkStatusPublisher,
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

    private inner class BootstrapperImpl : SuspendBootstrapper<Action>() {

        override suspend fun bootstrap() {

            dispatch(Action.LoadNewsFeed)

            networkStatusPublisher.getNotifier().collect { isConnected ->
                dispatch(Action.NetworkStatusChanged(isConnected = isConnected))
            }
        }
    }

    private inner class ExecutorImpl : SuspendExecutor<Intent, Action, State, Result, Label>() {

        override suspend fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                Intent.Idle -> Unit
                Intent.LoadMore -> loadNextPage(prevState = getState.invoke())
                Intent.UpdateScreen -> loadNewsFeed()
                is Intent.SortNewsBy -> loadNewsFeed(orderBy = intent.sort)
            }
        }

        override suspend fun executeAction(action: Action, getState: () -> State) {
            when (action) {
                Action.LoadNewsFeed -> loadNewsFeed()
                is Action.NetworkStatusChanged -> {
                    dispatch(Result.NetworkStatusChanged(isConnected = action.isConnected))
                    publish(Label.NetworkStatusChanged(isConnected = action.isConnected))
                }
            }
        }

        private suspend fun loadNewsFeed(orderBy: String? = null) {

            dispatch(Result.Loading)

            try {
                val response = loadNewsFeedUseCase.invoke(orderBy = orderBy)
                dispatch(Result.PostsLoaded(response = response, orderBy = orderBy))
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
                val response = loadNewsFeedUseCase.invoke(
                    after = prevState.cursor,
                    orderBy = prevState.currentSortType
                )
                dispatch(
                    Result.NextPageLoaded(
                        response = response,
                        orderBy = prevState.currentSortType
                    )
                )
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
                cursor = result.response.cursor,
                currentSortType = result.orderBy
            )
            is Result.NextPageLoaded -> {
                copy(
                    nextPage = result.response.posts,
                    cursor = result.response.cursor,
                    currentSortType = result.orderBy
                )
            }
            is Result.NetworkStatusChanged -> copy(hasConnection = result.isConnected)
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
        data class NetworkStatusChanged(val isConnected: Boolean) : Result()
        data class NextPageLoaded(
            val response: NewsFeedResponse,
            val orderBy: String? = null
        ) : Result()

        data class PostsLoaded(
            val response: NewsFeedResponse,
            val orderBy: String? = null
        ) : Result()
    }

    sealed class Action {
        object LoadNewsFeed : Action()
        data class NetworkStatusChanged(val isConnected: Boolean) : Action()
    }
}