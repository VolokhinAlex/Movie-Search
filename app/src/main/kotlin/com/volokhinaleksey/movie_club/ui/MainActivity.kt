package com.volokhinaleksey.movie_club.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.movie_club.actor.ui.ActorDetailsScreen
import com.volokhinaleksey.movie_club.details.ui.DetailsScreen
import com.volokhinaleksey.movie_club.favorites.ui.FavoriteScreen
import com.volokhinaleksey.movie_club.home.ui.screen.HomeScreen
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.movie_category.ui.CategoryMoviesScreen
import com.volokhinaleksey.movie_club.navigation.ScreenState
import com.volokhinaleksey.movie_club.navigation.navigate
import com.volokhinaleksey.movie_club.search.screen.SearchScreen
import com.volokhinaleksey.movie_club.uikit.theme.DarkPrimaryColor70
import com.volokhinaleksey.movie_club.utils.ARG_ACTOR_ID
import com.volokhinaleksey.movie_club.utils.ARG_CATEGORY_NAME
import com.volokhinaleksey.movie_club.utils.ARG_MOVIE
import com.volokhinaleksey.movie_club.utils.parcelable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieClub()
        }
    }
}

@Composable
internal fun MovieClub() {
    val navController = rememberNavController()
    BottomNavigationBar(navController) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenState.SplashScreen.route,
            modifier = Modifier.padding(innerPadding),
            builder = { navigationBuilder(navController) }
        )
    }
}


internal fun NavGraphBuilder.navigationBuilder(
    navController: NavController
) {
    val openMovieDetails: (Movie) -> Unit = {
        navController.navigate(
            ScreenState.DetailsScreen.route,
            bundleOf(ARG_MOVIE to it)
        )
    }
    composable(route = ScreenState.SplashScreen.route) {
        SplashScreen {
            navController.popBackStack()
            navController.navigate(ScreenState.HomeScreen.route)
        }
    }
    composable(route = ScreenState.HomeScreen.route) {
        HomeScreen(
            showMovieDetails = { openMovieDetails(it) },
            showMoreMovies = {
                navController.navigate(
                    ScreenState.CategoryMoviesScreen.route,
                    bundleOf(ARG_CATEGORY_NAME to it)
                )
            }
        )
    }
    composable(route = ScreenState.DetailsScreen.route) {
        val movieDetailsData = it.arguments?.parcelable<Movie>(ARG_MOVIE)
        movieDetailsData?.let { data ->
            DetailsScreen(
                movie = data,
                onSimilarMovieDetails = { openMovieDetails(data) },
                onActorDetails = { actorId ->
                    navController.navigate(
                        ScreenState.ActorDetailsScreen.route,
                        bundleOf(ARG_ACTOR_ID to actorId)
                    )
                },
                onClosePage = navController::popBackStack
            )
        }
    }
    composable(route = ScreenState.SearchScreen.route) {
        SearchScreen { openMovieDetails(it) }
    }
    composable(route = ScreenState.FavoriteScreen.route) {
        FavoriteScreen { openMovieDetails(it) }
    }
    composable(route = ScreenState.CategoryMoviesScreen.route) {
        val categoryName = it.arguments?.getString(ARG_CATEGORY_NAME)
        categoryName?.let {
            CategoryMoviesScreen(
                category = categoryName,
                onItemClick = { openMovieDetails(it) },
                onBack = navController::popBackStack
            )
        }
    }
    composable(route = ScreenState.ActorDetailsScreen.route) {
        val actorId = it.arguments?.getLong(ARG_ACTOR_ID)
        actorId?.let {
            ActorDetailsScreen(
                actorId = actorId,
                onBack = navController::popBackStack
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun BottomNavigationBar(
    navController: NavController,
    content: @Composable (PaddingValues) -> Unit,
) {
    val bottomNavItems =
        listOf(ScreenState.HomeScreen, ScreenState.SearchScreen, ScreenState.FavoriteScreen)
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        bottomBar = {
            if (ScreenState.DetailsScreen.route != currentRoute && ScreenState.CategoryMoviesScreen
                    .route != currentRoute && ScreenState.ActorDetailsScreen.route != currentRoute
                && ScreenState.SplashScreen.route != currentRoute
            ) {
                NavigationBar(containerColor = DarkPrimaryColor70) {
                    bottomNavItems.forEach { item ->
                        val selected = item.route == currentRoute
                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigate(item.route) },
                            label = {
                                Text(
                                    text = stringResource(id = item.name),
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (selected) Color.White else Color.Unspecified
                                )
                            },
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = stringResource(id = item.name),
                                    )
                                }
                            }
                        )
                    }
                }
            }
        },
        content = content
    )
}