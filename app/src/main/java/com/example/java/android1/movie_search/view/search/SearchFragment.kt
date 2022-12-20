package com.example.java.android1.movie_search.view.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentSearchBinding
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.AppState
import com.example.java.android1.movie_search.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!

    private val viewModel: SearchViewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    private val searchAdapter = SearchAdapter { movieData ->
        val bundle = Bundle()
        bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieData)
        requireActivity().supportFragmentManager.replace(
            R.id.container,
            MovieDetailsFragment.newInstance(bundle)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel.searchLiveData.observe(viewLifecycleOwner) { renderData(it) }
        val searchRecyclerView = mBinding.searchContainer
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        mBinding.searchField.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                text?.let { viewModel.getMoviesFromRemoteSource("ru-RU", 1, it) }
                return true
            }

        })
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {
                val error = appState.error
                Log.e("APP_STATE_ERROR", error.message ?: "Error get data")
            }
            AppState.Loading -> {}
            is AppState.Success -> {
                val movieList = appState.data
                searchAdapter.setMovieData(movieList)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
            }
    }
}