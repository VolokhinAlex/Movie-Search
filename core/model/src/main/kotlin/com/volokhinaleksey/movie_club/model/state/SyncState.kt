package com.volokhinaleksey.movie_club.model.state

sealed interface SyncState {
    data object Loading : SyncState
    data object Finished : SyncState
}