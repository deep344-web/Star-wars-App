package com.example.starwarsapp.star_wars_characters.adapter

import People
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.R
import com.google.android.material.card.MaterialCardView


class CharactersRecyclerAdapter(
    private var list : ArrayList<People>,
    private val onItemSelected : (ArrayList<String>?) -> Unit

) : RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.grid_view_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.name?.text = list[position].name
        holder.gender?.text = list[position].gender
        holder.charInitial?.text = list[position].name?.get(0)?.uppercase()
        holder.parent?.setOnClickListener {
            onItemSelected(list[position].filmsUrls)
        }
    }

    fun updateList(newList : ArrayList<People>?){
        newList?.let {
//            list.clear()
            list.addAll(newList)
            notifyDataSetChanged()
        }
    }

}

class CharactersViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var charInitial : AppCompatTextView?
    var name : AppCompatTextView?
    var gender: AppCompatTextView?
    var parent : MaterialCardView?

    init {
        name = itemView.findViewById(R.id.charName)
        gender = itemView.findViewById(R.id.charGender)
        charInitial = itemView.findViewById(R.id.characterInitial)
        parent = itemView.findViewById(R.id.parent)
    }
}