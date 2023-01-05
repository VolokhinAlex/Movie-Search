package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.category_movies.CategoryMoviesFragment
import com.example.java.android1.movie_search.view.compose.home.Category
import com.example.java.android1.movie_search.view.compose.home.CategoryAppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var movieCategories: MutableList<Category> = mutableListOf()
    private val homeViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var moviesCategoriesAdapter: MoviesHomeBasicAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        homeViewModel.homeLiveData.observe(viewLifecycleOwner) { state -> renderData(state) }
        val categoriesQueryList = listOf("popular", "now_playing", "upcoming", "top_rated")
        categoriesQueryList.forEach {
            homeViewModel.getMovieCategory(
                it,
                "ru-RU",
                1
            )
        }
        initBasicCategoriesList()
        return binding.root
    }

    private fun renderData(categoryAppState: CategoryAppState) {
        val emptyData = MovieChildListData(null, null)
        when (categoryAppState) {
            is CategoryAppState.Success -> {
                val movieData = categoryAppState.data
                movieCategories.add(movieData)
                moviesCategoriesAdapter.setParentListData(
                    listOf(
                        MovieChildListData(movieCategories[0].queryName, movieCategories[0].data),
                        if (movieCategories.size >= 2) MovieChildListData(
                            movieCategories[1].queryName,
                            movieCategories[1].data
                        ) else emptyData,
                        if (movieCategories.size >= 3) MovieChildListData(
                            movieCategories[2].queryName,
                            movieCategories[2].data
                        ) else emptyData,
                        if (movieCategories.size >= 4) MovieChildListData(
                            movieCategories[3].queryName,
                            movieCategories[3].data
                        ) else emptyData
                    )
                )
            }
            is CategoryAppState.Error -> {
                val error = categoryAppState.error
            }
            CategoryAppState.Loading -> {}
        }
    }

    private fun initBasicCategoriesList() {
        val basicCategoriesList = binding.containerBasicList
        moviesCategoriesAdapter = MoviesHomeBasicAdapter { categoryName ->
            val bundle = Bundle()
            bundle.putString(CategoryMoviesFragment.ARG_CATEGORY_NAME, categoryName)
            activity?.supportFragmentManager?.replace(
                R.id.container,
                CategoryMoviesFragment.newInstance(bundle)
            )
        }
        basicCategoriesList.adapter = moviesCategoriesAdapter
        basicCategoriesList.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
