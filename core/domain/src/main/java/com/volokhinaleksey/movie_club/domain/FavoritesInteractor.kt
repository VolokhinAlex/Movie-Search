package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.FavoritesRepository
import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.utils.convertStringFullDateToOnlyYear
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

interface FavoritesInteractor {
    fun getFavorites(): Flow<List<Movie>>
}

class FavoritesInteractorImpl(
    private val favoritesRepository: FavoritesRepository,
    private val homeRepository: HomeRepository
) : FavoritesInteractor {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getFavorites() = favoritesRepository.getAllFavorites()
        .map { favorites -> favorites.map { it.movieId } }
        .flatMapLatest { ids -> homeRepository.getMovies(ids) }
        .map { it.map { it.copy(releaseDate = it.releaseDate.convertStringFullDateToOnlyYear()) } }

}