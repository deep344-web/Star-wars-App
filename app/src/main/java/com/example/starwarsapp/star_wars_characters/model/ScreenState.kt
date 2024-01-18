package com.example.starwarsapp.star_wars_characters.model

import People

sealed class ScreenState {
    data class PeopleListState(val peopleList: ArrayList<People>) : ScreenState()
    class SetLoading : ScreenState()
}