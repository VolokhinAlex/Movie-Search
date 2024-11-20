package com.volokhinaleksey.movie_club.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

object MovieClubTheme {
    val colors: MovieClubColors
        @Composable get() = LocalMovieClubColors.current

    val typography: MovieClubTypography
        @Composable get() = LocalMovieClubTypography.current
}



@Composable
fun MovieClubAppTheme(
    textSize: MovieClubSize = MovieClubSize.Medium,
    padding: MovieClubPadding = MovieClubPadding.Medium,
    content: @Composable () -> Unit
) {
    val colors = MovieClubColors(
        primaryContainerColor = DarkPrimaryColor80,
        onPrimaryColor = Color.White,
        secondaryColor = DarkPrimaryColor70,
        highlightColor = Color(0xFF845496),
        onErrorColor = DarkErrorColor,
        surfaceVariant = DarkTransparentColor
    )

    val typography = MovieClubTypography(
        heading = TextStyle(
            color = Color.White,
            fontSize = HEADER_TEXT_SIZE,
            fontWeight = FontWeight.SemiBold,
        ),
        body = TextStyle(),
        toolbar = TextStyle(
            color = Color.White,
            fontSize = TOOLBAR_TEXT_SIZE,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
    )

    CompositionLocalProvider(
        LocalMovieClubColors provides colors,
        LocalMovieClubTypography provides typography,
        content = content
    )
}