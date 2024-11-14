package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.volokhinaleksey.movie_club.data.repository.DetailsRepository
import com.volokhinaleksey.movie_club.model.state.MovieState
import com.volokhinaleksey.movie_club.model.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DetailsInteractorImpl(
    private val detailsApiRepository: DetailsRepository
) : DetailsInteractor {
    override suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        isNetworkAvailable: Boolean,
        category: String,
    ): MovieState {
        return try {
            if (isNetworkAvailable) {
                val movie = detailsApiRepository.getMovieDetails(
                    movieId = movieId,
                    language = language
                )
//                detailsDatabaseRepository.saveMovieDetailsToLocalDataBase(movie)
                MovieState.Success(listOf(movie))
            } else {
//                val result = detailsDatabaseRepository.getMovieDetails(
//                    movieId = movieId,
//                    language = language
//                )
                MovieState.Success(listOf())
            }
        } catch (e: Exception) {
            MovieState.Error(e)
        }
    }

    override fun getSimilarMovies(
        movieId: Int,
        isNetworkAvailable: Boolean,
    ): Flow<PagingData<Movie>> {
//        return if (isNetworkAvailable) {
//            detailsApiRepository.getSimilarMovies(movieId = movieId).map {
//                it.map { movie ->
//                    localDataSource.saveMovie(mapMovieDataTMDBToLocalMovieData(movie))
//                    localDataSource.saveSimilarMovies(
//                        localMovieData = mapMovieDataTMDBToLocalMovieData(movie),
//                        movieId = movieId
//                    )
//                    mapMovieDataTMDBToMovieUI(moviesTMDB = movie, category = "similar")
//                }
//            }
//        } else {
//            detailsDatabaseRepository.getSimilarMovies(movieId = movieId).map {
//                it.map { movie ->
//                    detailsDatabaseRepository.saveMovie(movie)
//                    detailsDatabaseRepository.saveSimilarMovies(localMovieData = movie, movieId = movieId)
//                    mapLocalMovieToMovieUI(movie)
//                }
//            }
//        }

        return detailsApiRepository.getSimilarMovies(movieId = movieId)
            .map { it.map { it.copy(releaseDate = convertStringFullDateToOnlyYear(it.releaseDate)) } }
    }

    override suspend fun updateMovie(movieId: Int, favorite: Boolean) {
//        detailsDatabaseRepository.updateMovieFavoriteInLocalDataBase(movieId, favorite)
    }
}