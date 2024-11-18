package com.volokhinaleksey.movie_club.database.room.di

import androidx.room.Room
import com.volokhinaleksey.movie_club.database.room.MovieDataBase
import org.koin.dsl.module

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
