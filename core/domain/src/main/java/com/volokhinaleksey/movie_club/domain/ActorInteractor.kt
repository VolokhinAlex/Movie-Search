package com.volokhinaleksey.movie_club.domain

import com.volokhinaleksey.movie_club.data.repository.ActorRepository
import com.volokhinaleksey.movie_club.model.state.ActorDetailsState

interface ActorInteractor {
    suspend fun getActorDetails(actorId: Long): ActorDetailsState
    suspend fun syncActorDetails(actorId: Long, lang: String)
}

class ActorInteractorImpl(
    private val actorRepository: ActorRepository,
) : ActorInteractor {

    override suspend fun getActorDetails(actorId: Long): ActorDetailsState {
        return try {
            ActorDetailsState.Success(actorRepository.getActorDetails(actorId))
        } catch (e: Exception) {
            ActorDetailsState.Error(e.localizedMessage.orEmpty())
        }
    }

    override suspend fun syncActorDetails(actorId: Long, lang: String) {
        try {
            actorRepository.syncActorDetails(actorId, lang)
        } catch (e: Exception) {
            println("ActorInteractorImpl, syncActorDetails, actorId=$actorId, error=$e")
        }
    }

}