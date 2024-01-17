package com.example.starwarsapp.star_wars_films.model

import com.example.starwarsapp.star_wars_characters.model.PeopleList

sealed class ScreenState {
    data class FilmListState(val filmList: ArrayList<Film>) : ScreenState()
    class SetLoading : ScreenState()
}