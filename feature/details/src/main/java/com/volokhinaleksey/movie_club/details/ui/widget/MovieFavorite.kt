package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
internal fun MovieFavorite(isFavorite: Boolean, onChangeState: (Boolean) -> Unit) {
    var favorite by remember(isFavorite) { mutableStateOf(isFavorite) }
    Box(
        modifier = Modifier
            .size(25.dp)
            .clickable {
                favorite = !favorite
                onChangeState(favorite)
            },
        contentAlignment = Alignment.Center
    ) {
        if (favorite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "",
                tint = Color(0xFFDA3C3C)
            )
        } else {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "",
                tint = Color(0xFFDA3C3C)
            )
        }
    }
}