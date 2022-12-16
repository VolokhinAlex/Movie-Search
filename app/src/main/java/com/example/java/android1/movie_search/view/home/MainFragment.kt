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
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMainBinding
import com.example.java.android1.movie_search.model.MovieChildListData
import com.example.java.android1.movie_search.viewmodel.AppState
import com.example.java.android1.movie_search.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val mBinding get() = _binding!!
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
        viewModel.getLiveData().observe(viewLifecycleOwner) { state -> renderData(state) }
        viewModel.getMovieFromRemoteSource()
        initRecyclerViews()
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                mAdapter.setParentListData(
                    listOf(
                        MovieChildListData(getString(R.string.category_trending_now), movieData),
                        MovieChildListData(getString(R.string.category_new_products), movieData),
                        MovieChildListData(getString(R.string.category_the_best_movies), movieData),
                        MovieChildListData(
                            getString(R.string.category_coming_soon_in_cinemas),
                            movieData
                        ),
                        MovieChildListData(getString(R.string.category_holiday_movies), movieData),
                        MovieChildListData(getString(R.string.category_family_movies), movieData)
                    )
                )
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
        val basicList = mBinding.containerBasicList
        mAdapter = MoviesHomeBasicAdapter()
        basicList.adapter = mAdapter
        basicList.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //mAdapter.removeListener()
    }
}

fun FragmentManager.replace(container: Int, fragment: Fragment) {
    this.apply {
        beginTransaction().replace(container, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .addToBackStack(null).commit()
    }
}
