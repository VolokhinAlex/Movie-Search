package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.HomeDatabaseRepository
import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import com.volokhinaleksey.movie_club.model.state.MovieCategoryState
import com.volokhinaleksey.movie_club.model.ui.MovieUI

class HomeInteractorImpl(
    private val homeRepository: HomeRepository,
    private val homeDatabaseRepository: HomeDatabaseRepository,
) : HomeInteractor {

    override suspend fun getMovies(
        categoryId: String,
        language: String,
        page: Int,
        isLocalSource: Boolean,
    ): MovieCategoryState {
        return try {
            val result = if (isLocalSource) {
                homeDatabaseRepository.getMovies(categoryId, language, page)
            } else homeRepository.getMovies(categoryId, language, page)

            val movies = result.map { it.copy(releaseDate = convertStringFullDateToOnlyYear(it.releaseDate)) }

            if (!isLocalSource) homeDatabaseRepository.saveMovie(movies, categoryId)

            MovieCategoryState.Success(movies)
        } catch (e: Exception) {
            MovieCategoryState.Error(e)
        }
    }

    override suspend fun saveMovie(movies: List<MovieUI>, categoryId: String) {
        try {
            homeDatabaseRepository.saveMovie(movies, categoryId)
        } catch (e: Exception) {
            println("HomeInteractorImpl, saveMovie error=$e")
        }
    }
}