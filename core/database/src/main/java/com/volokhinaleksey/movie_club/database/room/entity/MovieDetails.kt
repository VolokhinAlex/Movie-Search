package com.volokhinaleksey.movie_club.database.room.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.volokhinaleksey.movie_club.model.ui.Movie

data class MovieDetails(
    @Embedded val movie: MovieEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id",
    )
    val isFavorite: FavoriteEntity?,

    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id",
    )
    val actors: List<ActorEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "movie_id"
    )
    val trailers: List<MovieTrailersEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MoviesGenresEntity::class,
            parentColumn = "movie_id",
            entityColumn = "genre_id",
        ),
    )
    val genres: List<GenreEntity>
)

fun MovieDetails.asExternalModel() = Movie(
    adult = movie.adult,
    backdropPath = movie.backdropPath,
    posterPath = movie.poster,
    id = movie.id,
    imdbId = movie.imdbId,
    genres = genres.map(GenreEntity::asExternalModel),
    originalLanguage = movie.language,
    overview = movie.overview,
    title = movie.title,
    voteAverage = movie.voteAverage,
    releaseDate = movie.releaseDate,
    runtime = movie.runtime,
    favorite = isFavorite?.favorite ?: false,
    actors = actors.map(ActorEntity::asExternalModel),
    videos = trailers.map(MovieTrailersEntity::asExternalModel)
)
