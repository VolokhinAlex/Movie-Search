package com.volokhinaleksey.movie_club.favorites.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.favorites.viewmodel.FavoritesViewModel
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.DarkPrimaryColor80
import com.volokhinaleksey.movie_club.uikit.theme.TITLE_SIZE
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    favoritesViewModel: FavoritesViewModel = koinViewModel(),
    onItemClick: (Movie) -> Unit,
) {
    val favoriteScreenState by favoritesViewModel.favoriteScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkPrimaryColor80), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = stringResource(id = R.string.favorite),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = Color.White,
            fontSize = TITLE_SIZE,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        FavoritesContent(
            favoriteScreenState = favoriteScreenState,
            onItemClick = onItemClick
        )
    }
}