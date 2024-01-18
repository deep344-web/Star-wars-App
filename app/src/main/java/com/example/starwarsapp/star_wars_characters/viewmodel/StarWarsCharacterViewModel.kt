package com.example.starwarsapp.star_wars_characters.viewmodel

import People
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.filters.model.SortBy
import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarWarsCharacterViewModel @Inject constructor(
    private val starWarsRepository: StarWarsRepository
): ViewModel() {

    var page : Int = 1

    private val _screenState = MutableStateFlow<ArrayList<People>>(arrayListOf())
    val screenState = _screenState.asStateFlow()

    private val _filterResponse = MutableStateFlow(FilterResponse(sortBy = null, filterFemale = true, filterMale = true, filterOthers = true))
    val filterResponse = _filterResponse.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = starWarsRepository.getAllStarWarsCharacters(page)
            response.body()?.results?.let { newCharacters ->
                _screenState.update {
                    val oldList = _screenState.value.clone() as ArrayList<People>
                    oldList.addAll(newCharacters)
                    applySortingLogic(oldList)
                }
            }
        }
    }

    private fun applySortingLogic(list : ArrayList<People>) : ArrayList<People>{
        val newList = arrayListOf<People>()
        list.forEach {
            if(it.gender == "male" && filterResponse.value.filterMale){
                newList.add(it)
            } else if(it.gender == "female" && filterResponse.value.filterFemale){
                newList.add(it)
            } else if(!it.gender.equals("female") && !it.gender.equals("male")){
                if(filterResponse.value.filterOthers){
                    newList.add(it)
                }
            }
        }

        if(filterResponse.value.sortBy == SortBy.NAME){
            return ArrayList( newList.sortedWith( compareBy { it.name }))
        }

        return newList
    }

    fun loadNextPage(){
        page++
        getCharacters()
    }

    fun updateFilterResponse(filterResponse : FilterResponse){
        _filterResponse.value = filterResponse
        updateUI()
    }

    private fun updateUI(){
        viewModelScope.launch(Dispatchers.Default) {
            _screenState.update {
                val oldList = _screenState.value.clone() as ArrayList<People>
                applySortingLogic(oldList)
            }
        }
    }
}