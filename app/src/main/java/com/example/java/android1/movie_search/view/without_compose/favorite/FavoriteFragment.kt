package com.example.java.android1.movie_search.view.without_compose.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.App
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.databinding.FragmentFavoriteBinding
import com.example.java.android1.movie_search.model.CreditsDTO
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.repository.MovieLocalRepositoryImpl
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.without_compose.details.MovieDetailsFragment
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModel
import com.example.java.android1.movie_search.viewmodel.FavoriteViewModelFactory

class FavoriteFragment : Fragment() {
    private val favoriteMoviesAdapter: FavoriteAdapter by lazy {
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
    private val favoriteViewModel: FavoriteViewModel by lazy {
        ViewModelProvider(
            this,
            FavoriteViewModelFactory(MovieLocalRepositoryImpl(App.movieDao))
        )[FavoriteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        favoriteViewModel.localMovieLiveData.observe(viewLifecycleOwner) { renderDataFromLocalDataBase(it) }
        favoriteViewModel.getMoviesFromLocalDataBase()
        val favoriteMoviesListView = binding.favoriteContainer
        favoriteMoviesListView.adapter = favoriteMoviesAdapter
        favoriteMoviesListView.layoutManager = GridLayoutManager(requireActivity(), 2)
        return binding.root
    }

    private fun renderDataFromLocalDataBase(roomAppState: RoomAppState) {
        when (roomAppState) {
            is RoomAppState.Error -> {}
            is RoomAppState.Success -> {
                val movieData = roomAppState.data
                favoriteMoviesAdapter.setFavoriteMovieListData(movieData)
            }
            RoomAppState.Loading -> {}
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }
}