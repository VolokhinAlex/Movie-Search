package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import kotlinx.coroutines.flow.map

class HomeInteractorImpl(
    private val homeRepository: HomeRepository,
) : HomeInteractor {

    override fun getMovies(categoryId: String) = homeRepository.getMovies(categoryId)
        .map {
            it.map { it.copy(releaseDate = convertStringFullDateToOnlyYear(it.releaseDate)) }
        }

    override suspend fun syncData(categoryId: String, lang: String) {
        try {
            homeRepository.syncData(categoryId, lang)
        } catch (e: Exception) {
            println("syncData, error=$e, categoryId=$categoryId")
        }
    }
}