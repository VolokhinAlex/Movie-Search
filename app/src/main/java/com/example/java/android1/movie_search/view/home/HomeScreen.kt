package com.example.java.android1.movie_search.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.model.CategoryMoviesData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.navigation.navigate
import com.example.java.android1.movie_search.view.theme.CARD_WIDTH_SIZE
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.LoadingProgressBar
import com.example.java.android1.movie_search.view.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.MainViewModel

/**
 * The main method for the layout of the home screen methods
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: MainViewModel) {
    val categoriesMovies = remember { mutableStateOf(mutableSetOf<CategoryMoviesData>()) }
    homeViewModel.popularMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, categoriesMovies, navController)
    }
    homeViewModel.upcomingMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, categoriesMovies, navController)
    }
    homeViewModel.topRatedMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, categoriesMovies, navController)
    }
    homeViewModel.nowPlayingMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, categoriesMovies, navController)
    }
}

/**
 * The method processes data received from a remote server
 * @param categoryMoviesState - The status that comes from the remote server
 * @param homeViewModel - Home View Model
 * @param categoriesMoviesList - collection for saving categories without duplicates
 * @param navController - Controller for screen navigation
 */

@Composable
private fun ServerResponseStateObserver(
    categoryMoviesState: CategoryAppState,
    homeViewModel: MainViewModel,
    categoriesMoviesList: MutableState<MutableSet<CategoryMoviesData>>,
    navController: NavController
) {
    when (categoryMoviesState) {
        is CategoryAppState.Error -> categoryMoviesState.error.localizedMessage?.let { message ->
            ErrorMessage(message = message) { homeViewModel.fetchAllCategoriesMovies() }
        }
        CategoryAppState.Loading -> LoadingProgressBar()
        is CategoryAppState.Success -> {
            categoriesMoviesList.value.add(categoryMoviesState.data)
            CategoriesList(categoriesMoviesList.value.toList(), navController)
        }
    }
}

/**
 * The method for generating movie categories
 * @param categoriesMoviesList - List with movies categories
 * @param navController - Controller for screen navigation
 */

@Composable
fun CategoriesList(categoriesMoviesList: List<CategoryMoviesData>, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = Color.White,
                fontSize = TITLE_SIZE,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            categoriesMoviesList.forEach { categoryMovies ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    when (categoryMovies.queryName) {
                        MovieCategory.NowPlaying.queryName -> SetCategoryTitle(MovieCategory.NowPlaying.title)
                        MovieCategory.TopRated.queryName -> SetCategoryTitle(MovieCategory.TopRated.title)
                        MovieCategory.Upcoming.queryName -> SetCategoryTitle(MovieCategory.Upcoming.title)
                        MovieCategory.Popular.queryName -> SetCategoryTitle(MovieCategory.Popular.title)
                    }
                    Image(
                        painter = painterResource(id = R.drawable.compose_arrow_right),
                        contentDescription = stringResource(id = R.string.more_movies),
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                val categoryMoviesBundle = Bundle()
                                categoryMoviesBundle.putString(
                                    ARG_CATEGORY_NAME_DATA,
                                    categoryMovies.queryName
                                )
                                navController.navigate(
                                    ScreenState.CategoryMoviesScreen.route,
                                    categoryMoviesBundle
                                )
                            }
                    )
                }
                NestedCategoryMoviesList(categoryMovies.data, navController)
            }
        }
    }
}

/**
 * The method for generating nested side lists with movies of categories
 * @param category - List of category movies
 * @param navController - Controller for screen navigation
 */

@Composable
fun NestedCategoryMoviesList(category: List<MovieDataTMDB>, navController: NavController) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
        items(
            count = category.size,
            key = {
                category[it]
            },
            itemContent = { itemIndex ->
                MovieCard(
                    modifier = Modifier
                        .size(width = CARD_WIDTH_SIZE, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            val detailsMovieBundle = Bundle()
                            detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, category[itemIndex])
                            navController.navigate(
                                ScreenState.DetailsScreen.route,
                                detailsMovieBundle
                            )
                        }, movieDataTMDB = category[itemIndex]
                )
            }
        )
    }
}

@Composable
private fun SetCategoryTitle(title: Int) {
    Text(
        text = stringResource(title),
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}