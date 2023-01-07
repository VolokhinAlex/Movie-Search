package com.example.java.android1.movie_search.view.without_compose.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.CategoryData
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.repository.HomeRepositoryImpl
import com.example.java.android1.movie_search.repository.RemoteDataSource
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.without_compose.category_movies.CategoryMoviesFragment
import com.example.java.android1.movie_search.app.CategoryAppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel
import com.example.java.android1.movie_search.viewmodel.MainViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var categoryMoviesData: MutableList<CategoryData> = mutableListOf()
    private val homeViewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModelFactory(HomeRepositoryImpl(RemoteDataSource()))
        )[MainViewModel::class.java]
    }

    private lateinit var categoriesMoviesAdapter: MoviesHomeBasicAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        homeViewModel.homeLiveData.observe(viewLifecycleOwner) { state -> renderDataFromRemoteServer(state) }
        val categoriesQueryList = listOf("popular", "now_playing", "upcoming", "top_rated")
        categoriesQueryList.forEach { // This is a bad decision
            homeViewModel.getCategoryMovies(
                it,
                "ru-RU",
                1
            )
        }
        initBasicCategoriesList()
        return binding.root
    }

    private fun renderDataFromRemoteServer(categoryAppState: CategoryAppState) {
        val emptyData = MovieChildListData(null, null)
        when (categoryAppState) {
            is CategoryAppState.Success -> {
                val movieData = categoryAppState.data
                categoryMoviesData.add(movieData)
                categoriesMoviesAdapter.setParentListData(
                    listOf(
                        MovieChildListData(categoryMoviesData[0].queryName, categoryMoviesData[0].data),
                        if (categoryMoviesData.size >= 2) MovieChildListData(
                            categoryMoviesData[1].queryName,
                            categoryMoviesData[1].data
                        ) else emptyData,
                        if (categoryMoviesData.size >= 3) MovieChildListData(
                            categoryMoviesData[2].queryName,
                            categoryMoviesData[2].data
                        ) else emptyData,
                        if (categoryMoviesData.size >= 4) MovieChildListData(
                            categoryMoviesData[3].queryName,
                            categoryMoviesData[3].data
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
        categoriesMoviesAdapter = MoviesHomeBasicAdapter { categoryName ->
            val bundle = Bundle()
            bundle.putString(CategoryMoviesFragment.ARG_CATEGORY_NAMEData, categoryName)
            activity?.supportFragmentManager?.replace(
                R.id.container,
                CategoryMoviesFragment.newInstance(bundle)
            )
        }
        basicCategoriesList.adapter = categoriesMoviesAdapter
        basicCategoriesList.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
