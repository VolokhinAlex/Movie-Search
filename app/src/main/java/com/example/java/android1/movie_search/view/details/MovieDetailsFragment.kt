package com.example.java.android1.movie_search.view.details

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMovieDetailsBinding
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.utils.DialogFragmentNote
import com.example.java.android1.movie_search.utils.getYearFromStringFullDate
import com.example.java.android1.movie_search.utils.replace
import com.example.java.android1.movie_search.view.actor.MovieActorFragment
import com.example.java.android1.movie_search.view.actor.MovieActorFragment.Companion.ARG_ACTOR_MOVIE_DATA
import com.example.java.android1.movie_search.app.AppState
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import com.example.java.android1.movie_search.app.RoomAppState
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.wasabeef.glide.transformations.BlurTransformation
import java.text.DecimalFormat

@Suppress("UNCHECKED_CAST")
class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val mBinding get() = _binding!!
    private val ratingFormat = DecimalFormat("#.#")
    private var mMovieData: MovieDataTMDB? = null
    private val actorsAdapter = MovieActorsAdapter { actorData ->
        val bundle = Bundle()
        bundle.putParcelable(ARG_ACTOR_MOVIE_DATA, actorData)
        activity?.supportFragmentManager?.replace(
            R.id.container,
            MovieActorFragment.newInstance(bundle)
        )
    }
    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this)[DetailsViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mMovieData = it.getParcelable(ARG_MOVIE_DATA_KEY)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        mMovieData = arguments?.getParcelable(ARG_MOVIE_DATA_KEY)
        viewModel.detailsLiveData.observe(viewLifecycleOwner) { renderData(it) }
        mMovieData?.id?.let { viewModel.getMovieDetailsFromRemoteSource(it, "ru-RU") }
        val actorsList: RecyclerView = mBinding.containerActorsList
        actorsList.apply {
            this.adapter = actorsAdapter
            this.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
        viewModel.movieLocalLiveData.observe(viewLifecycleOwner) { renderDataFromLocalDataBase(it) }
        return mBinding.root
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val movieData = appState.data
                setMovieData(movieData[0])
                viewModel.getMovieFromLocalDataBase(movieData[0])
            }
            is AppState.Error -> {
                val error = appState.error
                error.message?.let { Log.e("APP_STATE_ERROR", it) }
            }
            AppState.Loading -> {}
        }
    }

    private fun renderDataFromLocalDataBase(roomAppState: RoomAppState) {
        when (roomAppState) {
            is RoomAppState.Error -> {
                val error = roomAppState.error
                error.message?.let { Log.e("APP_STATE_ERROR", it) }
            }
            RoomAppState.Loading -> {}
            is RoomAppState.Success -> {
                val movieDataRoom = roomAppState.data
                mBinding.itemMovieFavorite.isChecked = movieDataRoom[0].movieFavorite ?: false
                openMovieNote(movieDataRoom[0], viewModel)
            }
        }

    }

    private fun setMovieData(movieDataDTO: MovieDataTMDB) {
        val castDTO = movieDataDTO.credits.cast
        actorsAdapter.setActorData(castDTO)
        val countries = StringBuilder()
        val genres = StringBuilder()
        movieDataDTO.production_countries?.forEach {
            countries.append(it.iso_3166_1).append(", ")
        }
        movieDataDTO.genres?.forEach {
            genres.append(it.name).append(", ")
        }
        with(mBinding) {
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
            mBinding.detailMovieImage.load("https://image.tmdb.org/t/p/w500${movieDataDTO.poster_path}")
            mBinding.detailMovieImage.setRenderEffect(
                RenderEffect.createBlurEffect(
                    80.0f, 80.0f, Shader.TileMode.CLAMP
                )
            )
        } else {
            Glide.with(requireActivity()).load(R.drawable.movie_image)
                .load("https://image.tmdb.org/t/p/w500${movieDataDTO.poster_path}")
                .apply(bitmapTransform(BlurTransformation(80)))
                .into(mBinding.detailMovieImage)
        }
    }

    private fun setFavoriteTrueOrFalse(movieId: Int) {
        mBinding.itemMovieFavorite.setOnCheckedChangeListener { _, isChecked ->
            viewModel.updateMovieFavorite(movieId = movieId, favorite = isChecked)
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
        mBinding.movieNote.setOnClickListener {
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