package com.volokhinaleksey.movie_club.details.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.uikit.R
import com.volokhinaleksey.movie_club.uikit.theme.DETAILS_PRIMARY_PAGING
import com.volokhinaleksey.movie_club.uikit.theme.MovieClubTheme
import com.volokhinaleksey.movie_club.uikit.widgets.LoadingProgressBar
import com.volokhinaleksey.movie_club.utils.TMDB_LOAD_IMAGE_API

@Composable
internal fun MovieCasts(
    movie: Movie,
    onActorDetails: (Long) -> Unit,
) {
    TitleCategoryDetails(
        title = stringResource(id = R.string.casts),
        modifier = Modifier.padding(bottom = DETAILS_PRIMARY_PAGING)
    )
    LazyRow(
        content = {
            itemsIndexed(movie.actors) { _, item ->
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, end = 15.dp)
                        .clickable { onActorDetails(item.actorId) },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SubcomposeAsyncImage(
                        model = "$TMDB_LOAD_IMAGE_API${item.profilePath}",
                        loading = { LoadingProgressBar() },
                        contentDescription = "movie_poster",
                        modifier = Modifier
                            .clip(CircleShape)
                            .fillMaxWidth()
                            .size(50.dp),
                        contentScale = ContentScale.Crop
                    )

                    Text(
                        text = item.name,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                        style = MovieClubTheme.typography.bodyMedium
                    )

                    Text(
                        text = item.character,
                        style = MovieClubTheme.typography.bodyMedium
                    )
                }
            }
        }
    )
}