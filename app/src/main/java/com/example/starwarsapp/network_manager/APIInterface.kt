package com.example.starwarsapp.network_manager

import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_films.model.Film
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface APIInterface {

    @GET("people/")
    suspend fun getAllPeople(@Query("page") page: Int) : Response<PeopleList?>

    @GET
    suspend fun getFilm(@Url url: String) : Response<Film?>
}