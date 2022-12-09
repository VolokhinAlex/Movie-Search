package com.example.java.android1.movie_search.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.AppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var mAdapter: MoviesHomePageAdapter

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        viewModel.getLiveData().observe(viewLifecycleOwner) { state -> renderData(state) }
        viewModel.getMovieFromLocalSource()
        initRecyclerViews()
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                mAdapter.setMovieData(movieData)
            }
            is AppState.Error -> {
                val error = appState.error
                android.util.Log.e("APP_STATE_ERROR", error.message ?: "Error get data")
            }
            AppState.Loading -> {

            }
        }
    }

    private fun initRecyclerViews() {
        val listOfTrendyMovies: RecyclerView = mBinding.containerForTrendyMovies
        val listOfNewMovies: RecyclerView = mBinding.containerForNewMovies
        val listOfTheBestMovies: RecyclerView = mBinding.containerForTheBestMovies
        val listOfComingSoonInCinemas: RecyclerView = mBinding.containerForComingSoonInCinemas
        val listOfHolidayMovies: RecyclerView = mBinding.containerForHolidayMovies
        val listOfFamilyMovies: RecyclerView = mBinding.containerForFamilyMovies

        mAdapter = MoviesHomePageAdapter { movieData ->
            val bundle = Bundle()
            bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieData)
            requireActivity().supportFragmentManager.replace(
                R.id.container,
                MovieDetailsFragment.newInstance(bundle)
            )
        }

        listOfTrendyMovies.adapter = mAdapter
        listOfNewMovies.adapter = mAdapter
        listOfTheBestMovies.adapter = mAdapter
        listOfComingSoonInCinemas.adapter = mAdapter
        listOfHolidayMovies.adapter = mAdapter
        listOfFamilyMovies.adapter = mAdapter

        listOfTrendyMovies.createLinearLayout()
        listOfNewMovies.createLinearLayout()
        listOfTheBestMovies.createLinearLayout()
        listOfComingSoonInCinemas.createLinearLayout()
        listOfHolidayMovies.createLinearLayout()
        listOfFamilyMovies.createLinearLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mAdapter.removeListener()
    }

    private fun RecyclerView.createLinearLayout() {
        this.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
    }
}

fun FragmentManager.replace(container: Int, fragment: Fragment) {
    this.apply {
        beginTransaction().replace(container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null).commit()
    }
}
