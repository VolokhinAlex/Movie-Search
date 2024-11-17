package com.volokhinaleksey.movie_club.uikit.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.theme.CARD_TEXT_SIZE
import com.volokhinaleksey.movie_club.uikit.theme.PrimaryColor70
import com.volokhinaleksey.movie_club.utils.TMDB_LOAD_IMAGE_API
import java.text.DecimalFormat

/**
 * The method is designed to create a list of movies using ready-made movie cards
 */

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    val ratingFormat = remember { DecimalFormat("#.#") }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor70,
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data("$TMDB_LOAD_IMAGE_API${movie.posterPath}")
                    .build(), contentDescription = "movie_poster",
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .fillMaxWidth()
            )
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = CARD_TEXT_SIZE,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = movie.releaseDate,
                    color = Color.White,
                    fontSize = CARD_TEXT_SIZE
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ratingFormat.format(movie.voteAverage),
                        modifier = Modifier.padding(end = 5.dp),
                        color = Color.White,
                        fontSize = CARD_TEXT_SIZE
                    )
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "icon_rating",
                        modifier = Modifier.size(20.dp),
                        tint = Color(0XFFFFC006)
                    )
                }
            }
        }
    }
}