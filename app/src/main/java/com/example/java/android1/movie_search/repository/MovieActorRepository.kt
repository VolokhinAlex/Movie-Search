package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import retrofit2.Callback

/**
 * The remote repository to get the actors of movie from TMDB API
 */

interface MovieActorRepository {

    fun getMovieActorDetailsFromRemoteServer(
        personId: Long,
        language: String,
        callback: Callback<ActorDTO>
    )

}