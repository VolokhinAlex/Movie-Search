package com.volokhinaleksey.movie_club.uikit.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

internal val DarkErrorColor = Color(0xFFA20C0C)
internal val DarkPrimaryColor80 = Color(0xFF111111)
internal val DarkPrimaryColor70 = Color(0xFF1B1C1E)
internal val DarkTransparentColor = Color(0x5E000000)

@Immutable
data class MovieClubColors(
    val primaryContainerColor: Color,
    val onPrimaryColor: Color,
    val secondaryColor: Color,
    val highlightColor: Color,
    val onErrorColor: Color,
    val surfaceVariant: Color,
)

val LocalMovieClubColors =
    staticCompositionLocalOf<MovieClubColors> { error("Colors not provided") }