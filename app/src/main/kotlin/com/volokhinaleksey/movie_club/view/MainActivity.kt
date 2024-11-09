package com.volokhinaleksey.movie_club.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.network.utils.NetworkStatus
import com.volokhinaleksey.movie_club.utils.parcelable
import com.volokhinaleksey.movie_club.view.actor_details.ARG_ACTOR_ID
import com.volokhinaleksey.movie_club.view.actor_details.ActorDetailsScreen
import com.volokhinaleksey.movie_club.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.volokhinaleksey.movie_club.view.category_movies.CategoryMoviesScreen
import com.volokhinaleksey.movie_club.view.details.DetailsScreen
import com.volokhinaleksey.movie_club.view.favorite.FavoriteScreen
import com.volokhinaleksey.movie_club.view.home.HomeScreen
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.search.SearchScreen
import com.volokhinaleksey.movie_club.view.splash.SplashScreen
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor70
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
                    SearchScreen(
                        navController = navController,
                        networkStatus = networkStatus
                    )
                }
                composable(route = ScreenState.FavoriteScreen.route) {
                    FavoriteScreen(navController = navController)
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