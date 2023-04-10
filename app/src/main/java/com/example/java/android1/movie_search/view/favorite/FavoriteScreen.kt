package com.example.java.android1.movie_search.view.favorite

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
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.states.RoomAppState
import com.example.java.android1.movie_search.utils.convertMovieRoomToMovieDto
import com.example.java.android1.movie_search.view.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.navigation.ScreenState
import com.example.java.android1.movie_search.view.navigation.navigate
import com.example.java.android1.movie_search.view.theme.CARD_HEIGHT_SIZE
import com.example.java.android1.movie_search.view.theme.CARD_WIDTH_SIZE
import com.example.java.android1.movie_search.view.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.theme.TITLE_SIZE
import com.example.java.android1.movie_search.view.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.widgets.LoadingProgressBar
import com.example.java.android1.movie_search.view.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel
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
 * @param navController - Needed for the method [FavoriteMoviesList]
 * @param favoriteViewModel - Needed if roomAppState came Error
 */

@Composable
private fun RenderFavoriteDataFromLocalDataBase(
    roomAppState: RoomAppState,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel
) {
    when (roomAppState) {
        is RoomAppState.Error -> roomAppState.errorMessage.localizedMessage?.let { message ->
            ErrorMessage(message = message) { favoriteViewModel.getMoviesFromLocalDataBase() }
        }

        is RoomAppState.Success -> {
            val favoriteMoviesData = roomAppState.moviesData
            FavoriteMoviesList(favoriteMoviesData, navController)
        }

        RoomAppState.Loading -> LoadingProgressBar()
    }
}

/**
 * The method creates a list in the form of a grid, which is filled with movies
 * @param favoriteMoviesData - List of Favorite Movies
 * @param navController - Needed to go to the details screen about the movie
 */

@Composable
private fun FavoriteMoviesList(
    favoriteMoviesData: List<MovieDataRoom>,
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
                        val movieDataTMDB = convertMovieRoomToMovieDto(item)
                        detailsMovieBundle.putParcelable(MOVIE_DATA_KEY, movieDataTMDB)
                        navController.navigate(ScreenState.DetailsScreen.route, detailsMovieBundle)
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