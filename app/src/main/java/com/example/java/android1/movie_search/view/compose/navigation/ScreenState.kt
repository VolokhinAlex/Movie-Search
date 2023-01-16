package com.example.java.android1.movie_search.view.compose.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * The class is needed for easy navigation between screens
 */

sealed class ScreenState(val route: String, val name: String, val icon: ImageVector?) {

    object HomeScreen : ScreenState(route = "home_screen", "Home", Icons.Rounded.Home)
    object DetailsScreen : ScreenState(route = "details_screen", "", null)
    object SearchScreen : ScreenState(route = "search_screen", "Search", Icons.Rounded.Search)
    object FavoriteScreen : ScreenState(route = "favorite_screen", "Favorite", Icons.Rounded.Favorite)
    object CategoryMoviesScreen : ScreenState(route = "category_movies_screen", "", null)
}