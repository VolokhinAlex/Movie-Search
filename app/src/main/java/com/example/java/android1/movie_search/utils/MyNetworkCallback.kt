package com.example.java.android1.movie_search.utils

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network

/**
 * The callback that returns some kind of message at a certain network event.
 * For example, the phone is connected or disconnected from the Internet
 */

class MyNetworkCallback(private val listener: (String) -> Unit) : NetworkCallback() {

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        listener("The network is available")
    }

    override fun onUnavailable() {
        super.onUnavailable()
        listener("The network is not available")
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        listener("Network lost")
    }

}