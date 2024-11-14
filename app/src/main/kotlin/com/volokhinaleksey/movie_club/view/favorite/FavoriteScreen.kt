package com.volokhinaleksey.movie_club.view.favorite

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.theme.CARD_HEIGHT_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor80
import com.volokhinaleksey.movie_club.uikit.theme.TITLE_SIZE
import com.volokhinaleksey.movie_club.uikit.widgets.MovieCard
import com.volokhinaleksey.movie_club.view.MOVIE_DATA_KEY
import com.volokhinaleksey.movie_club.view.navigation.ScreenState
import com.volokhinaleksey.movie_club.view.navigation.navigate
import com.volokhinaleksey.movie_club.uikit.widgets.ErrorMessage
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.viewmodel.FavoriteViewModel
import org.koin.androidx.compose.koinViewModel

/**
 * The main method [FavoriteScreen] that combines all the necessary methods for this screen
 * @param navController - Controller for screen navigation
 * @param favoriteViewModel - Favorite View Model [FavoriteViewModel]
 */

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = koinViewModel()
) {
    LaunchedEffect(true) { favoriteViewModel.getMoviesFromLocalDataBase() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderFavoriteScreen()
        favoriteViewModel.moviesFavoriteData.observeAsState().value?.let { state ->
            RenderFavoriteDataFromLocalDataBase(
                movieState = state,
                navController = navController,
                favoriteViewModel = favoriteViewModel
            )
        }
    }
}

/**
 * The method processes state from the database
 * @param movieState - The state that came from the database. [MovieState]
 * @param navController - Needed for the method [FavoriteMoviesList]
 * @param favoriteViewModel - Needed if roomAppState came Error
 */

@Composable
private fun RenderFavoriteDataFromLocalDataBase(
    movieState: MovieState,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    when (movieState) {
        is MovieState.Error -> movieState.errorMessage.localizedMessage?.let { message ->
            ErrorMessage(message = message) { favoriteViewModel.getMoviesFromLocalDataBase() }
        }

        is MovieState.Success -> {
            val favoriteMoviesData = movieState.data
            FavoriteMoviesList(favoriteMoviesData, navController)
        }

        MovieState.Loading -> LoadingProgressBar()
    }
}

/**
 * The method creates a list in the form of a grid, which is filled with movies
 * @param favoriteMoviesData - List of Favorite Movies
 * @param navController - Needed to go to the details screen about the movie
 */

@Composable
private fun FavoriteMoviesList(
    favoriteMoviesData: List<Movie>,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(favoriteMoviesData) { _, item ->
            MovieCard(
                modifier = Modifier
                    .size(width = CARD_WIDTH_SIZE, height = CARD_HEIGHT_SIZE)
                    .padding(bottom = 10.dp)
                    .clickable {
                        val detailsMovieBundle = Bundle()
                        detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, item)
                        navController.navigate(ScreenState.DetailsScreen.route, detailsMovieBundle)
                    },
                movie = item
            )
        }
    }
}

/**
 * The method of adding a title in the header of screen
 */

@Composable
fun HeaderFavoriteScreen() {
    Text(
        text = stringResource(id = ScreenState.FavoriteScreen.name),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = Color.White,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}