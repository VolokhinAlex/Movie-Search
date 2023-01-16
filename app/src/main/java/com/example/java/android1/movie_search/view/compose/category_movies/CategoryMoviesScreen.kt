package com.example.java.android1.movie_search.view.compose.category_movies

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.compose.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.compose.home.MovieCategory
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.navigation.navigate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModel

const val ARG_CATEGORY_NAME = "Category Name"

@Composable
fun CategoryMoviesScreen(categoryName: String, navController: NavController) {
    val categoryMoviesViewModel by lazy {
        mutableStateOf(CategoryMoviesViewModel())
    }
    val categoryMovies =
        categoryMoviesViewModel.value.getCategoryMoviesFromRemoteServer(categoryName)
            .collectAsLazyPagingItems()
    Column(modifier = Modifier.background(PrimaryColor80)) {
        when (categoryName) {
            MovieCategory.NowPlaying.queryName -> Header(
                MovieCategory.NowPlaying.title,
                navController
            )
            MovieCategory.TopRated.queryName -> Header(MovieCategory.TopRated.title, navController)
            MovieCategory.Upcoming.queryName -> Header(MovieCategory.Upcoming.title, navController)
            MovieCategory.Popular.queryName -> Header(MovieCategory.Popular.title, navController)
        }
        ShowCategoryMovies(categoryMovies, navController)
    }
}

@Composable
private fun Header(text: String, navController: NavController) {
    Row(
        modifier = Modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(end = 5.dp),
            onClick = {
                navController.navigateUp()
            }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = Color.White
            )
        }
        Text(
            text = text,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
private fun ShowCategoryMovies(
    lazyMovieItems: LazyPagingItems<MovieDataTMDB>,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(lazyMovieItems.itemCount) { item ->
            lazyMovieItems[item]?.let { movieData ->
                MovieCard(
                    modifier = Modifier
                        .size(width = 160.dp, height = 350.dp)
                        .padding(bottom = 10.dp)
                        .clickable {
                            val bundle = Bundle()
                            bundle.putParcelable(MOVIE_DATA_KEY, movieData)
                            navController.navigate(ScreenState.DetailsScreen.route, bundle)
                        },
                    movieDataTMDB = movieData
                )
            }
        }
        item(span = { GridItemSpan(2) }) {
            lazyMovieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        Loader()
                    }
                    loadState.append is LoadState.Loading -> {
                        Loader()
                    }
                    loadState.refresh is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.refresh as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) {
                            lazyMovieItems.retry()
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.append as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) {
                            lazyMovieItems.retry()
                        }
                    }
                }
            }
        }
    }
}