package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.utils.TMDB_LOAD_IMAGE_API

@Composable
internal fun MoviePoster(movie: Movie, onClick: () -> Unit) {
    Box {
        SubcomposeAsyncImage(
            model = "$TMDB_LOAD_IMAGE_API${movie.posterPath}",
            loading = { LoadingProgressBar() },
            contentDescription = "",
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 5.dp, bottomEnd = 5.dp))
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier.padding(5.dp),
            onClick = onClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = Icons.AutoMirrored.Filled.ArrowBack.name,
                tint = Color.White
            )
        }
    }
}