package com.volokhinaleksey.movie_club.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.volokhinaleksey.movie_club.actor.viewmodel.ActorsViewModel
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
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import com.volokhinaleksey.movie_club.datastore.MovieClubDataStore
import com.volokhinaleksey.movie_club.details.viewmodel.DetailsViewModel
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
import com.volokhinaleksey.movie_club.favorites.viewmodel.FavoritesViewModel
import com.volokhinaleksey.movie_club.home.viewmodel.HomeViewModel
import com.volokhinaleksey.movie_club.movie_category.viewmodel.CategoryMoviesViewModel
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import com.volokhinaleksey.movie_club.moviesapi.MovieTMBDCore
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPI
import com.volokhinaleksey.movie_club.search.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DB_NAME = "movie_club.db"
private const val UserPreferences = "user_preferences.preferences_pb"

val database = module {
    single {
        Room
            .databaseBuilder(get(), MovieDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
}

val datastore = module {
    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = emptyList(),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidContext().dataStoreFile(UserPreferences) }
        )
    }
    single<MovieClubDataStore> { MovieClubDataStore(get()) }
}

val network = module {
    /**
     * Providing a dependency for ApiHolder
     */

    single<CoreApi> { MovieTMBDCore(get()) }

    /**
     * Providing a dependency for the gson converter
     */

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    /**
     * Providing a dependency for OkHttpClient with HttpLoggingInterceptor
     */

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    /**
     * Providing a dependency to work with retrofit
     */

    single {
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
            .create(MovieTMDBAPI::class.java)
    }
}

val homeScreen = module {
    factory<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    factory<HomeInteractor> { HomeInteractorImpl(get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
    viewModel { HomeViewModel(get(), get()) }
}

val detailsScreen = module {
    factory<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
    factory<DetailsInteractor> { DetailsInteractorImpl(get()) }
    viewModel { DetailsViewModel(get(), get()) }
}

val favoriteScreen = module {
    factory<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    factory<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    factory<FavoritesInteractor> { FavoritesInteractorImpl(get(), get()) }
    viewModel { FavoritesViewModel(get()) }
}

val actorScreen = module {
    factory<ActorRepository> { ActorRepositoryImpl(get(), get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
    factory<ActorInteractor> { ActorInteractorImpl(get()) }
    viewModel { ActorsViewModel(get(), get()) }
}

val searchScreen = module {
    factory<SearchRepository> { SearchRepositoryImpl(get()) }
    factory<SearchInteractor> { SearchInteractorImpl(get()) }
    viewModel { SearchViewModel(get()) }
}

val categoryScreen = module {
    factory<CategoryRepository> { CategoryRepositoryImpl(get(), get(), get()) }
    factory<CategoryInteractor> { CategoryInteractorImpl(get()) }
    factory<LocaleInteractor> { LocaleInteractorImpl() }
    viewModel { CategoryMoviesViewModel(get(), get()) }
}