package com.volokhinaleksey.movie_club.domain

import java.util.Locale

interface LocaleInteractor {
    fun getCurrentLanguage(): String
}

class LocaleInteractorImpl : LocaleInteractor {
    override fun getCurrentLanguage(): String {
        val locale = Locale.getDefault()
        return "${locale.language}-${locale.country}"
    }
}