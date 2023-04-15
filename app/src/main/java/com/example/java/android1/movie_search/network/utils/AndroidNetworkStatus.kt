package com.example.java.android1.movie_search.network.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import androidx.core.content.getSystemService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * Implementation of the interface for receiving data from the network on smartphones with Android OS
 */

class AndroidNetworkStatus(context: Context) : NetworkStatus {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    /**
     * A method for monitoring the state of the network, if there is a network, then the source is
     * returned with true, otherwise the source is returned with false
     */

    override fun networkObserve(): Flow<Boolean> {
        return callbackFlow {
            // The base value for the source
            launch { send(false) }

            val callback = object : ConnectivityManager.NetworkCallback() {

                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch { send(true) }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch { send(false) }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch { send(false) }
                }

            }
            connectivityManager?.registerDefaultNetworkCallback(callback)
            awaitClose {
                connectivityManager?.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }

}