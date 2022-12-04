package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieData
import com.example.java.android1.movie_search.viewmodel.AppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.getLiveData().observe(viewLifecycleOwner) { state -> renderData(state) }
        viewModel.getMovieFromLocalSource()
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                setData(data = movieData)
            }
            is AppState.Error -> {
                val error = appState.error
            }
            AppState.Loading -> {

            }
        }
    }

    private fun setData(data: List<MovieData>) {
        val listOfTrendyMovies: RecyclerView = mBinding.containerForTrendyMovies
        val listOfNewMovies: RecyclerView = mBinding.containerForNewMovies
        val movieAdapter = MoviesHomePageAdapter(data)
        listOfTrendyMovies.adapter = movieAdapter
        listOfNewMovies.adapter = movieAdapter
        val trendyMoviesLayoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)
        val newMoviesLayoutManager = LinearLayoutManager(requireActivity(),
            LinearLayoutManager.HORIZONTAL, false)
        listOfTrendyMovies.layoutManager = trendyMoviesLayoutManager
        listOfNewMovies.layoutManager = newMoviesLayoutManager
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
