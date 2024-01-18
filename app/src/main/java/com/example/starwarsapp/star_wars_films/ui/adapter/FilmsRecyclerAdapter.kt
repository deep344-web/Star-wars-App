package com.example.starwarsapp.star_wars_films.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.GridViewItemBinding
import com.example.starwarsapp.star_wars_films.model.Film


class FilmsRecyclerAdapter(
        private var filmList : ArrayList<`Film`>
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        return FilmViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.binding.apply {
            filmList.getOrNull(holder.adapterPosition)?.let {film ->
                charName.text = film.title
                charGender.text = film.releaseDate
                if(!film.title.isNullOrEmpty()){
                    characterInitial.text = film.title[0].uppercase()
                }
            }
        }
    }

    fun updateList(newList : ArrayList<Film>?){
        newList?.let {
            val diffUtil = FilmDiffCallback(filmList, newList)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            filmList = newList
            diffResults.dispatchUpdatesTo(this)
        }
    }

}
