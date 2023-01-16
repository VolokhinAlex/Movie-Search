package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.viewmodel.AppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private var categories: MutableList<List<MovieDataTMDB>> = mutableListOf()
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var mAdapter: MoviesHomeBasicAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.homeLiveData.observe(viewLifecycleOwner) { state -> renderData(state) }
        viewModel.getCategoriesMovies("ru-RU", 1)
        initRecyclerViews()
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        val emptyList = MovieChildListData(null, null)
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                categories.add(movieData)
                mAdapter.setParentListData(
                    listOf(
                        MovieChildListData(getString(R.string.category_upcoming), categories[0]),
                        if (categories.size >= 2) MovieChildListData(getString(R.string.category_popular), categories[1]) else emptyList,
                        if (categories.size >= 3) MovieChildListData(getString(R.string.category_now_playing), categories[2]) else emptyList,
                        if (categories.size >= 4) MovieChildListData(getString(R.string.category_top_rated_movies), categories[3]) else emptyList,
                    )
                )
            }
            is AppState.Error -> {
                val error = appState.error
                Log.e("APP_STATE_ERROR", error.message ?: "Error get data")
            }
            AppState.Loading -> {}
        }
    }

    private fun initRecyclerViews() {
        val basicList = mBinding.containerBasicList
        mAdapter = MoviesHomeBasicAdapter()
        basicList.adapter = mAdapter
        basicList.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
