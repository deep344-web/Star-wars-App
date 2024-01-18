package com.example.starwarsapp.star_wars_films.ui.state

import com.example.starwarsapp.star_wars_films.model.Film

/**
 * UI state to show the state of the Film Screen.
 */
sealed class ScreenState {
    data class FilmListState(val filmList: ArrayList<Film>) : ScreenState()
    data object SetLoading : ScreenState()
}