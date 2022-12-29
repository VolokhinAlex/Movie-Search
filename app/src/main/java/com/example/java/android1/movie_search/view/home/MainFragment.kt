package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.view.compose.home.CategoriesData
import com.example.java.android1.movie_search.view.compose.home.Category
import com.example.java.android1.movie_search.view.compose.home.CategoryAppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var categories: MutableList<Category> = mutableListOf()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var moviesCategoryAdapter: MoviesHomeBasicAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.homeLiveData.observe(viewLifecycleOwner) { state -> renderData(state) }
        val categoriesList: List<CategoriesData> =
            listOf(
                CategoriesData("Popular", "popular"),
                CategoriesData("Now Playing", "now_playing"),
                CategoriesData("Upcoming", "upcoming"),
                CategoriesData("Top Rated", "top_rated")
            )
        categoriesList.forEach {
            viewModel.getCategoryMovies(
                it.queryName,
                "ru-RU",
                1
            )
        }
        initRecyclerViews()
        return binding.root
    }

    private fun renderData(categoryAppState: CategoryAppState) {
        val emptyList = MovieChildListData(null, null)
        when (categoryAppState) {
            is CategoryAppState.Success -> {
                val movieData = categoryAppState.data
                categories.add(movieData)
                moviesCategoryAdapter.setParentListData(
                    listOf(
                        MovieChildListData(categories[0].queryName, categories[0].data),
                        if (categories.size >= 2) MovieChildListData(categories[1].queryName, categories[1].data) else emptyList,
                        if (categories.size >= 3) MovieChildListData(categories[2].queryName, categories[2].data) else emptyList,
                        if (categories.size >= 4) MovieChildListData(categories[3].queryName, categories[3].data) else emptyList)
                )
            }
            is CategoryAppState.Error -> {
                val error = categoryAppState.error
                Log.e("APP_STATE_ERROR", error.message ?: "Error get data")
            }
            CategoryAppState.Loading -> {}
        }
    }

    private fun initRecyclerViews() {
        val basicList = binding.containerBasicList
        moviesCategoryAdapter = MoviesHomeBasicAdapter()
        basicList.adapter = moviesCategoryAdapter
        basicList.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
