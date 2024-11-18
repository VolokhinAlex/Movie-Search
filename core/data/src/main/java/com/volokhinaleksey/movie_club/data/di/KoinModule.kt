package com.volokhinaleksey.movie_club.data.di

import com.volokhinaleksey.movie_club.data.repository.ActorRepository
import com.volokhinaleksey.movie_club.data.repository.ActorRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.CategoryRepository
import com.volokhinaleksey.movie_club.data.repository.CategoryRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.DetailsRepository
import com.volokhinaleksey.movie_club.data.repository.DetailsRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.FavoritesRepository
import com.volokhinaleksey.movie_club.data.repository.FavoritesRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.HomeRepository
import com.volokhinaleksey.movie_club.data.repository.HomeRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.SearchRepository
import com.volokhinaleksey.movie_club.data.repository.SearchRepositoryImpl
import com.volokhinaleksey.movie_club.database.room.di.database
import com.volokhinaleksey.movie_club.datastore.di.datastore
import com.volokhinaleksey.movie_club.moviesapi.di.network
import org.koin.dsl.module

internal val commonModule = module {
    includes(
        database,
        datastore,
        network
    )
}

val favoriteScreenRepositoryModule = module {
    includes(commonModule)
    factory<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    factory<HomeRepository> { HomeRepositoryImpl(get(), get()) }
}

val actorScreenRepositoryModule = module {
    includes(commonModule)
    factory<ActorRepository> { ActorRepositoryImpl(get(), get()) }
}

val detailsScreenRepositoryModule = module {
    includes(commonModule)
    factory<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
}

val homeScreenRepositoryModule = module {
    includes(commonModule)
    factory<HomeRepository> { HomeRepositoryImpl(get(), get()) }
}

val categoryScreenRepositoryModule = module {
    includes(commonModule)
    factory<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
}

val searchScreenRepositoryModule = module {
    includes(commonModule)
    factory<SearchRepository> { SearchRepositoryImpl(get()) }
}