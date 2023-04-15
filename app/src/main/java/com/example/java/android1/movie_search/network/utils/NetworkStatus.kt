package com.example.java.android1.movie_search.network.utils

import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting information about the network
 * The interface returns an object that can be monitored
 * when subscribing to network availability.
 */

interface NetworkStatus {

    /**
     * A method for monitoring the state of the network, if there is a network, then the source is
     * returned with true, otherwise the source is returned with false
     */

    fun networkObserve(): Flow<Boolean>

}