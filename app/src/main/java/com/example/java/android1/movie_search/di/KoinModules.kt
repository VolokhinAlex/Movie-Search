package com.example.java.android1.movie_search.di

import androidx.paging.PagingData
import androidx.room.Room
import com.example.java.android1.movie_search.datasource.actor.ActorDataSource
import com.example.java.android1.movie_search.datasource.actor.RemoteActorDataSource
import com.example.java.android1.movie_search.datasource.category.CategoryDataSource
import com.example.java.android1.movie_search.datasource.category.LocalCategoryDataSource
import com.example.java.android1.movie_search.datasource.category.LocalCategoryDataSourceImpl
import com.example.java.android1.movie_search.datasource.category.RemoteCategoryDataSource
import com.example.java.android1.movie_search.datasource.details.DetailsDataSource
import com.example.java.android1.movie_search.datasource.details.LocalDetailsDataSource
import com.example.java.android1.movie_search.datasource.details.LocalDetailsDataSourceImpl
import com.example.java.android1.movie_search.datasource.details.RemoteDetailsDataSource
import com.example.java.android1.movie_search.datasource.favorite.FavoriteDataSource
import com.example.java.android1.movie_search.datasource.favorite.LocalFavoriteDataSource
import com.example.java.android1.movie_search.datasource.home.HomeDataSource
import com.example.java.android1.movie_search.datasource.home.LocalHomeDataSource
import com.example.java.android1.movie_search.datasource.home.LocalHomeDataSourceImpl
import com.example.java.android1.movie_search.datasource.home.RemoteHomeDataSource
import com.example.java.android1.movie_search.datasource.search.LocalSearchDataSource
import com.example.java.android1.movie_search.datasource.search.LocalSearchDataSourceImpl
import com.example.java.android1.movie_search.datasource.search.RemoteSearchDataSource
import com.example.java.android1.movie_search.datasource.search.SearchDataSource
import com.example.java.android1.movie_search.model.local.LocalMovieData
import com.example.java.android1.movie_search.model.old.remote.ActorDTO
import com.example.java.android1.movie_search.model.old.remote.CategoryMoviesTMDB
import com.example.java.android1.movie_search.model.old.remote.MovieDataTMDB
import com.example.java.android1.movie_search.network.ApiHolder
import com.example.java.android1.movie_search.network.MovieTMBDHolder
import com.example.java.android1.movie_search.network.MovieTMDBAPI
import com.example.java.android1.movie_search.network.utils.AndroidNetworkStatus
import com.example.java.android1.movie_search.network.utils.NetworkStatus
import com.example.java.android1.movie_search.repository.actor.MovieActorRepository
import com.example.java.android1.movie_search.repository.actor.MovieActorRepositoryImpl
import com.example.java.android1.movie_search.repository.category.CategoryRepository
import com.example.java.android1.movie_search.repository.category.CategoryRepositoryImpl
import com.example.java.android1.movie_search.repository.details.DetailsRepository
import com.example.java.android1.movie_search.repository.details.DetailsRepositoryImpl
import com.example.java.android1.movie_search.repository.favorite.FavoriteRepository
import com.example.java.android1.movie_search.repository.favorite.FavoriteRepositoryImpl
import com.example.java.android1.movie_search.repository.home.HomeRepository
import com.example.java.android1.movie_search.repository.home.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.search.SearchRepository
import com.example.java.android1.movie_search.repository.search.SearchRepositoryImpl
import com.example.java.android1.movie_search.room.MoviesDataBase
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModel
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel
import com.example.java.android1.movie_search.viewmodel.MainViewModel
import com.example.java.android1.movie_search.viewmodel.MovieActorViewModel
import com.example.java.android1.movie_search.viewmodel.SearchViewModel
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
    factory<ActorDataSource<ActorDTO>> { RemoteActorDataSource(get()) }
    factory<MovieActorRepository> { MovieActorRepositoryImpl(get()) }
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