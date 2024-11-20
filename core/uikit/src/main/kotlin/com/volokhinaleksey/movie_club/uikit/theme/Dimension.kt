package com.volokhinaleksey.movie_club.uikit.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val TOOLBAR_TEXT_SIZE = 24.sp
val HEADER_TEXT_SIZE = 20.sp
val DETAILS_PRIMARY_SIZE = 16.sp
val DETAILS_PRIMARY_PAGING = 10.dp
val ERROR_MESSAGE_TITLE_SIZE = 25.sp
val ERROR_MESSAGE_BUTTON_SIZE = 20.sp
val CARD_TEXT_SIZE = 20.sp
val CARD_WIDTH_SIZE = 160.dp
val CARD_HEIGHT_SIZE = 350.dp


data class MovieClubTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle
)

val LocalMovieClubTypography = staticCompositionLocalOf<MovieClubTypography> { error("Local Typography not provided") }

enum class MovieClubSize {
    Small, Regular, Medium, Big
}

enum class MovieClubPadding {
    Small, Medium, Big
}