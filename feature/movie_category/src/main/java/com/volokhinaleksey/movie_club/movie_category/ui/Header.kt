package com.volokhinaleksey.movie_club.movie_category.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
internal fun Header(
    category: String,
    onBack: () -> Unit
) {
    val title = remember {
        when (category) {
            MovieCategory.NowPlaying.id -> R.string.now_playing
            MovieCategory.Popular.id -> R.string.popular
            MovieCategory.TopRated.id -> R.string.top_rated
            else -> R.string.upcoming
        }
    }
    Row(
        modifier = Modifier.padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(
            modifier = Modifier.padding(end = 5.dp),
            onClick = onBack
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = MovieClubTheme.colors.onPrimaryColor
            )
        }
        Text(
            text = stringResource(id = title),
            style = MovieClubTheme.typography.heading
        )
    }
}