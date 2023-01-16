package com.example.java.android1.movie_search.view.compose.favorite

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.converterMovieRoomToMovieDto
import com.example.java.android1.movie_search.view.compose.MOVIE_DATA_KEY
import com.example.java.android1.movie_search.view.compose.navigation.ScreenState
import com.example.java.android1.movie_search.view.compose.navigation.navigate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel

/**
 * The main method [FavoriteScreen] that combines all the necessary methods for this screen
 */

@Composable
fun FavoriteScreen(navController: NavController) {
    val favoriteViewModel by remember {
        mutableStateOf(FavoriteViewModel())
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderFavoriteScreen()
        favoriteViewModel.localMovieLiveData.observeAsState().value?.let { state ->
            RenderData(roomAppState = state, navController)
        }
    }
}

@Composable
private fun RenderData(roomAppState: RoomAppState, navController: NavController) {
    when (roomAppState) {
        is RoomAppState.Error -> {}
        is RoomAppState.Success -> {
            val movieDataRoom = roomAppState.data
            ShowFavoriteMovies(movieDataRoom, navController)
        }
        RoomAppState.Loading -> {}
    }
}

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
                            null,
                            null,
                            item.moviePoster,
                            null,
                            item.movieId,
                            null,
                            null,
                            null,
                            null,
                            item.movieTitle,
                            null,
                            item.movieRating,
                            item.movieReleaseDate,
                            null,
                            null,
                            CreditsDTO(listOf()),
                            videos = null
                        )
                        bundle.putParcelable(MOVIE_DATA_KEY, movieDataTMDB)
                        navController.navigate(ScreenState.DetailsScreen.route, bundle)
                    },
                movieDataTMDB = converterMovieRoomToMovieDto(item)
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
        text = "Favorite",
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}