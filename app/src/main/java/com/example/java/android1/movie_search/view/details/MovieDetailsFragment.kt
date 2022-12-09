package com.example.java.android1.movie_search.view.details

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.example.java.android1.movie_search.databinding.FragmentMovieDetailsBinding
import com.example.java.android1.movie_search.model.MovieData
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.wasabeef.glide.transformations.BlurTransformation
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.view.home.replace

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

        mMovieData?.let {
            actorsAdapter.setActorData(it.actors)
        }
        actorsList.apply {
            this.adapter = actorsAdapter
            this.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL, false
            )
        }
        return mBinding.root
    }

    private fun setMovieData() {
        mBinding.apply {
            this.detailMovieTitle.text = mMovieData?.title
            this.detailMovieReleaseDate.text = mMovieData?.releaseDate
            this.detailMovieOverview.text = mMovieData?.overview
            this.detailMovieRating.text = mMovieData?.ratingOfMovie.toString()
            this.detailMovieGenres.text = mMovieData?.genres.toString()
            this.detailMovieTime.text = "1ч. 30 мин."
            this.detailMovieAge.text = String.format("%s+", mMovieData?.age)
            this.detailMovieCountry.text = mMovieData?.country
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            mBinding.detailMovieImage.setRenderEffect(
                RenderEffect.createBlurEffect(
                80.0f, 80.0f, Shader.TileMode.CLAMP
            ))
        } else {
            Glide.with(requireActivity()).load(R.drawable.movie_image)
                .apply(bitmapTransform(BlurTransformation(80)))
                .into(mBinding.detailMovieImage)
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