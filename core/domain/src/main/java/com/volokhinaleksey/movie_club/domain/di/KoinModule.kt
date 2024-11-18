package com.volokhinaleksey.movie_club.domain.di

import com.volokhinaleksey.movie_club.data.di.actorScreenRepositoryModule
import com.volokhinaleksey.movie_club.data.di.categoryScreenRepositoryModule
import com.volokhinaleksey.movie_club.data.di.detailsScreenRepositoryModule
import com.volokhinaleksey.movie_club.data.di.favoriteScreenRepositoryModule
import com.volokhinaleksey.movie_club.data.di.homeScreenRepositoryModule
import com.volokhinaleksey.movie_club.data.di.searchScreenRepositoryModule
import com.volokhinaleksey.movie_club.domain.ActorInteractor
import com.volokhinaleksey.movie_club.domain.ActorInteractorImpl
import com.volokhinaleksey.movie_club.domain.CategoryInteractor
import com.volokhinaleksey.movie_club.domain.CategoryInteractorImpl
import com.volokhinaleksey.movie_club.domain.DetailsInteractor
import com.volokhinaleksey.movie_club.domain.DetailsInteractorImpl
import com.volokhinaleksey.movie_club.domain.FavoritesInteractor
import com.volokhinaleksey.movie_club.domain.FavoritesInteractorImpl
import com.volokhinaleksey.movie_club.domain.HomeInteractor
import com.volokhinaleksey.movie_club.domain.HomeInteractorImpl
import com.volokhinaleksey.movie_club.domain.LocaleInteractor
import com.volokhinaleksey.movie_club.domain.LocaleInteractorImpl
import com.volokhinaleksey.movie_club.domain.SearchInteractor
import com.volokhinaleksey.movie_club.domain.SearchInteractorImpl
import org.koin.dsl.module

val favoriteScreenDomainModule = module {
    includes(favoriteScreenRepositoryModule)
    factory<FavoritesInteractor> { FavoritesInteractorImpl(get(), get()) }
}

val actorScreenDomainModule = module {
    includes(actorScreenRepositoryModule)
    factory<ActorInteractor> { ActorInteractorImpl(get()) }
}

val detailsScreenDomainModule = module {
    includes(detailsScreenRepositoryModule)
    factory<LocaleInteractor> { LocaleInteractorImpl() }
    factory<DetailsInteractor> { DetailsInteractorImpl(get()) }
}

val homeScreenDomainModule = module {
    includes(homeScreenRepositoryModule)
    factory<HomeInteractor> { HomeInteractorImpl(get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
}

val categoryScreenDomainModule = module {
    includes(categoryScreenRepositoryModule)
    factory<CategoryInteractor> { CategoryInteractorImpl(get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
}

val searchScreenDomainModule = module {
    includes(searchScreenRepositoryModule)
    factory<SearchInteractor> { SearchInteractorImpl(get()) }
}