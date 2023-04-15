package com.example.java.android1.movie_search.app

import android.app.Application
import com.example.java.android1.movie_search.di.actorScreen
import com.example.java.android1.movie_search.di.categoryScreen
import com.example.java.android1.movie_search.di.database
import com.example.java.android1.movie_search.di.detailsScreen
import com.example.java.android1.movie_search.di.favoriteScreen
import com.example.java.android1.movie_search.di.homeScreen
import com.example.java.android1.movie_search.di.network
import com.example.java.android1.movie_search.di.searchScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    database,
                    network,
                    homeScreen,
                    detailsScreen,
                    favoriteScreen,
                    searchScreen,
                    actorScreen,
                    categoryScreen
                )
            )
        }
    }
}