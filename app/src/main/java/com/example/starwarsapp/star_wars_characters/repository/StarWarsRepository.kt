package com.example.starwarsapp.star_wars_characters.repository

import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response

interface StarWarsRepository {
    /**
     * Get all the star-wars character info by page.
     * @param pageNo page number for the current page in pagination.
     * @return [Result] of [Response] of [PeopleList]
     */
    suspend fun getAllStarWarsCharacters(pageNo : Int) : Result<Response<PeopleList?>>
}