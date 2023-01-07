package com.example.java.android1.movie_search.view.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.java.android1.movie_search.model.MovieDataTMDB

/**
 * The method creates a list in the form of a grid, which is filled with movies
 * @param lazyMovieItems - List of Movies
 * @param onItemClick - Needed to process clicks on a list item
 */

@Composable
fun ShowListMoviesPagination(
    lazyMovieItems: LazyPagingItems<MovieDataTMDB>,
    onItemClick: (MovieDataTMDB) -> Unit
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
                            onItemClick(movieData)
                        },
                    movieDataTMDB = movieData
                )
            }
        }
        item(span = { GridItemSpan(2) }) {
            lazyMovieItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> Loader()
                    loadState.append is LoadState.Loading -> Loader()
                    loadState.refresh is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.refresh as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) { lazyMovieItems.retry() }
                    }
                    loadState.append is LoadState.Error -> {
                        val message = lazyMovieItems.loadState.append as LoadState.Error
                        ErrorMessage(message = message.error.localizedMessage!!) { lazyMovieItems.retry() }
                    }
                }
            }
        }
    }
}