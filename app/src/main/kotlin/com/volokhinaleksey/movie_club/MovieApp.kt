package com.volokhinaleksey.movie_club

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.volokhinaleksey.movie_club.actor.di.actorScreenModule
import com.volokhinaleksey.movie_club.details.di.detailsScreenModule
import com.volokhinaleksey.movie_club.favorites.di.favoriteScreenModule
import com.volokhinaleksey.movie_club.home.di.homeScreenModule
import com.volokhinaleksey.movie_club.movie_category.di.categoryScreenModule
import com.volokhinaleksey.movie_club.search.di.searchScreenModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            androidContext(applicationContext)
            modules(
                listOf(
                    homeScreenModule,
                    detailsScreenModule,
                    favoriteScreenModule,
                    searchScreenModule,
                    actorScreenModule,
                    categoryScreenModule
                )
            )
        }
    }
}