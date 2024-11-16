package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.volokhinaleksey.movie_club.data.repository.DetailsRepository
import com.volokhinaleksey.movie_club.model.ui.Favorite
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailsInteractorImpl(
    private val detailsRepository: DetailsRepository,
) : DetailsInteractor {

    override suspend fun getMovieDetails(movieId: Int) = detailsRepository
        .getMovieDetails(movieId)
        .run { copy(releaseDate = convertStringFullDateToOnlyYear(releaseDate)) }

    override fun getSimilarMovies(
        movieId: Int,
        isNetworkAvailable: Boolean,
    ): Flow<PagingData<Movie>> {
        return detailsRepository.getSimilarMovies(movieId = movieId)
            .map { it.map { it.copy(releaseDate = convertStringFullDateToOnlyYear(it.releaseDate)) } }
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