package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.utils.convertStringFullDateToOnlyYear
import kotlinx.coroutines.flow.map

class HomeInteractorImpl(
    private val homeRepository: HomeRepository,
) : HomeInteractor {

    override fun getMovies(categoryId: String) = homeRepository.getMovies(categoryId)
        .map {
            it.map { it.copy(releaseDate = it.releaseDate.convertStringFullDateToOnlyYear()) }
        }

    override suspend fun syncData(category: MovieCategory, lang: String) {
        try {
            homeRepository.syncData(category, lang)
        } catch (e: Exception) {
            println("syncData, error=$e, category=$category")
        }
    }
}