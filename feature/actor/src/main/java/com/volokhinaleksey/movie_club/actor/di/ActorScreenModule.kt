package com.volokhinaleksey.movie_club.actor.di

import com.volokhinaleksey.movie_club.actor.viewmodel.ActorsViewModel
import com.volokhinaleksey.movie_club.domain.di.actorScreenDomainModule
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val actorScreenModule = module {
    includes(actorScreenDomainModule)
    viewModel { ActorsViewModel(get(), get()) }
}