package com.example.java.android1.movie_search.view.compose.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.utils.converterMovieRoomToMovieDto
import com.example.java.android1.movie_search.view.compose.details.DetailsScreen
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard

/**
 * The main method [FavoriteScreen] that combines all the necessary methods for this screen
 */

@Composable
fun FavoriteScreen(movieDataRoom: List<MovieDataRoom>) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryColor80), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderFavoriteScreen()
        LazyHorizontalGrid(
            rows = GridCells.Adaptive(256.dp),
            modifier = Modifier.padding(20.dp),
            content = {
                itemsIndexed(movieDataRoom) { _, item ->
                    MovieCard(
                        modifier = Modifier
                            .size(width = 160.dp, height = 300.dp)
                            .padding(end = 20.dp, bottom = 35.dp),
                        movieDataTMDB = converterMovieRoomToMovieDto(item)
                    )
                }
            })
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