package com.example.java.android1.movie_search.view.category_movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.CategoryRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.compose.theme.PrimaryColor80
import com.example.java.android1.movie_search.view.compose.widgets.MovieCard
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModel
import com.example.java.android1.movie_search.viewmodel.CategoryMoviesViewModelFactory

class CategoryMoviesFragment : Fragment() {
    private var categoryName: String? = null

    private val categoryMoviesViewModel: CategoryMoviesViewModel by lazy {
        ViewModelProvider(
            this,
            CategoryMoviesViewModelFactory(CategoryRepositoryImpl(RemoteDataSource()))
        )[CategoryMoviesViewModel::class.java]
    }

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
            categoryName?.let { categoryName ->
                val categoryMovies =
                    categoryMoviesViewModel.getCategoryMoviesFromRemoteServer(categoryName)
                        .collectAsLazyPagingItems()
                Box(modifier = Modifier.background(PrimaryColor80)) {
                    ShowCategoryMovies(categoryMovies)
                }
            }
        }
    }

    @Composable
    private fun ShowCategoryMovies(movieDataTMDB: LazyPagingItems<MovieDataTMDB>) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(movieDataTMDB.itemCount) { item ->
                movieDataTMDB[item]?.let {
                    MovieCard(
                        modifier = Modifier
                            .size(width = 160.dp, height = 350.dp)
                            .padding(bottom = 10.dp)
                            .clickable {
                                val bundle = Bundle()
                                bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, it)
                                activity?.supportFragmentManager?.replace(
                                    R.id.container,
                                    MovieDetailsFragment.newInstance(bundle)
                                )
                            },
                        movieDataTMDB = it
                    )
                }
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