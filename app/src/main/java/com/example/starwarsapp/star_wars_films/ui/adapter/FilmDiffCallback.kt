package com.example.starwarsapp.star_wars_films.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.starwarsapp.star_wars_films.model.Film


class FilmDiffCallback(oldFilmList: List<Film>, newFilmList: List<Film>) :
    DiffUtil.Callback() {
    private val mOldFilmList: List<Film>
    private val mNewFilmList: List<Film>

    init {
        mOldFilmList = oldFilmList
        mNewFilmList = newFilmList
    }

    override fun getOldListSize(): Int {
        return mOldFilmList.size
    }

    override fun getNewListSize(): Int {
        return mNewFilmList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFilmList[oldItemPosition].toString() === mNewFilmList[newItemPosition].toString()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFilm: Film = mOldFilmList[oldItemPosition]
        val newFilm: Film = mNewFilmList[newItemPosition]
        return oldFilm.toString() == newFilm.toString()
    }
}