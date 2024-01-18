package com.example.starwarsapp.star_wars_films.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.star_wars_films.model.Film
import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import com.example.starwarsapp.star_wars_films.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StarWarsFilmsViewModel @Inject constructor(
    private val repository: StarWarsFilmRepository,
    private val savedStateHandle: SavedStateHandle? = null,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    companion object {
        private const val LIST_OF_FILMS = "list_of_films"
    }

    private val _screenState  = MutableStateFlow<ScreenState>(ScreenState.SetLoading)
    val screenState = _screenState.asStateFlow()

    init {
        viewModelScope.launch(ioDispatcher) {
            val list = savedStateHandle?.get<ArrayList<String>>(LIST_OF_FILMS)
            val films : ArrayList<Film> = arrayListOf()

            list?.forEach {
                repository.getFilm(it).getOrNull()?.body()?.let { film ->
                    films.add(film)
                }
            }

            _screenState.value = ScreenState.FilmListState(films)
        }
    }

}