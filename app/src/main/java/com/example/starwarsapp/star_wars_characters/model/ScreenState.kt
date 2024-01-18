package com.example.starwarsapp.star_wars_characters.model


sealed class ScreenState {
    data class Response(val peopleList: ArrayList<People>, val fromDB : Boolean) : ScreenState()
    data class ErrorState(val msg : String, val code : Int?) : ScreenState()
    data class ScreenUI(val loading : Boolean) : ScreenState()
}