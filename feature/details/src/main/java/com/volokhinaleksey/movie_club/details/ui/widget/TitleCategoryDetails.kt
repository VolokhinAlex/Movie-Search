package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.volokhinaleksey.movie_club.uikit.theme.TITLE_SIZE

@Composable
internal fun TitleCategoryDetails(title: String, modifier: Modifier) {
    Text(
        text = title,
        color = Color.White,
        modifier = modifier,
        fontSize = TITLE_SIZE,
        fontWeight = FontWeight.Bold
    )
}