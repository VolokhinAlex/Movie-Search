package com.volokhinaleksey.movie_club.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

object MovieClubTheme {
    val colors: MovieClubColors
        @Composable get() = LocalMovieClubColors.current

    val typography: MovieClubTypography
        @Composable get() = LocalMovieClubTypography.current
}



@Composable
fun MovieClubAppTheme(content: @Composable () -> Unit) {
    val colors = MovieClubColors(
        primaryContainerColor = DarkPrimaryColor80,
        onPrimaryColor = Color.White,
        secondaryColor = DarkPrimaryColor70,
        highlightColor = Color(0xFFCC94AC),
        onErrorColor = DarkErrorColor,
        surfaceVariant = DarkTransparentColor
    )

    val typography = MovieClubTypography(
        headingLarge = TextStyle(
            color = Color.White,
            fontSize = 21.sp,
            fontWeight = FontWeight.SemiBold,
        ),
        headingMedium = TextStyle(
            color = Color.White,
            fontSize = 20.sp,
        ),
        bodyLarge = TextStyle(
            fontSize = 18.sp,
            color = Color.White
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp,
            color = Color.White
        ),
        bodySmall = TextStyle(
            fontSize = 13.sp,
            color = Color.White
        ),
        toolbar = TextStyle(
            color = Color.White,
            fontSize = 24.sp,
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