package com.example.starwarsapp.star_wars_films.repository

import com.example.starwarsapp.network_manager.APIInterface
import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response
import javax.inject.Inject

class StarWarsFilmRepositoryImpl @Inject constructor(
    private val apiInterface: APIInterface
): StarWarsFilmRepository {
    override suspend fun getFilm(url : String) : Response<Film?>{
        return apiInterface.getFilm(url)
    }
}