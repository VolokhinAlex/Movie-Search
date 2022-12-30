package com.example.java.android1.movie_search.repository

import com.example.java.android1.movie_search.model.ActorDTO
import retrofit2.Callback

interface MovieActorRepository {

    fun getMovieActor(
        personId: Long,
        language: String,
        callback: Callback<ActorDTO>
    )

}