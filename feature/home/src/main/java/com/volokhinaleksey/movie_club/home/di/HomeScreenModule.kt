package com.volokhinaleksey.movie_club.home.di

import com.volokhinaleksey.movie_club.domain.di.homeScreenDomainModule
import com.volokhinaleksey.movie_club.home.viewmodel.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeScreenModule = module {
    includes(homeScreenDomainModule)
    viewModel { HomeViewModel(get(), get()) }
}