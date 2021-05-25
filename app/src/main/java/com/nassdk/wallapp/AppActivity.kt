package com.nassdk.wallapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.nassdk.wallapp.feature.newsfeed.presentation.NewsFeedFragment
import com.nassdk.wallapp.library.coreimpl.network.connection.NetworkStatusPublisher
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppActivity : AppCompatActivity() {

    @Inject
    lateinit var networkStatusPublisher: NetworkStatusPublisher

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleNetworkStatusChanges()

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(
                R.id.app_container,
                NewsFeedFragment(),
                "NewsFeedFragment"
            )
            transaction.commit()
        }
    }

    private fun handleNetworkStatusChanges() {

        val reqBuilder = NetworkRequest.Builder()
        reqBuilder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        reqBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        val netCallBack = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                lifecycleScope.launchWhenStarted {
                    networkStatusPublisher.onNetworkStateChanged(connected = true)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                lifecycleScope.launchWhenStarted {
                    networkStatusPublisher.onNetworkStateChanged(connected = false)
                }
            }
        }
        val conManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        conManager.registerNetworkCallback(reqBuilder.build(), netCallBack)
    }
}