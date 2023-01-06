package com.example.java.android1.movie_search.view.details

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.CastDTO

class MovieActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val cardActorName: TextView
    private val cardActorPhoto: AppCompatImageView

    init {
        cardActorName = itemView.findViewById(R.id.item_movie_actors_card_name)
        cardActorPhoto = itemView.findViewById(R.id.item_movie_actors_card_image)
    }

    fun bind(data: CastDTO, onItemClickListener: ((CastDTO) -> Unit)?) {
        cardActorName.text = data.name
        cardActorPhoto.load("https://image.tmdb.org/t/p/w500${data.profile_path}")
        itemView.setOnClickListener {
            onItemClickListener?.invoke(data)
        }
    }

}