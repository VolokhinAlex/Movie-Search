package com.volokhinaleksey.movie_club.uikit.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val DETAILS_PRIMARY_PAGING = 16.dp
val ERROR_MESSAGE_TITLE_SIZE = 25.sp
val CARD_WIDTH_SIZE = 160.dp
val CARD_HEIGHT_SIZE = 350.dp

data class MovieClubTypography(
    val headingLarge: TextStyle,
    val headingMedium: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val toolbar: TextStyle
)

val LocalMovieClubTypography = staticCompositionLocalOf<MovieClubTypography> { error("Local Typography not provided") }

enum class MovieClubSize {
    Small, Regular, Medium, Big
}