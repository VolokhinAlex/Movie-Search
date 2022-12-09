package com.example.java.android1.movie_search.view.details

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.java.android1.movie_search.R
import com.example.java.android1.movie_search.model.ActorData


class MovieActorsAdapter : RecyclerView.Adapter<MovieActorsViewHolder>() {

    private var actorsData: List<ActorData> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setActorData(data: List<ActorData>) {
        actorsData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieActorsViewHolder {
        return MovieActorsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_actors_card_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieActorsViewHolder, position: Int) {
        holder.bind(actorsData[position])
    }

    override fun getItemCount(): Int = actorsData.size
}