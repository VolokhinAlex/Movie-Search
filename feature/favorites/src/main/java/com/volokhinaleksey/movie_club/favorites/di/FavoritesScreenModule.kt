package com.volokhinaleksey.movie_club.favorites.di

import com.volokhinaleksey.movie_club.domain.di.favoriteScreenDomainModule
import com.volokhinaleksey.movie_club.favorites.viewmodel.FavoritesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val favoriteScreenModule = module {
    includes(favoriteScreenDomainModule)
    viewModel { FavoritesViewModel(get()) }
}