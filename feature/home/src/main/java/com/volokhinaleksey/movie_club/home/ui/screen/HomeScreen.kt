package com.volokhinaleksey.movie_club.home.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.home.ui.CategoryTitle
import com.volokhinaleksey.movie_club.home.ui.HorizontalMovieList
import com.volokhinaleksey.movie_club.home.viewmodel.HomeViewModel
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.uikit.theme.TITLE_SIZE
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = koinViewModel(),
    showMoreMovies: (String) -> Unit,
    showMovieDetails: (Movie) -> Unit,
) {
    val popularMovies by homeViewModel.popularMovies.collectAsState()
    val nowPlayingMovies by homeViewModel.nowPlayingMovies.collectAsState()
    val topRatedMovies by homeViewModel.topRatedMovies.collectAsState()
    val upcomingMovies by homeViewModel.upcomingMovies.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = Color.White,
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        CategoryContainer(
            state = popularMovies,
            categoryId = MovieCategory.Popular,
            showMoreMovies = showMoreMovies,
            showMovieDetails = showMovieDetails
        )

        CategoryContainer(
            state = nowPlayingMovies,
            categoryId = MovieCategory.NowPlaying,
            showMoreMovies = showMoreMovies,
            showMovieDetails = showMovieDetails
        )

        CategoryContainer(
            state = topRatedMovies,
            categoryId = MovieCategory.TopRated,
            showMoreMovies = showMoreMovies,
            showMovieDetails = showMovieDetails
        )

        CategoryContainer(
            state = upcomingMovies,
            categoryId = MovieCategory.Upcoming,
            showMoreMovies = showMoreMovies,
            showMovieDetails = showMovieDetails
        )
    }
}

@Composable
internal fun CategoryContainer(
    state: MovieCategoryState,
    categoryId: MovieCategory,
    showMoreMovies: (String) -> Unit,
    showMovieDetails: (Movie) -> Unit,
) {
    if (state is MovieCategoryState.Success) {
        CategoryTitle(categoryId) { showMoreMovies(categoryId.id) }
        HorizontalMovieList(movies = state.data, onClick = showMovieDetails)
    }
}