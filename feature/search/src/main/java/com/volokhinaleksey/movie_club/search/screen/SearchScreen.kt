package com.volokhinaleksey.movie_club.search.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.search.viewmodel.SearchViewModel
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.uikit.widgets.MovieListPaging
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = koinViewModel(),
    onMovieDetails: (Movie) -> Unit
) {
    val searchQuery by searchViewModel.searchQuery.collectAsState()
    val isFocused by searchViewModel.isFocused.collectAsState()
    val movieItems = searchViewModel.movieItems.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80)
            .padding(top = 15.dp)
    ) {
        SearchBar(
            query = searchQuery,
            focused = isFocused,
            onQueryChange = searchViewModel::updateSearchQuery,
            onSearchFocusChange = searchViewModel::updateSearchFocus
        )
        SearchResult(
            movieItems = { movieItems },
            onItemClick = onMovieDetails
        )
    }
}

@Composable
internal fun SearchResult(
    movieItems: () -> LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit
) {
    MovieListPaging(lazyMovieItems = movieItems(), onItemClick = onItemClick)
}