package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.DETAILS_PRIMARY_PAGING
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme
import java.text.DecimalFormat

@Composable
internal fun MovieDetailsContent(
    movie: Movie,
    isFavorite: Boolean,
    onChangeFavoriteState: (Boolean) -> Unit,
) {
    val ratingFormat = DecimalFormat("#.#")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.8f),
            text = movie.title,
            style = MovieClubTheme.typography.headingLarge
        )
        MovieFavorite(
            isFavorite = isFavorite,
            onChangeState = onChangeFavoriteState
        )
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(top = DETAILS_PRIMARY_PAGING),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = "",
            tint = Color(0xFFFFC006),
            modifier = Modifier
                .padding(end = 5.dp)
                .size(22.dp)
        )
        Text(
            text = "${ratingFormat.format(movie.voteAverage)}/10 (IMDb)",
            modifier = Modifier.padding(end = 15.dp),
            style = MovieClubTheme.typography.bodyMedium
        )
        Text(
            text = movie.releaseDate,
            modifier = Modifier.padding(end = 15.dp),
            style = MovieClubTheme.typography.bodyMedium
        )
        Text(
            text = movie.runtime,
            style = MovieClubTheme.typography.bodyMedium
        )
    }

    // Genres
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = DETAILS_PRIMARY_PAGING)
    ) {
        items(
            count = movie.genres.size,
            key = { movie.genres[it].id }
        ) {
            Box(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .background(MovieClubTheme.colors.secondaryColor, RoundedCornerShape(100))
                    .padding(vertical = 5.dp, horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = movie.genres[it].name.uppercase(),
                    style = MovieClubTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }

    // Movie Overview
    TitleCategoryDetails(
        title = stringResource(id = R.string.overview),
        modifier = Modifier.padding(vertical = DETAILS_PRIMARY_PAGING),
    )

    Text(
        text = movie.overview,
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING),
        style = MovieClubTheme.typography.bodyMedium
    )
}