package com.volokhinaleksey.movie_club.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first

class MovieClubDataStore(
    private val datastore: DataStore<Preferences>,
) {
    suspend fun saveCategoryMoviesRemoteKey(remoteKey: CategoryMoviesRemoteKey) {
        println("saveCategoryMoviesRemoteKey, remoteKey=$remoteKey")
        try {
            datastore.edit { it[intPreferencesKey(remoteKey.category)] = remoteKey.nextKey }
        } catch (e: IOException) {
            println("saveCategoryMoviesRemoteKey, remoteKey=$remoteKey, error=$e")
        }
    }

    suspend fun getCategoryMoviesNextKey(category: String): Int? {
        val value = datastore.data.first()[intPreferencesKey(category)]
        println("getCategoryMoviesNextKey, value=$value")
        return value
    }

}