package com.example.java.android1.movie_search.view.compose.navigation

sealed class ScreenState(val route: String) {

    object HomeScreen : ScreenState(route = "home_screen")
    object DetailsScreen : ScreenState(route = "details_screen")
    object SearchScreen : ScreenState(route = "search_screen")
    object FavoriteScreen : ScreenState(route = "favorite_screen")
    object ProfileScreen : ScreenState(route = "profile_screen")

}