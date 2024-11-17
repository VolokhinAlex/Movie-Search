package com.volokhinaleksey.movie_club.favorites.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.model.state.FavoriteScreenState
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.theme.CARD_HEIGHT_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.CARD_WIDTH_SIZE
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.uikit.widgets.MovieCard

@Composable
internal fun FavoritesContent(
    favoriteScreenState: FavoriteScreenState,
    onItemClick: (Movie) -> Unit
) {
    when (favoriteScreenState) {
        FavoriteScreenState.Loading -> LoadingProgressBar()
        is FavoriteScreenState.Success -> MovieItems(favoriteScreenState.data, onItemClick)
    }
}

@Composable
internal fun MovieItems(
    items: List<Movie>,
    onItemClick: (Movie) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            count = items.count(),
            key = { items[it].id },
        ) { index ->
            MovieCard(
                modifier = Modifier
                    .size(width = CARD_WIDTH_SIZE, height = CARD_HEIGHT_SIZE)
                    .padding(bottom = 10.dp)
                    .clickable { onItemClick(items[index]) },
                movie = items[index]
            )
        }
    }
}