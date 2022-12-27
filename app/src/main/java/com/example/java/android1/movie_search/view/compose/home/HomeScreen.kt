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
import com.example.java.android1.movie_search.viewmodel.AppState


@Composable
fun HomeScreen() {

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
            viewModel.getMovieCategory(
                it.queryName,
                "ru-RU",
                1
            )
        }
    }

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
                Text(
                    text = categories.title ?: "Not Found",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Image(
                    painter = painterResource(id = R.drawable.compose_arrow_right),
                    contentDescription = "more movies",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            Log.d("", categories.queryName.toString())
                        }
                )
            }

            // Some kind of request to remote server to receive a data
            when (val value = moviesViewModel.value) {
                is AppState.Error -> ErrorMessage(message = "${value.error.message}")
                AppState.Loading -> Loader()
                is AppState.Success -> {
                    NestedMovieList(value.data)
                }
                else -> {}
            }
        }
    })
}

data class CategoriesData(
    val title: String?,
    val queryName: String
)

@Composable
fun NestedMovieList(category: List<MovieDataTMDB>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, top = 10.dp), content = {
        itemsIndexed(category) { _, movieCategory ->
            MovieCard(movieDataTMDB = movieCategory)
        }
    })
}
