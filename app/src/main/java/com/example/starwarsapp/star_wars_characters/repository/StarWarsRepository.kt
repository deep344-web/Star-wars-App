package com.example.starwarsapp.star_wars_characters.repository

import com.example.starwarsapp.star_wars_characters.model.PeopleList
import retrofit2.Response

interface StarWarsRepository {
    suspend fun getAllStarWarsCharacters(pageNo : Int) : Response<PeopleList?>
}