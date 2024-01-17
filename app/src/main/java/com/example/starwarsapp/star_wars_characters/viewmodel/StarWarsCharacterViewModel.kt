package com.example.starwarsapp.star_wars_characters.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_characters.model.ScreenState
import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarWarsCharacterViewModel @Inject constructor(
    private val starWarsRepository: StarWarsRepository
): ViewModel() {

    var page : Int = 1

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.SetLoading())
    val screenState = _screenState.asStateFlow()

    init {
        getCharacters()
    }
    fun getCharacters(){
        viewModelScope.launch(Dispatchers.IO) {
            val response = starWarsRepository.getAllStarWarsCharacters(page)
            response.body()?.let {
                _screenState.value = ScreenState.PeopleListState(response.body() as PeopleList)
                Log.d("Response", response.body().toString())
            }
        }
    }

    fun loadNextPage(){
        page++
        getCharacters()
    }
}