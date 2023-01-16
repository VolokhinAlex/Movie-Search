package com.example.java.android1.movie_search.view.compose.home

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.app.MovieCategory
import com.example.java.android1.movie_search.model.CategoryData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.compose.category_movies.ARG_CATEGORY_NAME_DATA
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.navigation.navigate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.MainViewModel

/**
 * The main method for the layout of the home screen methods
 */

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: MainViewModel) {
    val movieCategories = remember { mutableStateOf(mutableSetOf<CategoryData>()) }
    homeViewModel.popularMutableLiveData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, movieCategories, navController)
    }
    homeViewModel.upcomingMutableLiveData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, movieCategories, navController)
    }
    homeViewModel.topRatedMutableLiveData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, movieCategories, navController)
    }
    homeViewModel.nowPlayingMutableLiveData.observeAsState().value?.let { state ->
        ServerResponseStateObserver(state, homeViewModel, movieCategories, navController)
    }
}

/**
 * The method processes data received from a remote server
 */

@Composable
private fun ServerResponseStateObserver(
    state: CategoryAppState,
    homeViewModel: MainViewModel,
    movieCategories: MutableState<MutableSet<CategoryData>>,
    navController: NavController
) {
    when (state) {
        is CategoryAppState.Error -> state.error.localizedMessage?.let { message ->
            ErrorMessage(message = message) { homeViewModel.fetchAllCategoriesMovies() }
        }
        CategoryAppState.Loading -> Loader()
        is CategoryAppState.Success -> {
            movieCategories.value.add(state.data)
            CategoriesList(movieCategories.value.toList(), navController)
        }
    }
}

/**
 * The method for generating movie categories
 */

@Composable
fun CategoriesList(categoriesList: List<CategoryData>, navController: NavController) {
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
                text = "Movie Club",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = Color.White,
                fontSize = TITLE_SIZE,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            categoriesList.forEach {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    when (it.queryName) {
                        MovieCategory.NowPlaying.queryName -> Text(
                            text = MovieCategory.NowPlaying.title,
                            fontSize = TITLE_SIZE,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        MovieCategory.TopRated.queryName -> Text(
                            text = MovieCategory.TopRated.title,
                            fontSize = TITLE_SIZE,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        MovieCategory.Upcoming.queryName -> Text(
                            text = MovieCategory.Upcoming.title,
                            fontSize = TITLE_SIZE,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        MovieCategory.Popular.queryName -> Text(
                            text = MovieCategory.Popular.title,
                            fontSize = TITLE_SIZE,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                    Image(
                        painter = painterResource(id = R.drawable.compose_arrow_right),
                        contentDescription = "more movies",
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                val bundle = Bundle()
                                bundle.putString(ARG_CATEGORY_NAME_DATA, it.queryName)
                                navController.navigate(
                                    ScreenState.CategoryMoviesScreen.route,
                                    bundle
                                )
                            }
                    )
                }
                NestedMovieList(it.data, navController)
            }
        }
    }
}

/**
 * The method for generating nested side lists with movies of categories
 */

@Composable
fun NestedMovieList(category: List<MovieDataTMDB>, navController: NavController) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp), content = {
        items(
            count = category.size,
            key = {
                category[it]
            },
            itemContent = { index ->
                MovieCard(
                    modifier = Modifier
                        .size(width = 160.dp, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable {
                            val bundle = Bundle()
                            bundle.putParcelable(MOVIE_DATA_KEY, category[index])
                            navController.navigate(ScreenState.DetailsScreen.route, bundle)
                        }, movieDataTMDB = category[index]
                )
            }
        )
    })
}
