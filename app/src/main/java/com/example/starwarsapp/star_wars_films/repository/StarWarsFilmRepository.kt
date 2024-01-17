package com.example.starwarsapp.star_wars_films.repository

import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response

interface StarWarsFilmRepository {
    suspend fun getFilm(url : String) : Response<Film?>
}