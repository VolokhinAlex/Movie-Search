package com.volokhinaleksey.movie_club.movie_category.ui

import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.widgets.MovieListPaging

@Composable
internal fun CategoryMovieList(
    items: () -> LazyPagingItems<Movie>,
    onItemClick: (Movie) -> Unit
) {
    MovieListPaging(lazyMovieItems = items(), onItemClick = { onItemClick(it) })
}
