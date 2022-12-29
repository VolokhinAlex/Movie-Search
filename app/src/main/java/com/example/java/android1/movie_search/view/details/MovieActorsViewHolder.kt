package com.example.java.android1.movie_search.view.details

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bumptech.glide.Glide
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.ActorData

class MovieActorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val actorName: TextView
    private val actorPhoto: AppCompatImageView

    init {
        actorName = itemView.findViewById(R.id.item_movie_actors_card_name)
        actorPhoto = itemView.findViewById(R.id.item_movie_actors_card_image)
    }

    fun bind(data: ActorData) {
        actorName.text = data.actorName
//        Glide.with(itemView.context).load("https://image.tmdb.org/t/p/w500${data.actorPhoto}")
//            .into(actorPhoto)
        actorPhoto.load("https://image.tmdb.org/t/p/w500${data.actorPhoto}")
    }

}