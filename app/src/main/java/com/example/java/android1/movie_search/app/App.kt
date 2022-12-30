package com.example.java.android1.movie_search.app

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.java.android1.movie_search.room.MoviesDataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {

        var appInstance: App? = null
        private const val DB_NAME = "MovieSearch.db"

        val movieDao by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                MoviesDataBase::class.java,
                DB_NAME
            )
                .build()
                .moviesDao()
        }

        val historySearchDao by lazy {
            Room.databaseBuilder(
                appInstance!!.applicationContext,
                MoviesDataBase::class.java,
                DB_NAME
            )
                .build()
                .historySearchDao()
        }
    }

}