package com.example.java.android1.movie_search.view.compose.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.unit.sp
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard


@Composable
fun HomeScreen() {
    val movieCategories = remember { mutableStateOf(mutableListOf<Category>()) }
    val viewModel by remember {
        mutableStateOf(MainViewModelCompose())
    }
    val moviesViewModel = viewModel.homeLiveData.observeAsState()
    val categoriesList: List<CategoriesData> =
        listOf(
            CategoriesData("Popular", "popular"),
            CategoriesData("Now Playing", "now_playing"),
            CategoriesData("Upcoming", "upcoming"),
            CategoriesData("Top Rated", "top_rated")
        )
    categoriesList.forEach {
        LaunchedEffect(Unit) {
            viewModel.getCategoryMovies(
                it.queryName,
                "ru-RU",
                1
            )
        }
    }

    when (val value = moviesViewModel.value) {
        is CategoryAppState.Error -> ErrorMessage(message = "${value.error.message}")
        CategoryAppState.Loading -> Loader()
        is CategoryAppState.Success -> {
            movieCategories.value.add(value.data)
            MovieList(movieCategories.value)
            if (movieCategories.value.size >= 2) {
                MovieList(movieCategories.value)
            } else if (movieCategories.value.size >= 3) {
                MovieList(movieCategories.value)
            } else if (movieCategories.value.size >= 4) {
                MovieList(movieCategories.value)
            }
            Log.e("TAG_CHECKER", movieCategories.value.size.toString())
        }
        else -> {}
    }
}

data class CategoriesData(
    val title: String?,
    val queryName: String
)

@Composable
fun MovieList(categoriesList: List<Category>) {
    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(PrimaryColor80), content = {
        item {
            Text(
                text = "Movie Club",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        itemsIndexed(categoriesList) { _, categories ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                when (categories.queryName) {
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
                            Log.d("", categories.queryName)
                        }
                )
            }
            NestedMovieList(categories.data)
        }
    })
}

@Composable
fun NestedMovieList(category: List<MovieDataTMDB>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, top = 10.dp), content = {
        itemsIndexed(category) { _, movieCategory ->
            MovieCard(
                modifier = Modifier
                    .size(width = 160.dp, height = 300.dp)
                    .padding(end = 10.dp), movieDataTMDB = movieCategory
            )
        }
    })
}
