package com.volokhinaleksey.movie_club.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
internal fun CategoryTitle(
    categoryId: MovieCategory,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(
                when (categoryId) {
                    MovieCategory.NowPlaying -> R.string.now_playing
                    MovieCategory.Popular -> R.string.popular
                    MovieCategory.TopRated -> R.string.top_rated
                    MovieCategory.Upcoming -> R.string.upcoming
                }
            ),
            style = MovieClubTheme.typography.heading
        )
        IconButton(onClick) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(id = R.string.more_movies),
                modifier = Modifier.size(28.dp),
                tint = MovieClubTheme.colors.onPrimaryColor
            )
        }
    }
}