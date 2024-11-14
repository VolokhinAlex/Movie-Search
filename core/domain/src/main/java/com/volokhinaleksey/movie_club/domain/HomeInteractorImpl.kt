package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.HomeRepository

class HomeInteractorImpl(
    private val homeRepository: HomeRepository,
) : HomeInteractor {

    override fun getMovies(categoryId: String) = homeRepository.getMovies(categoryId)

    override suspend fun syncData(categoryId: String, lang: String) {
        try {
            homeRepository.syncData(categoryId, lang)
        } catch (e: Exception) {
            println("syncData, error=$e, categoryId=$categoryId")
        }
    }
}