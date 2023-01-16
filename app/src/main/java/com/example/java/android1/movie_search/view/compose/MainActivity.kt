package com.example.java.android1.movie_search.view.compose

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
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.category_movies.ARG_CATEGORY_NAME
import com.example.java.android1.movie_search.view.compose.category_movies.CategoryMoviesScreen
import com.example.java.android1.movie_search.view.compose.details.DetailsScreen
import com.example.java.android1.movie_search.view.compose.favorite.FavoriteScreen
import com.example.java.android1.movie_search.view.compose.home.Category
import com.example.java.android1.movie_search.view.compose.home.HomeScreen
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.search.SearchScreen
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor70
import com.example.java.android1.movie_search.viewmodel.MainViewModel

const val MOVIE_DATA_KEY = "Movie Data"

class MainActivity : ComponentActivity() {
    val movieCategories: MutableList<Category> = mutableListOf()
    private val homeViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val categoriesQueryList = listOf("popular", "now_playing", "upcoming", "top_rated")
//        categoriesQueryList.forEach {
//            homeViewModel.getMovieCategory(
//                it,
//                "ru-RU",
//                1
//            )
//        }
//        homeViewModel.homeLiveData.observeForever {
//            when (it) {
//                is CategoryAppState.Error -> {}
//                CategoryAppState.Loading -> {}
//                is CategoryAppState.Success -> {
//                    movieCategories.add(it.data)
//                    //Log.e("TAG_DEBUG", movieCategories.size.toString())
//                    if (movieCategories.size >= 2) setContent {
//                        Navigation(movieCategories)
//                    }
//                    if (movieCategories.size >= 3) setContent {
//                        Navigation(movieCategories)
//                    }
//                    if (movieCategories.size >= 4) setContent {
//                        Navigation(movieCategories)
//                    }
//                }
//            }
//        }
        setContent {
            Navigation()
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    BottomNavigation(navController) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenState.HomeScreen.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = ScreenState.HomeScreen.route) {
                HomeScreen(navController = navController)
            }
            composable(route = ScreenState.DetailsScreen.route) {
                val movieData = it.arguments?.getParcelable<MovieDataTMDB>(MOVIE_DATA_KEY)
                movieData?.let { data ->
                    DetailsScreen(
                        movieDataTMDB = data,
                        navController = navController
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
                val categoryName = it.arguments?.getString(ARG_CATEGORY_NAME)
                categoryName?.let {
                    CategoryMoviesScreen(
                        categoryName = categoryName,
                        navController = navController
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BottomNavigation(navController: NavController, content: @Composable (PaddingValues) -> Unit) {
    val bottomNavItems =
        listOf(ScreenState.HomeScreen, ScreenState.SearchScreen, ScreenState.FavoriteScreen)
    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = PrimaryColor70) {
                bottomNavItems.forEach { item ->
                    val selected =
                        item.route == navController.currentBackStackEntryAsState().value?.destination?.route
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
                                    contentDescription = "$item.name",
                                )
                            }
                        }
                    )
                }
            }
        },
        content = content
    )
}