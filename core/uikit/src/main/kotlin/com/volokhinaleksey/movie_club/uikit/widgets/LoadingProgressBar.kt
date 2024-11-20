package com.volokhinaleksey.movie_club.uikit.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

@Composable
fun LoadingProgressBar() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MovieClubTheme.colors.primaryContainerColor),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MovieClubTheme.colors.highlightColor
        )
    }
}