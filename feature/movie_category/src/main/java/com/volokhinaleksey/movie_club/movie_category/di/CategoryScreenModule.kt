package com.volokhinaleksey.movie_club.movie_category.di

import com.volokhinaleksey.movie_club.domain.di.categoryScreenDomainModule
import com.volokhinaleksey.movie_club.movie_category.viewmodel.CategoryMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val categoryScreenModule = module {
    includes(categoryScreenDomainModule)
    viewModel { CategoryMoviesViewModel(get(), get()) }
}