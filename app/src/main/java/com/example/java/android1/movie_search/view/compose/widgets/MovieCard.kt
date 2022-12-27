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
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80

@Composable
fun MovieCard(movieDataTMDB: MovieDataTMDB) {
    Card(
        modifier = Modifier
            .size(width = 160.dp, height = 310.dp)
            .padding(end = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = PrimaryColor80,
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
//            SubcomposeAsyncImage(
//                model = "https://image.tmdb.org/t/p/w500${movieDataTMDB.poster_path}",
//                loading = {
//                    Loader()
//                },
//                contentDescription = "movie_poster",
//                modifier = Modifier
//                    .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
//                    .fillMaxWidth()
//            )
            Text(
                text = movieDataTMDB.title ?: "",
                color = Color.White,
                fontSize = 20.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, bottom = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${movieDataTMDB.release_date?.let { "".getYearFromStringFullDate(it) }}",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${movieDataTMDB.vote_average}",
                        modifier = Modifier.padding(end = 5.dp),
                        color = Color.White,
                        fontSize = 20.sp
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