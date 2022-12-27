package com.example.java.android1.movie_search.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentFavoriteBinding
import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel
import com.example.java.android1.movie_search.viewmodel.RoomAppState

class FavoriteFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteFragment()
    }
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter { movieData ->
            val bundle = Bundle()
            val movieDataTMDB = MovieDataTMDB(
                null,
                null,
                movieData.moviePoster,
                null,
                movieData.movieId,
                null,
                null,
                null,
                null,
                movieData.movieTitle,
                null,
                movieData.movieRating,
                movieData.movieReleaseDate,
                null,
                null,
                CreditsDTO(listOf()),
                videos = null
            )
            bundle.putParcelable(MovieDetailsFragment.ARG_MOVIE_DATA_KEY, movieDataTMDB)
            requireActivity().supportFragmentManager.replace(
                R.id.container,
                MovieDetailsFragment.newInstance(bundle)
            )
        }
    }
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FavoriteViewModel by lazy {
        ViewModelProvider(this)[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        viewModel.localMovieLiveData.observe(viewLifecycleOwner) { renderData(it) }
        val favoriteRecyclerView = binding.favoriteContainer
        favoriteRecyclerView.adapter = favoriteAdapter
        favoriteRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        return binding.root
    }

    private fun renderData(roomAppState: RoomAppState) {
        when (roomAppState) {
            is RoomAppState.Error -> {

            }
            is RoomAppState.Success -> {
                val movieData = roomAppState.data
                favoriteAdapter.setMovieData(movieData)
            }
            RoomAppState.Loading -> {

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}