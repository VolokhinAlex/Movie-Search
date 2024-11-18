package com.volokhinaleksey.movie_club.search.di

import com.volokhinaleksey.movie_club.domain.di.searchScreenDomainModule
import com.volokhinaleksey.movie_club.search.viewmodel.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val searchScreenModule = module {
    includes(searchScreenDomainModule)
    viewModel { SearchViewModel(get()) }
}