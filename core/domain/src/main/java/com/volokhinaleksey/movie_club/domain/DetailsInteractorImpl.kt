package com.volokhinaleksey.movie_club.domain

import androidx.paging.map
import com.volokhinaleksey.movie_club.data.repository.DetailsRepository
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.utils.convertStringFullDateToOnlyYear
import kotlinx.coroutines.flow.map

class DetailsInteractorImpl(
    private val detailsRepository: DetailsRepository,
) : DetailsInteractor {

    override suspend fun getMovieDetails(movieId: Int) = detailsRepository
        .getMovieDetails(movieId)
        .run { copy(releaseDate = releaseDate.convertStringFullDateToOnlyYear()) }

    override fun getSimilarMovies(
        movieId: Int,
        language: String
    ) = detailsRepository
        .getSimilarMovies(movieId = movieId, language = language)
        .map { movies ->
            movies.map { it.copy(releaseDate = it.releaseDate.convertStringFullDateToOnlyYear()) }
        }

    override suspend fun saveFavoriteMovie(favorite: Favorite) {
        try {
            detailsRepository.saveFavoriteMovie(favorite)
        } catch (e: Exception) {
            println("DetailsInteractorImpl, saveFavoriteMovie, favorite=$favorite, error=$e")
        }
    }

    override suspend fun syncMovieDetails(movieId: Int, language: String) {
        try {
            detailsRepository.syncMovieDetails(movieId, language)
        } catch (e: Exception) {
            println("DetailsInteractorImpl, syncMovieDetails, movieId=$movieId, error=$e")
        }
    }
}