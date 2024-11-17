package com.volokhinaleksey.movie_club.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.volokhinaleksey.movie_club.data.repository.CategoryRepository
import com.volokhinaleksey.movie_club.model.ui.Movie
import com.volokhinaleksey.movie_club.utils.convertStringFullDateToOnlyYear
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface CategoryInteractor {
    fun getCategoryMovies(category: String, language: String): Flow<PagingData<Movie>>
}

class CategoryInteractorImpl(
    private val categoryRepository: CategoryRepository,
) : CategoryInteractor {

    override fun getCategoryMovies(category: String, language: String): Flow<PagingData<Movie>> {
        return categoryRepository.getCategoryMovies(category, language).map { movies ->
            movies.map { it.copy(releaseDate = it.releaseDate.convertStringFullDateToOnlyYear()) }
        }
    }

}