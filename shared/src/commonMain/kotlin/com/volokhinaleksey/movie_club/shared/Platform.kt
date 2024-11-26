package com.volokhinaleksey.movie_club.shared

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform