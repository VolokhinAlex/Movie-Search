package com.volokhinaleksey.movie_club.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.volokhinaleksey.movie_club.details.ui.widget.MovieCasts
import com.volokhinaleksey.movie_club.details.ui.widget.MovieDetailsContent
import com.volokhinaleksey.movie_club.details.ui.widget.MoviePoster
import com.volokhinaleksey.movie_club.details.ui.widget.MovieTrailer
import com.volokhinaleksey.movie_club.details.ui.widget.SimilarMovies
import com.volokhinaleksey.movie_club.details.viewmodel.DetailsViewModel
import com.volokhinaleksey.movie_club.model.state.DetailsMovieState
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme
import com.volokhinaleksey.movie_club.uikit.widgets.ErrorMessage
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreen(
    movie: Movie,
    detailsViewModel: DetailsViewModel = koinViewModel(),
    onSimilarMovieDetails: (Movie?) -> Unit,
    onActorDetails: (Long) -> Unit,
    onClosePage: () -> Unit,
) {
    val movieDetailsState by detailsViewModel.movieDetails.collectAsState()
    val similarMoviesFlow = detailsViewModel.getSimilarMovies(movie.id).collectAsLazyPagingItems()

    LaunchedEffect(true) { detailsViewModel.getMovieDetails(movie) }

    Column(
        modifier = Modifier
            .background(MovieClubTheme.colors.primaryContainerColor)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center
    ) {
        when (val state = movieDetailsState) {
            is DetailsMovieState.Error -> {
                ErrorMessage(
                    message = state.message,
                    click = { detailsViewModel.getMovieDetails(movie) }
                )
            }

            DetailsMovieState.Loading -> LoadingProgressBar()

            is DetailsMovieState.Success -> {
                DetailsContent(
                    movie = state.data,
                    similarMoviesFlow = { similarMoviesFlow },
                    onSimilarMovieDetails = onSimilarMovieDetails,
                    onChangeFavoriteState = detailsViewModel::saveFavoriteMovie,
                    onActorDetails = onActorDetails,
                    onClosePage = onClosePage
                )
            }
        }
    }
}

@Composable
internal fun DetailsContent(
    movie: Movie,
    similarMoviesFlow: () -> LazyPagingItems<Movie>,
    onSimilarMovieDetails: (Movie?) -> Unit,
    onChangeFavoriteState: (Int, Boolean) -> Unit,
    onActorDetails: (Long) -> Unit,
    onClosePage: () -> Unit,
) {
    MoviePoster(movie = movie, onClick = onClosePage)
    Column(
        modifier = Modifier.padding(
            start = 15.dp,
            end = 15.dp,
            top = 20.dp,
            bottom = 20.dp
        )
    ) {
        MovieDetailsContent(
            movie = movie,
            isFavorite = movie.favorite,
            onChangeFavoriteState = { onChangeFavoriteState(movie.id, it) }
        )

        MovieCasts(movie = movie, onActorDetails = onActorDetails)

        if (movie.videos.isNotEmpty()) {
            MovieTrailer(movie)
        }

        SimilarMovies(
            similarMoviesFlow = similarMoviesFlow,
            onSimilarMovieDetails = onSimilarMovieDetails
        )
    }
}