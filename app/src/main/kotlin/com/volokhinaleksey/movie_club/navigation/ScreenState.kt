package com.volokhinaleksey.movie_club.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.volokhinaleksey.movie_club.R

/**
 * The class is needed for easy navigation between screens
 * @param route - Needed to determine the screen for navigation
 * @param name - Needed to display the screen name in the bottom navigation bar. The value as String Res
 * @param icon - Needed to display the screen icon in the bottom navigation bar
 */

sealed class ScreenState(val route: String, @StringRes val name: Int, val icon: ImageVector?) {

    data object SplashScreen : ScreenState(
        route = "splash_screen",
        name = R.string.splash,
        icon = null
    )

    data object HomeScreen : ScreenState(
        route = "home_screen",
        name = R.string.home,
        icon = Icons.Rounded.Home
    )

    data object DetailsScreen :
        ScreenState(route = "details_screen", name = R.string.details_movie, icon = null)

    data object SearchScreen : ScreenState(
        route = "search_screen",
        name = R.string.search,
        icon = Icons.Rounded.Search
    )

    data object FavoriteScreen :
        ScreenState(
            route = "favorite_screen",
            name = R.string.favorite,
            icon = Icons.Rounded.Favorite
        )

    data object CategoryMoviesScreen :
        ScreenState(route = "category_movies_screen", name = R.string.category_movies, icon = null)

    data object ActorDetailsScreen :
        ScreenState(route = "actor_details_screen", name = R.string.actor_details, icon = null)
}