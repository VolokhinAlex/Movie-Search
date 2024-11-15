package com.volokhinaleksey.movie_club.app

import android.app.Application
import com.volokhinaleksey.movie_club.di.actorScreen
import com.volokhinaleksey.movie_club.di.categoryScreen
import com.volokhinaleksey.movie_club.di.database
import com.volokhinaleksey.movie_club.di.detailsScreen
import com.volokhinaleksey.movie_club.di.favoriteScreen
import com.volokhinaleksey.movie_club.di.homeScreen
import com.volokhinaleksey.movie_club.di.network
import com.volokhinaleksey.movie_club.di.searchScreen
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