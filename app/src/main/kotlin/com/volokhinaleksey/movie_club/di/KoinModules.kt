package com.volokhinaleksey.movie_club.di

import androidx.paging.PagingData
import androidx.room.Room
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.volokhinaleksey.movie_club.data.repository.DetailsApiRepository
import com.volokhinaleksey.movie_club.data.repository.DetailsDatabaseRepository
import com.volokhinaleksey.movie_club.data.repository.DetailsDatabaseRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.DetailsRepository
import com.volokhinaleksey.movie_club.data.repository.SearchDatabaseRepository
import com.volokhinaleksey.movie_club.data.repository.SearchDatabaseRepositoryImpl
import com.volokhinaleksey.movie_club.data.repository.SearchRepository
import com.volokhinaleksey.movie_club.data.repository.SearchRepositoryImpl
import com.volokhinaleksey.movie_club.database.room.MoviesDataBase
import com.volokhinaleksey.movie_club.datasource.actor.ActorDataSource
import com.volokhinaleksey.movie_club.datasource.actor.LocalActorDataSource
import com.volokhinaleksey.movie_club.datasource.actor.LocalActorDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.actor.RemoteActorDataSource
import com.volokhinaleksey.movie_club.datasource.category.CategoryDataSource
import com.volokhinaleksey.movie_club.datasource.category.LocalCategoryDataSource
import com.volokhinaleksey.movie_club.datasource.category.LocalCategoryDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.category.RemoteCategoryDataSource
import com.volokhinaleksey.movie_club.datasource.favorite.FavoriteDataSource
import com.volokhinaleksey.movie_club.datasource.favorite.LocalFavoriteDataSource
import com.volokhinaleksey.movie_club.details.viewmodel.DetailsViewModel
import com.volokhinaleksey.movie_club.domain.DetailsInteractor
import com.volokhinaleksey.movie_club.domain.DetailsInteractorImpl
import com.volokhinaleksey.movie_club.domain.HomeInteractor
import com.volokhinaleksey.movie_club.domain.HomeInteractorImpl
import com.volokhinaleksey.movie_club.domain.SearchInteractor
import com.volokhinaleksey.movie_club.domain.SearchInteractorImpl
import com.volokhinaleksey.movie_club.home.viewmodel.HomeViewModel
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.moviesapi.CoreApi
import com.volokhinaleksey.movie_club.moviesapi.MovieTMBDCore
import com.volokhinaleksey.movie_club.moviesapi.MovieTMDBAPI
import com.volokhinaleksey.movie_club.network.utils.AndroidNetworkStatus
import com.volokhinaleksey.movie_club.network.utils.NetworkStatus
import com.volokhinaleksey.movie_club.repository.actor.MovieActorRepository
import com.volokhinaleksey.movie_club.repository.actor.MovieActorRepositoryImpl
import com.volokhinaleksey.movie_club.repository.category.CategoryRepository
import com.volokhinaleksey.movie_club.repository.category.CategoryRepositoryImpl
import com.volokhinaleksey.movie_club.repository.favorite.FavoriteRepository
import com.volokhinaleksey.movie_club.repository.favorite.FavoriteRepositoryImpl
import com.volokhinaleksey.movie_club.search.viewmodel.SearchViewModel
import com.volokhinaleksey.movie_club.viewmodel.CategoryMoviesViewModel
import com.volokhinaleksey.movie_club.viewmodel.FavoriteViewModel
import com.volokhinaleksey.movie_club.viewmodel.MovieActorViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val DB_NAME = "MovieSearch.db"

val database = module {
    single {
        Room
            .databaseBuilder(get(), MoviesDataBase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }
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

    single<NetworkStatus> {
        AndroidNetworkStatus(get())
    }
}

val homeScreen = module {
    factory<com.volokhinaleksey.movie_club.data.repository.HomeRepository> {
        com.volokhinaleksey.movie_club.data.repository.HomeApiRepository(
            get()
        )
    }
    factory<com.volokhinaleksey.movie_club.data.repository.HomeDatabaseRepository> {
        com.volokhinaleksey.movie_club.data.repository.HomeDatabaseRepositoryImpl(
            get()
        )
    }
    factory<HomeInteractor> { HomeInteractorImpl(get(), get()) }
    viewModel { HomeViewModel(get()) }
}

val detailsScreen = module {
    factory<DetailsDatabaseRepository> { DetailsDatabaseRepositoryImpl(get()) }
    factory<DetailsRepository> { DetailsApiRepository(get()) }
    factory<DetailsInteractor> { DetailsInteractorImpl(get(), get()) }
    viewModel { DetailsViewModel(get()) }
}

val favoriteScreen = module {
    factory<FavoriteDataSource<LocalMovieData>> { LocalFavoriteDataSource(get()) }
    factory<FavoriteRepository> { FavoriteRepositoryImpl(get()) }
    viewModel { FavoriteViewModel(get()) }
}

val actorScreen = module {
    factory<LocalActorDataSource> { LocalActorDataSourceImpl(get()) }
    factory<ActorDataSource<ActorDTO>> { RemoteActorDataSource(get()) }
    factory<MovieActorRepository> { MovieActorRepositoryImpl(get(), get()) }
    viewModel { MovieActorViewModel(get()) }
}

val searchScreen = module {
    factory<SearchRepository> { SearchRepositoryImpl(get()) }
    factory<SearchDatabaseRepository> { SearchDatabaseRepositoryImpl(get()) }
    factory<SearchInteractor> { SearchInteractorImpl(get(), get()) }
    viewModel { SearchViewModel(get()) }
}

val categoryScreen = module {
    factory<LocalCategoryDataSource> { LocalCategoryDataSourceImpl(get()) }
    factory<CategoryDataSource<PagingData<MovieDataTMDB>>> { RemoteCategoryDataSource(get()) }
    factory<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    viewModel { CategoryMoviesViewModel(get()) }
}