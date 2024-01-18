package com.example.starwarsapp.network_manager

import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


/**
 * Retrofit API declaration for Star Wars APIs
 */
interface APIInterface {

    /**
     * Get characters details for StarWars by pagination.
     * @param page page id for that page.
     * @return [Response] of [PeopleList] with details of all the characters.
     * Returns `404` if reached the end of the list.
     */
    @GET("people/")
    suspend fun getAllPeople(@Query("page") page: Int) : Response<PeopleList?>

    /**
     * Get
     * @param url to get the film details.
     * @return [Response] of [Film] with details of the film.
     */
    @GET
    suspend fun getFilm(@Url url: String) : Response<Film?>
}