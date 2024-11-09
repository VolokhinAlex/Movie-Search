package com.volokhinaleksey.movie_club.view.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.annotation.StringRes
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
import com.volokhinaleksey.movie_club.R
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.CategoryState
import com.volokhinaleksey.movie_club.model.ui.CategoryMovieUI
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.view.MOVIE_DATA_KEY
import com.volokhinaleksey.movie_club.view.category_movies.ARG_CATEGORY_NAME_DATA
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.view.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.view.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.view.theme.TITLE_SIZE
import com.volokhinaleksey.movie_club.view.widgets.ErrorMessage
import com.volokhinaleksey.movie_club.view.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.view.widgets.MovieCard
import com.volokhinaleksey.movie_club.viewmodel.MainViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The main method for the layout of the home screen methods
 * @param navController - To navigate in the other screen
 * @param homeViewModel - Main View Model, needs to get categories movies
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(
    navController: NavController,
    networkStatus: Boolean,
    homeViewModel: MainViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = true) {
        homeViewModel.fetchAllCategoriesMovies(isOnline = networkStatus)
    }
    val categoriesMovies = remember { mutableStateOf(mutableSetOf<CategoryMovieUI>()) }
    homeViewModel.popularMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(
            state,
            homeViewModel,
            categoriesMovies,
            navController,
            networkStatus
        )
    }
    homeViewModel.upcomingMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(
            state,
            homeViewModel,
            categoriesMovies,
            navController,
            networkStatus
        )
    }
    homeViewModel.topRatedMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(
            state,
            homeViewModel,
            categoriesMovies,
            navController,
            networkStatus
        )
    }
    homeViewModel.nowPlayingMoviesData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(
            state,
            homeViewModel,
            categoriesMovies,
            navController,
            networkStatus
        )
    }
}

/**
 * The method processes data received from a remote server
 * @param categoryState - The status that comes from the remote server
 * @param homeViewModel - Home View Model
 * @param categoriesMoviesList - collection for saving categories without duplicates
 * @param navController - Controller for screen navigation
 */

@Composable
private fun ServerResponseStateObserver(
    categoryState: CategoryState,
    homeViewModel: MainViewModel,
    categoriesMoviesList: MutableState<MutableSet<CategoryMovieUI>>,
    navController: NavController,
    networkStatus: Boolean
) {
    when (categoryState) {
        is CategoryState.Error -> categoryState.errorMessage.localizedMessage?.let { message ->
            ErrorMessage(message = message) {
                homeViewModel.fetchAllCategoriesMovies(
                    networkStatus
                )
            }
        }

        CategoryState.Loading -> LoadingProgressBar()
        is CategoryState.Success -> {
            categoriesMoviesList.value.add(categoryState.data)
            CategoriesMoviesList(categoriesMoviesList.value.toList(), navController)
        }
    }
}

/**
 * The method for generating movie categories
 * @param categoriesMoviesList - List with movies categories
 * @param navController - Controller for screen navigation
 */

@Composable
fun CategoriesMoviesList(
    categoriesMoviesList: List<CategoryMovieUI>,
    navController: NavController
) {
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
            HeaderHomeScreen(R.string.app_name)
            categoriesMoviesList.forEach { categoryMovies ->
                CategoryMoviesHeader(
                    categoryMovies = categoryMovies.category,
                    navController = navController
                )
                NestedCategoryMoviesList(
                    categoryMovies = categoryMovies.movie,
                    navController = navController
                )
            }
        }
    }
}

/**
 * The method for setting header of the screen
 * @param title - The title of screen from strings resource
 */

@Composable
private fun HeaderHomeScreen(@StringRes title: Int) {
    Text(
        text = stringResource(id = title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = Color.White,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

/**
 * The method for setting title of each category
 * @param title - The title of category from strings resource
 */

@Composable
private fun TitleCategoryMovies(@StringRes title: Int) {
    Text(
        text = stringResource(title),
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        color = Color.White
    )
}

/**
 * The method sets the header for the movies category
 * @param categoryMovies - Name of the current movies category
 * @param navController - Go to the screen with the full list of films of this [categoryMovies]
 */

@Composable
private fun CategoryMoviesHeader(categoryMovies: String, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        when (categoryMovies) {
            MovieCategory.NowPlaying.queryName -> TitleCategoryMovies(MovieCategory.NowPlaying.title)
            MovieCategory.TopRated.queryName -> TitleCategoryMovies(MovieCategory.TopRated.title)
            MovieCategory.Upcoming.queryName -> TitleCategoryMovies(MovieCategory.Upcoming.title)
            MovieCategory.Popular.queryName -> TitleCategoryMovies(MovieCategory.Popular.title)
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
                        categoryMovies
                    )
                    navController.navigate(
                        ScreenState.CategoryMoviesScreen.route,
                        categoryMoviesBundle
                    )
                }
        )
    }
}

/**
 * The method for generating nested side lists with movies of categories
 * @param categoryMovies - List of category movies
 * @param navController - Controller for screen navigation
 */

@Composable
fun NestedCategoryMoviesList(categoryMovies: List<MovieUI>, navController: NavController) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
        items(
            count = categoryMovies.size,
            key = {
                categoryMovies[it]
            },
            itemContent = { movieItemIndex ->
                MovieCard(
                    modifier = Modifier
                        .size(width = CARD_WIDTH_SIZE, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            val detailsMovieBundle = Bundle()
                            detailsMovieBundle.putParcelable(
                                MOVIE_DATA_KEY,
                                categoryMovies[movieItemIndex]
                            )
                            navController.navigate(
                                ScreenState.DetailsScreen.route,
                                detailsMovieBundle
                            )
                        }, movie = categoryMovies[movieItemIndex]
                )
            }
        )
    }
}