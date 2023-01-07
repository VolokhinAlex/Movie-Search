package com.example.java.android1.movie_search.view.navigation

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.*

/**
 * Extensions for the NavController class, for convenient data transfer between screens using bundle
 * Thanks to this, any parcelable objects can be transferred between screens
 */

fun NavController.navigate(
    route: String,
    args: Bundle,
    navOptions: NavOptions? = null,
    navigatorExtras: Navigator.Extras? = null
) {
    val routeLink = NavDeepLinkRequest
        .Builder
        .fromUri(NavDestination.createRoute(route).toUri())
        .build()

    val deepLinkMatch = graph.matchDeepLink(routeLink)
    if (deepLinkMatch != null) {
        val destination = deepLinkMatch.destination
        navigate(destination.id, args, navOptions, navigatorExtras)
    } else {
        navigate(route, navOptions, navigatorExtras)
    }
}