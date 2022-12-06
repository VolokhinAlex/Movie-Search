package com.example.java.android1.movie_search.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * The class is responsible for storing information about the movie.
 * @param id -               ID of the movie in the database
 * @param imdbId -           ID of the movie in the imdb database
 * @param title -            Title of movie
 * @param overview -         Short description of the movie
 * @param backdropPath -     Image of movie
 * @param actors -           The main characters of the movie
 * @param genres -           Genres of movie
 * @param country -          The country where the movie was shot
 * @param releaseDate -      Premiere date of the movie
 * @param ratingOfMovie -    Popularity ball (0-10) according to IMDB
 * @param voteCount -        Number of users who voted
 * @param age -              Acceptable age for watch the movie
 * @param adult -            Adult movie
 * @param originalLanguage - The original language of the movie
 */

@Parcelize
data class MovieData(
    val id: Int,
    val imdbId: String,
    val title: String,
    val overview: String,
    val backdropPath: String,
    val actors: List<ActorData>,
    val genres: List<String>,
    val country: String,
    val releaseDate: String,
    val ratingOfMovie: Double,
    val voteCount: Int,
    val age: Int,
    val adult: Boolean = false,
    val originalLanguage: String,
) : Parcelable

fun getListOfMoviesFromLocalStorage(): List<MovieData> = listOf(
    MovieData(
        id = 1,
        imdbId = "tt0137523",
        title = "Эмма",
        overview = "Англия, XIX век. " +
                "21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. " +
                "Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, " +
                "Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
        backdropPath = "2131165395",
        actors = listOf(
            ActorData("Аня \nТейлор-Джой", ""),
            ActorData("Джонни \nФлинн", ""),
            ActorData("Миа \nГот", ""),
            ActorData("Билл \nНайи", ""),
            ActorData("Каллум \nТернер", ""),
            ActorData("Миранда \nХарт", ""),
            ActorData("Эмбер \nАндерсон", ""),
            ActorData("Руперт \nГрейвз", ""),
            ActorData("Джемма \nУилан", ""),
        ),
        genres = listOf("комедия", "мелодрама"),
        country = "Великобритания",
        releaseDate = "2020",
        ratingOfMovie = 6.9,
        voteCount = 20000,
        age = 12,
        adult = true,
        originalLanguage = "en"
    ),
    MovieData(
        id = 1,
        imdbId = "tt0137523",
        title = "Эмма",
        overview = "Англия, XIX век. " +
                "21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. " +
                "Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, " +
                "Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
        backdropPath = "2131165395",
        actors = listOf(
            ActorData("Аня \nТейлор-Джой", ""),
            ActorData("Джонни \nФлинн", ""),
            ActorData("Миа \nГот", ""),
            ActorData("Билл \nНайи", ""),
            ActorData("Каллум \nТернер", ""),
            ActorData("Миранда \nХарт", ""),
            ActorData("Эмбер \nАндерсон", ""),
            ActorData("Руперт \nГрейвз", ""),
            ActorData("Джемма \nУилан", ""),
        ),
        genres = listOf("комедия", "мелодрама"),
        country = "Великобритания",
        releaseDate = "2020",
        ratingOfMovie = 6.9,
        voteCount = 20000,
        age = 12,
        adult = true,
        originalLanguage = "en"
    ),
    MovieData(
        id = 1,
        imdbId = "tt0137523",
        title = "Эмма",
        overview = "Англия, XIX век. " +
                "21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. " +
                "Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, " +
                "Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
        backdropPath = "2131165395",
        actors = listOf(
            ActorData("Аня \nТейлор-Джой", ""),
            ActorData("Джонни \nФлинн", ""),
            ActorData("Миа \nГот", ""),
            ActorData("Билл \nНайи", ""),
            ActorData("Каллум \nТернер", ""),
            ActorData("Миранда \nХарт", ""),
            ActorData("Эмбер \nАндерсон", ""),
            ActorData("Руперт \nГрейвз", ""),
            ActorData("Джемма \nУилан", ""),
        ),
        genres = listOf("комедия", "мелодрама"),
        country = "Великобритания",
        releaseDate = "2020",
        ratingOfMovie = 6.9,
        voteCount = 20000,
        age = 12,
        adult = true,
        originalLanguage = "en"
    ),
    MovieData(
        id = 1,
        imdbId = "tt0137523",
        title = "Эмма",
        overview = "Англия, XIX век. " +
                "21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. " +
                "Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, " +
                "Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
        backdropPath = "2131165395",
        actors = listOf(
            ActorData("Аня \nТейлор-Джой", ""),
            ActorData("Джонни \nФлинн", ""),
            ActorData("Миа \nГот", ""),
            ActorData("Билл \nНайи", ""),
            ActorData("Каллум \nТернер", ""),
            ActorData("Миранда \nХарт", ""),
            ActorData("Эмбер \nАндерсон", ""),
            ActorData("Руперт \nГрейвз", ""),
            ActorData("Джемма \nУилан", ""),
        ),
        genres = listOf("комедия", "мелодрама"),
        country = "Великобритания",
        releaseDate = "2020",
        ratingOfMovie = 6.9,
        voteCount = 20000,
        age = 12,
        adult = true,
        originalLanguage = "en"
    )
)