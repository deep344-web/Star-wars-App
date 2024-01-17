package com.example.starwarsapp.star_wars_characters.model

sealed class ScreenState {
    data class PeopleListState(val peopleList: PeopleList) : ScreenState()
    class SetLoading : ScreenState()
}