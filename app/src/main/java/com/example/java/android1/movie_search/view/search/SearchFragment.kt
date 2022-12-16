package com.example.java.android1.movie_search.view.search

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentSearchBinding
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.view.home.replace

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val mBinding get() = _binding!!
    private val searchAdapter = SearchAdapter { movieData ->
        val bundle = Bundle()
        bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieData)
        requireActivity().supportFragmentManager.replace(
            R.id.container,
            MovieDetailsFragment.newInstance(bundle)
        )
    }
    private val loadData: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.getStringExtra(LOAD_RESULT_EXTRA)) {
                RESPONSE_SUCCESS_EXTRA -> {
                    val data: List<MovieDataTMDB> =
                        intent.getParcelableArrayListExtra<MovieDataTMDB>(DATA_EXTRA) as List<MovieDataTMDB>
                    searchAdapter.setMovieData(data)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(
            loadData, IntentFilter(
                INTENT_FILTER
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val searchRecyclerView = mBinding.searchContainer
        searchRecyclerView.adapter = searchAdapter
        searchRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        mBinding.searchField.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(text: String?): Boolean {
                requireContext().startService(Intent(requireContext(), SearchDataLoad::class.java).putExtra(
                    TITLE_EXTRA, text))
                return true
            }

        })
        return mBinding.root
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(loadData)
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply {
            }
    }
}