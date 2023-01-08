package com.example.java.android1.movie_search.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.java.android1.movie_search.app.App.Companion.movieDao
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.*
import com.example.java.android1.movie_search.view.actor_details.ARG_ACTOR_ID
import com.example.java.android1.movie_search.view.actor_details.ActorDetailsScreen
import com.example.java.android1.movie_search.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.example.java.android1.movie_search.view.category_movies.CategoryMoviesScreen
import com.example.java.android1.movie_search.view.details.DetailsScreen
import com.example.java.android1.movie_search.view.favorite.FavoriteScreen
import com.example.java.android1.movie_search.view.home.HomeScreen
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.search.SearchScreen
import com.example.java.android1.movie_search.view.theme.PrimaryColor70
import com.example.java.android1.movie_search.viewmodel.*

const val MOVIE_DATA_KEY = "Movie Data"

class MainActivity : ComponentActivity() {

    private val remoteDataSource = RemoteDataSource()
    private val homeViewModel: MainViewModel by viewModels {
        MainViewModelFactory(HomeRepositoryImpl(remoteDataSource))
    }
    private val detailsViewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(
            DetailsRepositoryImpl(remoteDataSource),
            MovieLocalRepositoryImpl(movieDao)
        )
    }
    private val searchViewModel: SearchViewModel by viewModels {
        SearchViewModelFactory(SearchRepositoryImpl(remoteDataSource))
    }
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        FavoriteViewModelFactory(MovieLocalRepositoryImpl(movieDao))
    }
    private val categoryViewModel: CategoryMoviesViewModel by viewModels {
        CategoryMoviesViewModelFactory(CategoryRepositoryImpl(remoteDataSource))
    }
    private val actorDetailsViewModel: MovieActorViewModel by viewModels {
        MovieActorViewModelFactory(MovieActorRepositoryImpl(remoteDataSource))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Navigation(
                homeViewModel,
                detailsViewModel,
                searchViewModel,
                favoriteViewModel,
                categoryViewModel,
                actorDetailsViewModel
            )
        }
    }
}

@Composable
fun Navigation(
    homeViewModel: MainViewModel,
    detailsViewModel: DetailsViewModel,
    searchViewModel: SearchViewModel,
    favoriteViewModel: FavoriteViewModel,
    categoryViewModel: CategoryMoviesViewModel,
    actorDetailsViewModel: MovieActorViewModel
) {
    val navController = rememberNavController()
    BottomNavigationBar(navController) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenState.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ScreenState.HomeScreen.route) {
                HomeScreen(navController = navController, homeViewModel = homeViewModel)
            }
            composable(route = ScreenState.DetailsScreen.route) {
                val movieDetailsData = it.arguments?.getParcelable<MovieDataTMDB>(MOVIE_DATA_KEY)
                movieDetailsData?.let { data ->
                    DetailsScreen(
                        movieDataTMDB = data,
                        navController = navController,
                        movieDetailsViewModel = detailsViewModel
                    )
                }
            }
            composable(route = ScreenState.SearchScreen.route) {
                SearchScreen(navController = navController, searchViewModel = searchViewModel)
            }
            composable(route = ScreenState.FavoriteScreen.route) {
                FavoriteScreen(navController = navController, favoriteViewModel = favoriteViewModel)
            }
            composable(route = ScreenState.CategoryMoviesScreen.route) {
                val categoryName = it.arguments?.getString(ARG_CATEGORY_NAME_DATA)
                categoryName?.let {
                    CategoryMoviesScreen(
                        categoryName = categoryName,
                        navController = navController,
                        categoryMoviesViewModel = categoryViewModel
                    )
                }
            }
            composable(route = ScreenState.ActorDetailsScreen.route) {
                val actorId = it.arguments?.getLong(ARG_ACTOR_ID)
                actorId?.let {
                    ActorDetailsScreen(
                        actorId = actorId,
                        navController = navController,
                        actorViewModel = actorDetailsViewModel
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
            ) {
                NavigationBar(containerColor = PrimaryColor70) {
                    bottomNavItems.forEach { item ->
                        val selected = item.route == currentRoute
                        NavigationBarItem(
                            selected = selected,
                            onClick = { navController.navigate(item.route) },
                            label = {
                                Text(
                                    text = item.name,
                                    fontWeight = FontWeight.SemiBold,
                                    color = if (selected) Color.White else Color.Unspecified
                                )
                            },
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = item.name,
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