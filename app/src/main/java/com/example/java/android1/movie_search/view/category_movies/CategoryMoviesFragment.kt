package com.example.java.android1.movie_search.view.category_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.ErrorMessage
import com.example.java.android1.movie_search.view.compose.widgets.Loader
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModel

class CategoryMoviesFragment : Fragment() {
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            categoryName = it.getString(ARG_CATEGORY_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setContent {
            val categoryMoviesViewModel by lazy {
                mutableStateOf(CategoryMoviesViewModel())
            }
            LaunchedEffect(Unit) {
                categoryName?.let { name ->
                    categoryMoviesViewModel.value.getCategoryMoviesFromRemoteServer(
                        name,
                        "ru-RU",
                        2
                    )
                }
            }
            Box(modifier = Modifier.background(PrimaryColor80)) {
                categoryMoviesViewModel.value.categoryMoviesLiveData.observeAsState().value?.let { state ->
                    RenderData(state)
                }
            }
        }
    }

    @Composable
    private fun RenderData(movieAppState: MovieAppState) {
        when (movieAppState) {
            is MovieAppState.Error -> ErrorMessage(message = movieAppState.error.message.toString())
            MovieAppState.Loading -> Loader()
            is MovieAppState.Success -> {
                val categoryMoviesList = movieAppState.data
                ShowCategoryMovies(categoryMoviesList)
            }
        }
    }

    @Composable
    private fun ShowCategoryMovies(movieDataTMDB: List<MovieDataTMDB>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(movieDataTMDB) { _, item ->
                MovieCard(
                    modifier = Modifier
                        .size(width = 160.dp, height = 350.dp)
                        .padding(bottom = 10.dp).clickable {
                            val bundle = Bundle()
                            bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, item)
                            activity?.supportFragmentManager?.replace(
                                R.id.container,
                                MovieDetailsFragment.newInstance(bundle)
                            )
                        },
                    movieDataTMDB = item
                )
            }
        }
    }

    companion object {
        const val ARG_CATEGORY_NAME = "Category Name"

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            CategoryMoviesFragment().apply {
                arguments = bundle
            }
    }
}