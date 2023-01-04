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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.compose.category_movies.ARG_CATEGORY_NAME
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.navigation.navigate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.MainViewModel

@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavController) {
    val movieCategories = remember { mutableStateOf(mutableListOf<Category>()) }
    val homeMainViewModel by remember {
        mutableStateOf(MainViewModel())
    }
    val categoriesQueryList = listOf("popular", "now_playing", "upcoming", "top_rated")
    LaunchedEffect(Unit) {
        categoriesQueryList.forEach {
            homeMainViewModel.getMovieCategory(
                it,
                "ru-RU",
                1
            )
        }
    }
    homeMainViewModel.homeLiveData.observeAsState().value?.let { state ->
        RenderData(state, movieCategories, navController)
    }
}

@Composable
private fun RenderData(
    categoryAppState: CategoryAppState,
    movieCategories: MutableState<MutableList<Category>>,
    navController: NavController,
) {
    when (categoryAppState) {
        is CategoryAppState.Success -> {
            val movieData = categoryAppState.data
            movieCategories.value.add(movieData)
            MovieList(movieCategories.value, navController)
            if (movieCategories.value.size >= 2) MovieList(movieCategories.value, navController)
            if (movieCategories.value.size >= 3) MovieList(movieCategories.value, navController)
            if (movieCategories.value.size >= 4) MovieList(movieCategories.value, navController)
        }
        is CategoryAppState.Error -> categoryAppState.error.localizedMessage?.let {
            ErrorMessage(message = it) {}
        }
        CategoryAppState.Loading -> Loader()
    }
}

data class CategoriesData(
    val title: String?,
    val queryName: String
)

@Composable
fun MovieList(categoriesList: List<Category>, navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(PrimaryColor80)
    ) {
        categoriesList.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (it.queryName) {
                    MovieCategory.NowPlaying.queryName -> Text(
                        text = MovieCategory.NowPlaying.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    MovieCategory.TopRated.queryName -> Text(
                        text = MovieCategory.TopRated.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    MovieCategory.Upcoming.queryName -> Text(
                        text = MovieCategory.Upcoming.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    MovieCategory.Popular.queryName -> Text(
                        text = MovieCategory.Popular.title,
                        fontSize = 22.sp,
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
                            bundle.putString(ARG_CATEGORY_NAME, it.queryName)
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

//    LazyColumn(modifier = Modifier
//        .fillMaxSize()
//        .background(PrimaryColor80), content = {
//        item {
//            Text(
//                text = "Movie Club",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 20.dp),
//                color = Color.White,
//                fontSize = 22.sp,
//                fontWeight = FontWeight.Bold,
//                textAlign = TextAlign.Center
//            )
//        }
//        itemsIndexed(categoriesList) { _, categories ->
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(20.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                when (categories.queryName) {
//                    MovieCategory.NowPlaying.queryName -> Text(
//                        text = MovieCategory.NowPlaying.title,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//                    MovieCategory.TopRated.queryName -> Text(
//                        text = MovieCategory.TopRated.title,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//                    MovieCategory.Upcoming.queryName -> Text(
//                        text = MovieCategory.Upcoming.title,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//                    MovieCategory.Popular.queryName -> Text(
//                        text = MovieCategory.Popular.title,
//                        fontSize = 22.sp,
//                        fontWeight = FontWeight.Bold,
//                        color = Color.White
//                    )
//                }
//                Image(
//                    painter = painterResource(id = R.drawable.compose_arrow_right),
//                    contentDescription = "more movies",
//                    modifier = Modifier
//                        .size(28.dp)
//                        .clickable {
//                            val bundle = Bundle()
//                            bundle.putString(ARG_CATEGORY_NAME, categories.queryName)
//                            navController.navigate(
//                                ScreenState.CategoryMoviesScreen.route,
//                                bundle
//                            )
//                        }
//                )
//            }
//            NestedMovieList(categories.data, navController)
//        }
//    })
}

@Composable
fun NestedMovieList(category: List<MovieDataTMDB>, navController: NavController) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, top = 10.dp), content = {
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
