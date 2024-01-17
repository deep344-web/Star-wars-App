package com.example.starwarsapp.star_wars_characters.repository

import com.example.starwarsapp.network_manager.APIInterface
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import retrofit2.Response
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(
    private val apiInterface: APIInterface
): StarWarsRepository {
    override suspend fun getAllStarWarsCharacters(pageNo : Int) : Response<PeopleList?> {
        return apiInterface.getAllPeople(pageNo)
    }
}