package com.example.java.android1.movie_search.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.databinding.FragmentMovieDetailsBinding
import com.example.java.android1.movie_search.model.MovieData
import com.google.android.material.bottomnavigation.BottomNavigationView

private const val ARG_MOVIE_DATA_KEY = "movieDataKey"

class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val mBinding get() = _binding!!

    private var mMovieData: MovieData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mMovieData = it.getParcelable(ARG_MOVIE_DATA_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation).visibility =
            View.GONE
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        mMovieData = arguments?.getParcelable(ARG_MOVIE_DATA_KEY)
        setMovieData()
        val actorsList: RecyclerView = mBinding.containerActorsList
        val actorsAdapter = MovieActorsAdapter()
        actorsAdapter.setActorData(mMovieData?.actors!!)
        actorsList.adapter = actorsAdapter
        actorsList.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL, false
        )
        return mBinding.root
    }

    private fun setMovieData() {
        mBinding.detailMovieTitle.text = mMovieData?.title
        mBinding.detailMovieReleaseDate.text = mMovieData?.releaseDate
        mBinding.detailMovieOverview.text = mMovieData?.overview
        mBinding.detailMovieRating.text = mMovieData?.ratingOfMovie.toString()
        mBinding.detailMovieGenres.text = mMovieData?.genres.toString()
        mBinding.detailMovieTime.text = "1ч. 30 мин."
        mBinding.detailMovieAge.text = String.format("%s+", mMovieData?.age)
        mBinding.detailMovieCountry.text = mMovieData?.country
    }

    companion object {
        @JvmStatic
        fun newInstance(movieData: MovieData) =
            MovieDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MOVIE_DATA_KEY, movieData)
                }
            }
    }
}