package com.example.java.android1.movie_search.view.compose.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80


@Composable
fun HomeScreen() {

    val categoriesList: List<CategoriesData> =
        listOf(
            CategoriesData("Popular", "popular"),
            CategoriesData("Now Playing", "now_playing"),
            CategoriesData("Upcoming", "upcoming"),
            CategoriesData("Top Rated", "top_rated")
        )

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .background(PrimaryColor80), content = {
        itemsIndexed(categoriesList) { _, categories ->

            Text(
                text = categories.title ?: "Not Found",
                modifier = Modifier.padding(20.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            // Some kind of request to remote server to receive a data
//            LaunchedEffect(Unit) {
//                categories.queryName?.let {
//                    viewModel.getUpcomingMoviesFromRemoteSource(
//                        it,
//                        "ru-RU",
//                        1
//                    )
//                }
//            }

            LazyRow(modifier = Modifier.padding(start = 20.dp, top = 10.dp), content = {
                itemsIndexed(
                    listOf(
                        "Popular",
                        "Now Playing",
                        "Upcoming",
                        "Top Rated"
                    )
                ) { _, movieCategory ->
                    Text(
                        text = movieCategory,
                        modifier = Modifier.padding(end = 10.dp),
                        color = Color.White
                    )
                }
            })

        }
    })
}

data class CategoriesData(
    val title: String?,
    val queryName: String?
)

@Composable
fun NestedMovieList(category: List<MovieDataTMDB>) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, top = 10.dp), content = {
        itemsIndexed(category) { _, movieCategory ->
            Text(
                text = movieCategory.title ?: "",
                modifier = Modifier.padding(end = 10.dp),
                color = Color.White
            )
        }
    })
}