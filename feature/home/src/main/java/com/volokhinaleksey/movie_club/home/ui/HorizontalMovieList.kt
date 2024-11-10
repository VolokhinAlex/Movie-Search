package com.volokhinaleksey.movie_club.home.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.uikit.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.uikit.widgets.MovieCard

@Composable
internal fun HorizontalMovieList(
    movies: List<MovieUI>,
    onClick: (MovieUI) -> Unit,
) {
    LazyRow(modifier = Modifier.padding(start = 20.dp, bottom = 20.dp)) {
        items(
            count = movies.size,
            key = { movies[it] },
            itemContent = { movieItemIndex ->
                MovieCard(
                    modifier = Modifier
                        .size(width = CARD_WIDTH_SIZE, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable { onClick(movies[movieItemIndex]) },
                    movie = movies[movieItemIndex]
                )
            }
        )
    }
}