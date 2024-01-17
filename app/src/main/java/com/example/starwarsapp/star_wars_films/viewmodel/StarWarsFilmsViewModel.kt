package com.example.starwarsapp.star_wars_films.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.star_wars_films.model.Film
import com.example.starwarsapp.star_wars_films.model.ScreenState
import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarWarsFilmsViewModel @Inject constructor(
    private val repository: StarWarsFilmRepository,
    private val savedStateHandle: SavedStateHandle? = null
) : ViewModel() {

    private val _screenState   = MutableStateFlow<ScreenState>(ScreenState.SetLoading())
    val screenState = _screenState.asStateFlow()


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val list = savedStateHandle?.get<ArrayList<String>>("list_of_films")
            val films : ArrayList<Film> = arrayListOf()
            list?.forEach {
                val response = repository.getFilm(it).body()
                response?.let {
                    films.add(it)
                }
            }

            _screenState.value = ScreenState.FilmListState(films)

        }
    }

}