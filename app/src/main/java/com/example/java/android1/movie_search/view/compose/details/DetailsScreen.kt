package com.example.java.android1.movie_search.view.compose.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.CastDTO
import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.GenresDTO
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment

@Composable
fun DetailsScreen(movieDataTMDB: MovieDataTMDB) {
    Column(
        modifier = Modifier
            .background(PrimaryColor80)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        TopOfScreen(movieDataTMDB)

        Column(
            modifier = Modifier.padding(
                start = 15.dp,
                end = 15.dp,
                top = 20.dp,
                bottom = 20.dp
            )
        ) {
            DetailOfMovie(movieDataTMDB)
            Casts(movieDataTMDB)
        }
    }

}


@Composable
private fun TopOfScreen(movieDataTMDB: MovieDataTMDB) {
    SubcomposeAsyncImage(
        model = "https://image.tmdb.org/t/p/w500${movieDataTMDB.poster_path}",
        loading = {
            Loader()
        },
        contentDescription = "movie_poster",
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp))
            .fillMaxWidth(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun DetailOfMovie(movieDataTMDB: MovieDataTMDB) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = movieDataTMDB.title ?: "null",
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Image(
            painter = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
            contentDescription = ""
        )
    }

    LazyRow(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp), content = {
        itemsIndexed(listOf("Fantasy", "Action")) { _, item ->
            Text(text = item, fontSize = 16.sp, color = Color.White)
            Text(text = ", ", fontSize = 16.sp, color = Color.White)
        }
    })

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "IMDB ${movieDataTMDB.vote_average}",
            color = Color.White, fontSize = 16.sp
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_star_rate_24),
            contentDescription = "icon_rating",
            modifier = Modifier
                .padding(end = 15.dp)
                .size(22.dp)
        )
        Text(
            text = "${movieDataTMDB.release_date?.let { "".getYearFromStringFullDate(it) }}",
            modifier = Modifier.padding(end = 15.dp),
            color = Color.White,
            fontSize = 16.sp
        )
        Text(
            text = "${
                movieDataTMDB.runtime?.let {
                    MovieDetailsFragment().timeToFormatHoursAndMinutes(
                        it
                    )
                }
            }",
            color = Color.White,
            fontSize = 16.sp
        )
    }

    Text(
        text = "Overview",
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
        color = Color.White,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
    Text(
        text = "${movieDataTMDB.overview}",
        modifier = Modifier.padding(bottom = 10.dp),
        color = Color.White
    )

    Text(
        text = "Casts",
        color = Color.White,
        modifier = Modifier.padding(bottom = 10.dp),
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun Casts(movieDataTMDB: MovieDataTMDB) {
    LazyRow(content = {
        itemsIndexed(movieDataTMDB.credits.cast) { _, item ->
            Column(
                modifier = Modifier
                    .padding(top = 15.dp, end = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SubcomposeAsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${item.profile_path}",
                    loading = {
                        Loader()
                    },
                    contentDescription = "movie_poster",
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .size(50.dp),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "${item.name}",
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    color = Color.White
                )
                Text(text = "${item.character}", color = Color.White, fontSize = 14.sp)
            }
        }
    })
}
