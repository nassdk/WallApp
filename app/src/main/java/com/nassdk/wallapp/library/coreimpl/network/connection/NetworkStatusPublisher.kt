package com.nassdk.wallapp.library.coreimpl.network.connection

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class NetworkStatusPublisher @Inject constructor(
    private val networkChecking: NetworkChecking
) {
    private val publisher = MutableSharedFlow<Boolean>()

    private var currentStatus: Boolean? = null

    fun getNotifier(): SharedFlow<Boolean> = publisher.asSharedFlow()

    fun getCurrentStatus(): Boolean? {
        return currentStatus
    }

    suspend fun onNetworkStateChanged(connected: Boolean) {

        val isConnected = networkChecking.hasConnection()
        if (isConnected == currentStatus) return

        currentStatus = isConnected
        publisher.emit(isConnected)
    }
}