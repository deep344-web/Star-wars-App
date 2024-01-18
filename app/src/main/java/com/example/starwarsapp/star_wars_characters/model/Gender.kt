package com.example.starwarsapp.star_wars_characters.model

sealed class Gender(val value: String){
    data object Male: Gender("male")
    data object Female: Gender("female")
}