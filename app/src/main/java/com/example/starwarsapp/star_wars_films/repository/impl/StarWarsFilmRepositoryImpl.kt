package com.example.starwarsapp.star_wars_films.repository.impl

import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.common.runCatchingWithDispatcher
import com.example.starwarsapp.network_manager.APIInterface
import com.example.starwarsapp.star_wars_films.model.Film
import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response
import javax.inject.Inject

class StarWarsFilmRepositoryImpl @Inject constructor(
    private val apiInterface: APIInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): StarWarsFilmRepository {
    override suspend fun getFilm(url : String) : Result<Response<Film?>>{
        return runCatchingWithDispatcher(ioDispatcher){
            apiInterface.getFilm(url)
        }
    }
}