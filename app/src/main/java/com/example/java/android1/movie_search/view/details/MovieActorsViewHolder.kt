package com.example.java.android1.movie_search.view.details

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.CastDTO

class MovieActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val actorName: TextView
    private val actorPhoto: AppCompatImageView

    init {
        actorName = itemView.findViewById(R.id.item_movie_actors_card_name)
        actorPhoto = itemView.findViewById(R.id.item_movie_actors_card_image)
    }

    fun bind(data: CastDTO, onItemClickListener: ((CastDTO) -> Unit)?) {
        actorName.text = data.name
        actorPhoto.load("https://image.tmdb.org/t/p/w500${data.profile_path}")
        itemView.setOnClickListener {
            onItemClickListener?.invoke(data)
        }
    }

}