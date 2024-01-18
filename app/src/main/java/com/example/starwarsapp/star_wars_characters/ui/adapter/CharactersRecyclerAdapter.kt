package com.example.starwarsapp.star_wars_characters.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarsapp.databinding.GridViewItemBinding
import com.example.starwarsapp.star_wars_characters.model.People


class CharactersRecyclerAdapter(
    private var characterList : ArrayList<People>,
    private val onItemSelected : (ArrayList<String>?) -> Unit

) : RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        return CharactersViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        holder.binding.apply {
            charName.text = characterList[position].name
            charGender.text = characterList[position].gender
            if(!characterList[position].name.isNullOrEmpty()){
                characterInitial.text = characterList[position].name?.get(0)?.uppercase()
            }
            parent.setOnClickListener {
                onItemSelected(characterList[position].filmsUrls)
            }
        }
    }

    fun updateList(newList : ArrayList<People>?){
        newList?.let {
            val diffUtil = PeopleDiffCallback(characterList, newList)
            val diffResults = DiffUtil.calculateDiff(diffUtil)
            characterList = newList
            diffResults.dispatchUpdatesTo(this)
        }
    }

}