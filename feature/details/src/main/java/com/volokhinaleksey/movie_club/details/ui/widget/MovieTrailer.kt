package com.volokhinaleksey.movie_club.details.ui.widget

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.movie_club.model.ui.MovieUI
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.TransparentColor
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar

@Composable
internal fun MovieTrailer(movie: MovieUI) {
    val context = LocalContext.current
    TitleCategoryDetails(
        title = stringResource(id = R.string.trailer),
        modifier = Modifier.padding(top = 15.dp, bottom = 15.dp)
    )
    Box(modifier = Modifier.size(width = 250.dp, height = 150.dp)) {
        SubcomposeAsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.backdropPath}",
            loading = { LoadingProgressBar() },
            contentDescription = "movie_poster",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .background(TransparentColor)
                .clip(RoundedCornerShape(8.dp))
                .fillMaxSize()
                .clickable {
                    val trailerIntent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=${movie.videos[0].key})")
                    )
                    startActivity(context, trailerIntent, null)
                }) {
            Image(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play Video",
                modifier = Modifier
                    .align(Center)
                    .size(64.dp)
            )
        }
    }
}
