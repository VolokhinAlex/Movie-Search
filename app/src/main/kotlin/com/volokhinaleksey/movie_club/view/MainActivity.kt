package com.volokhinaleksey.movie_club.view

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.movie_club.details.ui.DetailsScreen
import com.volokhinaleksey.movie_club.favorites.ui.FavoriteScreen
import com.volokhinaleksey.movie_club.home.ui.screen.HomeScreen
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.network.utils.NetworkStatus
import com.volokhinaleksey.movie_club.search.screen.SearchScreen
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor70
import com.volokhinaleksey.movie_club.utils.parcelable
import com.volokhinaleksey.movie_club.view.actor_details.ARG_ACTOR_ID
import com.volokhinaleksey.movie_club.view.actor_details.ActorDetailsScreen
import com.volokhinaleksey.movie_club.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.volokhinaleksey.movie_club.view.category_movies.CategoryMoviesScreen
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.view.splash.SplashScreen
import org.koin.android.ext.android.inject

const val MOVIE_DATA_KEY = "Movie Data"

class MainActivity : ComponentActivity() {

    private val networkStatus: NetworkStatus by inject()
    private val isNetworkAvailable = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(key1 = true) {
                networkStatus.networkObserve().collect {
                    isNetworkAvailable.value = it
                }
            }
            Navigation(isNetworkAvailable.value)
        }
    }

    @Composable
    fun Navigation(networkStatus: Boolean) {
        val navController = rememberNavController()
        val openMovieDetails: (Movie) -> Unit = {
            navController.navigate(
                ScreenState.DetailsScreen.route,
                bundleOf(MOVIE_DATA_KEY to it)
            )
        }
        BottomNavigationBar(navController) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = ScreenState.SplashScreen.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = ScreenState.SplashScreen.route) {
                    SplashScreen(navController = navController)
                }
                composable(route = ScreenState.HomeScreen.route) {
                    HomeScreen(
                        showMovieDetails = {
                            navController.navigate(
                                ScreenState.DetailsScreen.route,
                                bundleOf(MOVIE_DATA_KEY to it)
                            )
                        },
                        showMoreMovies = {
                            navController.navigate(
                                ScreenState.CategoryMoviesScreen.route,
                                bundleOf(ARG_CATEGORY_NAME_DATA to it)
                            )
                        }
                    )
                }
                composable(route = ScreenState.DetailsScreen.route) {
                    val movieDetailsData = it.arguments?.parcelable<Movie>(MOVIE_DATA_KEY)
                    movieDetailsData?.let { data ->
                        DetailsScreen(
                            movie = data,
                            onSimilarMovieDetails = { openMovieDetails(data) },
                            onActorDetails = {
                                navController.navigate(
                                    ScreenState.ActorDetailsScreen.route,
                                    bundleOf(ARG_ACTOR_ID to it)
                                )
                            },
                            onClosePage = { navController.popBackStack() }
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
                    val categoryName = it.arguments?.getString(ARG_CATEGORY_NAME_DATA)
                    categoryName?.let {
                        CategoryMoviesScreen(
                            categoryName = categoryName,
                            navController = navController,
                            networkStatus = networkStatus
                        )
                    }
                }
                composable(route = ScreenState.ActorDetailsScreen.route) {
                    val actorId = it.arguments?.getLong(ARG_ACTOR_ID)
                    actorId?.let {
                        ActorDetailsScreen(
                            actorId = actorId,
                            navController = navController,
                            networkStatus = networkStatus
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun BottomNavigationBar(
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
                    NavigationBar(containerColor = PrimaryColor70) {
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
}