package com.example.java.android1.movie_search.view.compose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.view.compose.theme.CARD_TEXT_SIZE
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor70
import java.text.DecimalFormat

/**
 * The method is designed to create a list of movies using ready-made movie cards
 */

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movieDataTMDB: MovieDataTMDB
) {
    val ratingFormat = DecimalFormat("#.#")
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
                    .data("https://image.tmdb.org/t/p/w500${movieDataTMDB.poster_path}")
                    .build(), contentDescription = "movie_poster",
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                    .fillMaxWidth()
            )
            Text(
                text = movieDataTMDB.title ?: "",
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
                    text = "${movieDataTMDB.release_date?.let { "".getYearFromStringFullDate(it) }}",
                    color = Color.White,
                    fontSize = CARD_TEXT_SIZE
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = ratingFormat.format(movieDataTMDB.vote_average),
                        modifier = Modifier.padding(end = 5.dp),
                        color = Color.White,
                        fontSize = CARD_TEXT_SIZE
                    )
                    Image(
                        painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
                        contentDescription = "icon_rating",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}