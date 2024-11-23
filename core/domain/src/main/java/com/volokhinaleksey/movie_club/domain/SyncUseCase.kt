package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import com.volokhinaleksey.movie_club.model.MovieCategory
import com.volokhinaleksey.movie_club.model.state.SyncState
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

interface SyncUseCase {
    suspend fun syncMoviesByCategories(categories: List<MovieCategory>): SyncState
}

class SyncUseCaseImpl(
    private val localeInteractor: LocaleInteractor,
    private val homeRepository: HomeRepository,
) : SyncUseCase {

    override suspend fun syncMoviesByCategories(categories: List<MovieCategory>): SyncState = coroutineScope {
        val lang = localeInteractor.getCurrentLanguage()
        categories.forEach {
            launch {
                try {
                    homeRepository.syncMoviesByCategory(category = it, lang = lang)
                } catch (e: Exception) {
                    println("syncMoviesByCategory, category=$it, error=$e")
                }
            }
        }
        SyncState.Finished
    }

}