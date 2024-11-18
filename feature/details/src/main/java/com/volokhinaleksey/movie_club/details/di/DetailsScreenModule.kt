package com.volokhinaleksey.movie_club.details.di

import com.volokhinaleksey.movie_club.details.viewmodel.DetailsViewModel
import com.volokhinaleksey.movie_club.domain.di.detailsScreenDomainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailsScreenModule = module {
    includes(detailsScreenDomainModule)
    viewModel { DetailsViewModel(get(), get()) }
}