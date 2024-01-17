package com.example.starwarsapp.star_wars_films.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.R
import com.example.starwarsapp.star_wars_films.model.Film

class FilmsRecyclerAdapter(
    private var list : ArrayList<Film>
) : RecyclerView.Adapter<FilmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_view_item, parent, false)
        return FilmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.name?.text = list[position].title
        holder.gender?.text = list[position].releaseDate
        holder.charInitial?.text = list[position].title?.get(0)?.uppercase()
    }

    fun updateList(newList : ArrayList<Film>?){
        newList?.let {
//            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }
    }

}

class FilmViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var charInitial : AppCompatTextView?
    var name : AppCompatTextView?
    var gender: AppCompatTextView?

    init {
        name = itemView.findViewById(R.id.charName)
        gender = itemView.findViewById(R.id.charGender)
        charInitial = itemView.findViewById(R.id.characterInitial)
    }
}