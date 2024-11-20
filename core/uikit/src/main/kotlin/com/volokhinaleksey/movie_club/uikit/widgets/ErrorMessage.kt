package com.volokhinaleksey.movie_club.uikit.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.ERROR_MESSAGE_BUTTON_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.ERROR_MESSAGE_TITLE_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme

/**
 * The widget is for show an error message
 */

@Composable
fun ErrorMessage(message: String, click: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MovieClubTheme.colors.primaryContainerColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message, fontSize = ERROR_MESSAGE_TITLE_SIZE, color = MovieClubTheme.colors.onErrorColor,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center
        )
        Button(onClick = click) {
            Text(
                text = stringResource(id = R.string.retry),
                fontSize = ERROR_MESSAGE_BUTTON_SIZE,
                color = MovieClubTheme.colors.onPrimaryColor,
                textAlign = TextAlign.Center
            )
        }
    }
}
