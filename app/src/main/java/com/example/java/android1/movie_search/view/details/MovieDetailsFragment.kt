package com.example.java.android1.movie_search.view.details

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.app.MovieAppState
import com.example.java.android1.movie_search.app.RoomAppState
import com.example.java.android1.movie_search.databinding.FragmentMovieDetailsBinding
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.DialogFragmentNote
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.actor.MovieActorFragment
import com.example.java.android1.movie_search.view.actor.MovieActorFragment.Companion.ARG_ACTOR_MOVIE_DATA
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.DecimalFormat

@Suppress("UNCHECKED_CAST")
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private val ratingFormat = DecimalFormat("#.#")
    private var movieData: MovieDataTMDB? = null
    private val actorsMovieAdapter = MovieActorsAdapter { actorData ->
        val bundle = Bundle()
        bundle.putParcelable(ARG_ACTOR_MOVIE_DATA, actorData)
        activity?.supportFragmentManager?.replace(
            R.id.container,
            MovieActorFragment.newInstance(bundle)
        )
    }
    private val movieDetailsViewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieData = it.getParcelable(ARG_MOVIE_DATA_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        movieData = arguments?.getParcelable(ARG_MOVIE_DATA_KEY)
        movieDetailsViewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        movieData?.id?.let { movieDetailsViewModel.getMovieDetailsFromRemoteSource(it, "ru-RU") }
        val actorsMovieListView: RecyclerView = binding.containerActorsList
        actorsMovieListView.apply {
            this.adapter = actorsMovieAdapter
            this.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
        movieDetailsViewModel.movieLocalLiveData.observe(viewLifecycleOwner) {
            renderDataFromLocalDataBase(
                it
            )
        }
        return binding.root
    }

    private fun renderData(appState: MovieAppState) {
        when (appState) {
            is MovieAppState.Success -> {
                val movieData = appState.data
                setDetailsMovieData(movieData[0])
                movieDetailsViewModel.getMovieFromLocalDataBase(movieData[0])
            }
            is MovieAppState.Error -> {}
            MovieAppState.Loading -> {}
        }
    }

    private fun renderDataFromLocalDataBase(roomAppState: RoomAppState) {
        when (roomAppState) {
            is RoomAppState.Error -> {
                val error = roomAppState.error
                error.message?.let { Log.e("ROOM_STATE_ERROR", it) }
            }
            RoomAppState.Loading -> {}
            is RoomAppState.Success -> {
                val movieDataRoom = roomAppState.data
                binding.itemMovieFavorite.isChecked = movieDataRoom[0].movieFavorite ?: false
                openMovieNote(movieDataRoom[0], movieDetailsViewModel)
            }
        }

    }

    private fun setDetailsMovieData(movieDataDTO: MovieDataTMDB) {
        val castDTO = movieDataDTO.credits.cast
        actorsMovieAdapter.setActorsListData(castDTO)
        val countries = StringBuilder()
        val genres = StringBuilder()
        movieDataDTO.production_countries?.forEach {
            countries.append(it.iso_3166_1).append(", ")
        }
        movieDataDTO.genres?.forEach {
            genres.append(it.name).append(", ")
        }
        with(binding) {
            detailMovieFrontImage.load("https://image.tmdb.org/t/p/w500${movieDataDTO.poster_path}")
            detailMovieTitle.text = movieDataDTO.title
            detailMovieReleaseDate.text =
                movieDataDTO.release_date?.let { "".getYearFromStringFullDate(it) }
            detailMovieOverview.text = movieDataDTO.overview
            detailMovieRating.text = ratingFormat.format(movieDataDTO.vote_average)
            detailMovieGenres.text =
                if (genres.length > 2) genres.deleteCharAt(genres.lastIndexOf(", ")) else genres
            detailMovieTime.text = movieDataDTO.runtime?.let { timeToFormatHoursAndMinutes(it) }
            detailMovieCountry.text =
                if (countries.length > 2) countries.deleteCharAt(countries.lastIndexOf(", ")) else countries
        }
        setBlurForBackground(movieDataDTO)
        movieDataDTO.id?.let { setFavoriteTrueOrFalse(it) }
    }

    /**
     * Setting the blur for the movie picture in the background.
     * For Api below 31, the Glide library is used
     */

    private fun setBlurForBackground(movieDataDTO: MovieDataTMDB) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            binding.detailMovieImage.load("https://image.tmdb.org/t/p/w500${movieDataDTO.poster_path}")
            binding.detailMovieImage.setRenderEffect(
                RenderEffect.createBlurEffect(
                    80.0f, 80.0f, Shader.TileMode.CLAMP
                )
            )
        } else {
            Glide.with(requireActivity()).load(R.drawable.movie_image)
                .load("https://image.tmdb.org/t/p/w500${movieDataDTO.poster_path}")
                .apply(bitmapTransform(BlurTransformation(80)))
                .into(binding.detailMovieImage)
        }
    }

    private fun setFavoriteTrueOrFalse(movieId: Int) {
        binding.itemMovieFavorite.setOnCheckedChangeListener { _, isChecked ->
            movieDetailsViewModel.updateMovieFavorite(movieId = movieId, favorite = isChecked)
        }
    }

    /**
     * Minutes are converted to the format of hours and minutes
     * @sample min = 96 -> 1h 36min
     */

    fun timeToFormatHoursAndMinutes(min: Int): String {
        val hour = min / 60
        val minutes = min % 60
        return String.format("%02dh %02dmin", hour, minutes)
    }

    private fun openMovieNote(dataRoom: MovieDataRoom, viewModel: DetailsViewModel) {
        binding.movieNote.setOnClickListener {
            DialogFragmentNote(
                dataRoom,
                viewModel
            ).show(requireActivity().supportFragmentManager, "")
        }
    }

    companion object {
        const val ARG_MOVIE_DATA_KEY = "movieDataKey"

        @JvmStatic
        fun newInstance(bundle: Bundle) =
            MovieDetailsFragment().apply {
                arguments = bundle
            }
    }
}