package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
internal fun TitleCategoryDetails(title: String, modifier: Modifier) {
    Text(
        text = title,
        modifier = modifier,
        style = MovieClubTheme.typography.heading
    )
}