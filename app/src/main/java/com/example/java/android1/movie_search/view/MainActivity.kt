package com.example.java.android1.movie_search.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.java.android1.movie_search.model.ui.MovieUI
import com.example.java.android1.movie_search.network.utils.NetworkStatus
import com.example.java.android1.movie_search.repository.*
import com.example.java.android1.movie_search.utils.parcelable
import com.example.java.android1.movie_search.view.actor_details.ARG_ACTOR_ID
import com.example.java.android1.movie_search.view.actor_details.ActorDetailsScreen
import com.example.java.android1.movie_search.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.example.java.android1.movie_search.view.category_movies.CategoryMoviesScreen
import com.example.java.android1.movie_search.view.details.DetailsScreen
import com.example.java.android1.movie_search.view.favorite.FavoriteScreen
import com.example.java.android1.movie_search.view.home.HomeScreen
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.search.SearchScreen
import com.example.java.android1.movie_search.view.splash.SplashScreen
import com.example.java.android1.movie_search.view.theme.PrimaryColor70
import com.example.java.android1.movie_search.viewmodel.*
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
                    HomeScreen(navController = navController, networkStatus = networkStatus)
                }
                composable(route = ScreenState.DetailsScreen.route) {
                    val movieDetailsData = it.arguments?.parcelable<MovieUI>(MOVIE_DATA_KEY)
                    movieDetailsData?.let { data ->
                        DetailsScreen(
                            movie = data,
                            navController = navController,
                            networkStatus = networkStatus
                        )
                    }
                }
                composable(route = ScreenState.SearchScreen.route) {
                    SearchScreen(navController = navController)
                }
                composable(route = ScreenState.FavoriteScreen.route) {
                    FavoriteScreen(navController = navController)
                }
                composable(route = ScreenState.CategoryMoviesScreen.route) {
                    val categoryName = it.arguments?.getString(ARG_CATEGORY_NAME_DATA)
                    categoryName?.let {
                        CategoryMoviesScreen(
                            categoryName = categoryName,
                            navController = navController
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

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun BottomNavigationBar(
        navController: NavController,
        content: @Composable (PaddingValues) -> Unit
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