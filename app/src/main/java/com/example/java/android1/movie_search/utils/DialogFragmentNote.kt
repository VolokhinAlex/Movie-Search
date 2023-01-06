package com.example.java.android1.movie_search.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.java.android1.movie_search.databinding.MovieDialogNoteBinding
import com.example.java.android1.movie_search.model.MovieDataRoom
import com.example.java.android1.movie_search.model.MovieDataTMDB
import com.example.java.android1.movie_search.viewmodel.DetailsViewModel
import com.google.android.material.textfield.TextInputEditText

class DialogFragmentNote(
    private val movieData: MovieDataRoom,
    private val viewModel: DetailsViewModel
) :
    DialogFragment() {

    private var _binding: MovieDialogNoteBinding? = null
    private val mBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDialogNoteBinding.inflate(inflater, container, false)

        val title: TextView = mBinding.movieNoteTitle
        val noteInput: TextInputEditText = mBinding.noteInput

        title.text = movieData.movieTitle
        noteInput.setText(movieData.movieNote)
        mBinding.saveNote.setOnClickListener {
            movieData.movieId?.let { movieId ->
                viewModel.updateMovieNote(movieId, noteInput.text.toString())
            }
            dismiss()
        }
        return mBinding.root
    }

}