package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieData
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
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

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                setData(data = movieData)
            }
            is AppState.Error -> {
                val error = appState.error
                android.util.Log.e("APP_STATE_ERROR", error.message ?: "Error get data")
            }
            AppState.Loading -> {

            }
        }
    }

    private fun setData(data: List<MovieData>) {
        val listOfTrendyMovies: RecyclerView = mBinding.containerForTrendyMovies
        val listOfNewMovies: RecyclerView = mBinding.containerForNewMovies
        val listOfTheBestMovies: RecyclerView = mBinding.containerForTheBestMovies
        val listOfComingSoonInCinemas: RecyclerView = mBinding.containerForComingSoonInCinemas
        val listOfHolidayMovies: RecyclerView = mBinding.containerForHolidayMovies
        val listOfFamilyMovies: RecyclerView = mBinding.containerForFamilyMovies
        val movieAdapter = MoviesHomePageAdapter(data, object : OnItemClickListener {
            override fun onItemClickListener(movieData: MovieData) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MovieDetailsFragment.newInstance(movieData))
                    .addToBackStack(null).commit()
            }
        })
        listOfTrendyMovies.adapter = movieAdapter
        listOfNewMovies.adapter = movieAdapter
        listOfTheBestMovies.adapter = movieAdapter
        listOfComingSoonInCinemas.adapter = movieAdapter
        listOfHolidayMovies.adapter = movieAdapter
        listOfFamilyMovies.adapter = movieAdapter

        listOfTrendyMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        listOfNewMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        listOfTheBestMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        listOfComingSoonInCinemas.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        listOfHolidayMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        listOfFamilyMovies.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
