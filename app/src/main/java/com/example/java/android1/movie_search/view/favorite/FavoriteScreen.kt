package com.example.java.android1.movie_search.view.favorite

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.convertMovieRoomToMovieDto
import com.example.java.android1.movie_search.view.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.navigation.navigate
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.Loader
import com.example.java.android1.movie_search.view.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel

/**
 * The main method [FavoriteScreen] that combines all the necessary methods for this screen
 * @param navController -Controller for screen navigation
 * @param favoriteViewModel - Favorite View Model [FavoriteViewModel]
 */

@Composable
fun FavoriteScreen(navController: NavController, favoriteViewModel: FavoriteViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderFavoriteScreen()
        LaunchedEffect(true) { favoriteViewModel.getMoviesFromLocalDataBase() }
        favoriteViewModel.localMovieLiveData.observeAsState().value?.let { state ->
            RenderDataFromDataBase(
                roomAppState = state,
                navController = navController,
                favoriteViewModel = favoriteViewModel
            )
        }
    }
}

/**
 * The method processes state from the database
 * @param roomAppState - The state that came from the database. [RoomAppState]
 * @param navController - Needed for the method [ShowFavoriteMovies]
 * @param favoriteViewModel - Needed if roomAppState came Error
 */

@Composable
private fun RenderDataFromDataBase(
    roomAppState: RoomAppState,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    when (roomAppState) {
        is RoomAppState.Error -> roomAppState.error.localizedMessage?.let {
            ErrorMessage(message = it) { favoriteViewModel.getMoviesFromLocalDataBase() }
        }
        is RoomAppState.Success -> {
            val movieDataRoom = roomAppState.data
            ShowFavoriteMovies(movieDataRoom, navController)
        }
        RoomAppState.Loading -> Loader()
    }
}

/**
 * The method creates a list in the form of a grid, which is filled with movies
 * @param movieDataRoom - List of Movies
 * @param navController - Needed to go to the details screen about the movie
 */

@Composable
private fun ShowFavoriteMovies(
    movieDataRoom: List<MovieDataRoom>,
    navController: NavController,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        itemsIndexed(movieDataRoom) { _, item ->
            MovieCard(
                modifier = Modifier
                    .size(width = 160.dp, height = 350.dp)
                    .padding(bottom = 10.dp)
                    .clickable {
                        val bundle = Bundle()
                        val movieDataTMDB = MovieDataTMDB(
                            null, null, item.moviePoster, null, item.movieId,
                            null, null, null, null, item.movieTitle,
                            null, item.movieRating, item.movieReleaseDate, null,
                            null, CreditsDTO(listOf()), videos = null
                        )
                        bundle.putParcelable(MOVIE_DATA_KEY, movieDataTMDB)
                        navController.navigate(ScreenState.DetailsScreen.route, bundle)
                    },
                movieDataTMDB = convertMovieRoomToMovieDto(item)
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
        text = ScreenState.FavoriteScreen.name,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = Color.White,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}