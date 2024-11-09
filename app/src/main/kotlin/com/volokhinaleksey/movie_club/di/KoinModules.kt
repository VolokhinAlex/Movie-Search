package com.volokhinaleksey.movie_club.di

import androidx.paging.PagingData
import androidx.room.Room
import com.volokhinaleksey.movie_club.datasource.actor.ActorDataSource
import com.volokhinaleksey.movie_club.datasource.actor.LocalActorDataSource
import com.volokhinaleksey.movie_club.datasource.actor.LocalActorDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.actor.RemoteActorDataSource
import com.volokhinaleksey.movie_club.datasource.category.CategoryDataSource
import com.volokhinaleksey.movie_club.datasource.category.LocalCategoryDataSource
import com.volokhinaleksey.movie_club.datasource.category.LocalCategoryDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.category.RemoteCategoryDataSource
import com.volokhinaleksey.movie_club.datasource.details.DetailsDataSource
import com.volokhinaleksey.movie_club.datasource.details.LocalDetailsDataSource
import com.volokhinaleksey.movie_club.datasource.details.LocalDetailsDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.details.RemoteDetailsDataSource
import com.volokhinaleksey.movie_club.datasource.favorite.FavoriteDataSource
import com.volokhinaleksey.movie_club.datasource.favorite.LocalFavoriteDataSource
import com.volokhinaleksey.movie_club.datasource.home.HomeDataSource
import com.volokhinaleksey.movie_club.datasource.home.LocalHomeDataSource
import com.volokhinaleksey.movie_club.datasource.home.LocalHomeDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.home.RemoteHomeDataSource
import com.volokhinaleksey.movie_club.datasource.search.LocalSearchDataSource
import com.volokhinaleksey.movie_club.datasource.search.LocalSearchDataSourceImpl
import com.volokhinaleksey.movie_club.datasource.search.RemoteSearchDataSource
import com.volokhinaleksey.movie_club.datasource.search.SearchDataSource
import com.volokhinaleksey.movie_club.model.local.LocalMovieData
import com.volokhinaleksey.movie_club.model.remote.ActorDTO
import com.volokhinaleksey.movie_club.model.remote.CategoryMoviesTMDB
import com.volokhinaleksey.movie_club.model.remote.MovieDataTMDB
import com.volokhinaleksey.movie_club.network.ApiHolder
import com.volokhinaleksey.movie_club.network.MovieTMBDHolder
import com.volokhinaleksey.movie_club.network.MovieTMDBAPI
import com.volokhinaleksey.movie_club.network.utils.AndroidNetworkStatus
import com.volokhinaleksey.movie_club.network.utils.NetworkStatus
import com.volokhinaleksey.movie_club.repository.actor.MovieActorRepository
import com.volokhinaleksey.movie_club.repository.actor.MovieActorRepositoryImpl
import com.volokhinaleksey.movie_club.repository.category.CategoryRepository
import com.volokhinaleksey.movie_club.repository.category.CategoryRepositoryImpl
import com.volokhinaleksey.movie_club.repository.details.DetailsRepository
import com.volokhinaleksey.movie_club.repository.details.DetailsRepositoryImpl
import com.volokhinaleksey.movie_club.repository.favorite.FavoriteRepository
import com.volokhinaleksey.movie_club.repository.favorite.FavoriteRepositoryImpl
import com.volokhinaleksey.movie_club.repository.home.HomeRepository
import com.volokhinaleksey.movie_club.repository.home.HomeRepositoryImpl
import com.volokhinaleksey.movie_club.repository.search.SearchRepository
import com.volokhinaleksey.movie_club.repository.search.SearchRepositoryImpl
import com.volokhinaleksey.movie_club.room.MoviesDataBase
import com.volokhinaleksey.movie_club.viewmodel.CategoryMoviesViewModel
import com.volokhinaleksey.movie_club.viewmodel.DetailsViewModel
import com.volokhinaleksey.movie_club.viewmodel.FavoriteViewModel
import com.volokhinaleksey.movie_club.viewmodel.MainViewModel
import com.volokhinaleksey.movie_club.viewmodel.MovieActorViewModel
import com.volokhinaleksey.movie_club.viewmodel.SearchViewModel
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
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

    single<ApiHolder> { MovieTMBDHolder(get()) }

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
    factory<HomeDataSource<CategoryMoviesTMDB>> { RemoteHomeDataSource(get()) }
    factory<LocalHomeDataSource> { LocalHomeDataSourceImpl(get()) }
    factory<HomeRepository> { HomeRepositoryImpl(get(), get()) }
    viewModel { MainViewModel(get()) }
}

val detailsScreen = module {
    factory<LocalDetailsDataSource> { LocalDetailsDataSourceImpl(get()) }
    factory<DetailsDataSource<MovieDataTMDB>> { RemoteDetailsDataSource(get()) }
    factory<DetailsRepository> { DetailsRepositoryImpl(get(), get()) }
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
    factory<LocalSearchDataSource> { LocalSearchDataSourceImpl(get()) }
    factory<SearchDataSource<PagingData<MovieDataTMDB>>> { RemoteSearchDataSource(get()) }
    factory<SearchRepository> { SearchRepositoryImpl(get(), get()) }
    viewModel { SearchViewModel(get()) }
}

val categoryScreen = module {
    factory<LocalCategoryDataSource> { LocalCategoryDataSourceImpl(get()) }
    factory<CategoryDataSource<PagingData<MovieDataTMDB>>> { RemoteCategoryDataSource(get()) }
    factory<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    viewModel { CategoryMoviesViewModel(get()) }
}