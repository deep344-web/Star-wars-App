package com.example.starwarsapp.star_wars_films.repository

import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response

interface StarWarsFilmRepository {

    /**
     * Get film response with given film URL.
     * @param url given url for the film.
     * @return [Result] of [Response] of [Film]
     */

    suspend fun getFilm(url : String) : Result<Response<Film?>>
}