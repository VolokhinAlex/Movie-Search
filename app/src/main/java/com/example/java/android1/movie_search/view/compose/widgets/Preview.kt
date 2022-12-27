package com.example.java.android1.movie_search.view.compose.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.java.android1.movie_search.model.*
import com.example.java.android1.movie_search.view.compose.details.DetailsScreen
import com.example.java.android1.movie_search.view.compose.home.HomeScreen
import com.example.java.android1.movie_search.view.compose.home.MainViewModelCompose

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen()
//    DetailsScreen(
//        movieDataTMDB = MovieDataTMDB(
//            adult = false,
//            backdrop_path = "/5GbkL9DDRzq3A21nR7Gkv6cFGjq.jpg",
//            poster_path = "/uHpHzbHLSsVmAuuGuQSpyVDZmDc.jpg",
//            budget = null,
//            id = 556678,
//            imdb_id = "tt9214832",
//            genres = listOf(GenresDTO(0, "Comedy"), GenresDTO(0, "Fantasy")),
//            original_language = null,
//            overview = "Англия, XIX век. 21-летняя провинциалка Эмма Вудхаус красива, богата, остроумна и считает, что прекрасно разбирается в людях. Девушка решила, что никогда не выйдет замуж и не оставит отца одного. Когда её подруга в связи с собственным замужеством переезжает в дом супруга, Эмма находит себе новую компаньонку - сироту Гарриет Смит - и теперь, используя все свои хитрости, пытается устроить девушке личную жизнь.",
//            title = "Emma",
//            vote_count = null,
//            vote_average = 8.5,
//            release_date = "2022-12-12",
//            production_countries = null,
//            runtime = 96,
//            credits = CreditsDTO(
//                listOf(
//                    CastDTO(
//                        "Marlon Brando",
//                        "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg",
//                        "Don Vito Corleone"
//                    ),
//                    CastDTO(
//                        "Marlon Brando",
//                        "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg",
//                        "Don Vito Corleone"
//                    ),
//                    CastDTO(
//                        "Marlon Brando",
//                        "/fuTEPMsBtV1zE98ujPONbKiYDc2.jpg",
//                        "Don Vito Corleone"
//                    )
//                )
//            ),
//            videos = Videos(Trailer(null, "_E1IfP4PN5Y", null, null))
//        )
//    )
    //SearchScreen()
//    Loader()
//    ErrorMessage("Not found Data")
//    MovieCard(
//        MovieDataTMDB(
//            adult = false,
//            backdrop_path = null,
//            poster_path = "/uHpHzbHLSsVmAuuGuQSpyVDZmDc.jpg",
//            budget = null,
//            id = null,
//            imdb_id = null,
//            genres = null,
//            original_language = null,
//            overview = null,
//            title = "Emma",
//            vote_count = null,
//            vote_average = 8.5,
//            release_date = "2022-12-12",
//            production_countries = null,
//            runtime = null,
//            credits = CreditsDTO(
//                listOf()
//            )
//        )
//    )
}