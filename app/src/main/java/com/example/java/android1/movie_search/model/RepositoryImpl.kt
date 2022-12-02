package com.example.java.android1.movie_search.model

class RepositoryImpl : Repository {

    override fun getMovieFromServer(): MovieData {
        TODO("Not yet implemented")
    }

    override fun getMovieFromLocalStorage(): MovieData {
        return MovieData(
            id = 1,
            imdbId = "tt0137523",
            title = "Эмма",
            overview = "Англия, XIX век. " +
                    "21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. " +
                    "Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, " +
                    "Эмма находит себе новую компаньонку — сироту Гарриет Смит — и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
            backdropPath = "2131165395",
            actors = listOf("Аня Тейлор-Джой\n" +
                    "Джонни Флинн\n" +
                    "Миа Гот\n" +
                    "Джош О’Коннор\n" +
                    "Билл Найи\n" +
                    "Каллум Тернер\n" +
                    "Миранда Харт\n" +
                    "Эмбер Андерсон\n" +
                    "Руперт Грейвз\n" +
                    "Джемма Уилан"),
            genres = listOf("комедия", "мелодрама"),
            country = "Великобритания",
            releaseDate = "2020",
            rationOfMovie = 6.9,
            voteCount = 20000,
            age = 12,
            adult = true,
            originalLanguage = "en"
        )
    }

}