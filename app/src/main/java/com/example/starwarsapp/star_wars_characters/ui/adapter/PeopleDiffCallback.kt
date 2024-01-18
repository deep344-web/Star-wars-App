package com.example.starwarsapp.star_wars_characters.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.starwarsapp.star_wars_characters.model.People

class PeopleDiffCallback(oldPeopleList: List<People>, newPeopleList: List<People>) :
    DiffUtil.Callback() {
    private val mOldPeopleList: List<People>
    private val mNewPeopleList: List<People>

    init {
        mOldPeopleList = oldPeopleList
        mNewPeopleList = newPeopleList
    }

    override fun getOldListSize(): Int {
        return mOldPeopleList.size
    }

    override fun getNewListSize(): Int {
        return mNewPeopleList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldPeopleList[oldItemPosition].toString() === mNewPeopleList[newItemPosition].toString()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldPeople: People = mOldPeopleList[oldItemPosition]
        val newPeople: People = mNewPeopleList[newItemPosition]
        return oldPeople.toString() == newPeople.toString()
    }
}