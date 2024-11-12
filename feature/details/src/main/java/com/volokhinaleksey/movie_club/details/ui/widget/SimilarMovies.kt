package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.uikit.widgets.MovieCard

@Composable
internal fun SimilarMovies(
    similarMoviesFlow: () -> LazyPagingItems<MovieUI>,
    onSimilarMovieDetails: (MovieUI?) -> Unit
) {
    TitleCategoryDetails(
        title = stringResource(id = R.string.similar),
        modifier = Modifier.padding(top = 15.dp)
    )

    val lazySimilarMovies = similarMoviesFlow()

    LazyRow(modifier = Modifier.padding(bottom = 20.dp, top = 15.dp)) {

        items(
            count = lazySimilarMovies.itemCount
        ) { index ->
            lazySimilarMovies[index]?.let {
                MovieCard(
                    modifier = Modifier
                        .size(width = CARD_WIDTH_SIZE, height = 300.dp)
                        .padding(end = 10.dp)
                        .clickable { onSimilarMovieDetails(lazySimilarMovies[index]) },
                    movie = it
                )
            }
        }
    }
}