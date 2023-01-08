package com.example.java.android1.movie_search.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The class is needed for easy navigation between screens
 * @param route - Needed to determine the screen for navigation
 * @param name - Needed to display the screen name in the bottom navigation bar
 * @param icon - Needed to display the screen icon in the bottom navigation bar
 */

sealed class ScreenState(val route: String, val name: String, val icon: ImageVector?) {
    object HomeScreen : ScreenState(route = "home_screen", "Home", Icons.Rounded.Home)
    object DetailsScreen : ScreenState(route = "details_screen", "", null)
    object SearchScreen : ScreenState(route = "search_screen", "Search", Icons.Rounded.Search)
    object FavoriteScreen : ScreenState(route = "favorite_screen", "Favorite", Icons.Rounded.Favorite)
    object CategoryMoviesScreen : ScreenState(route = "category_movies_screen", "", null)
    object ActorDetailsScreen : ScreenState(route = "actor_details_screen", "", null)
}