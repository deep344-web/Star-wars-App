package com.example.starwarsapp.star_wars_characters.repository.impl

import android.util.Log
import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.common.runCatchingWithDispatcher
import com.example.starwarsapp.network_manager.APIInterface
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Response
import javax.inject.Inject

class StarWarsRepositoryImpl @Inject constructor(
    private val apiInterface: APIInterface,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
): StarWarsRepository {
    override suspend fun getAllStarWarsCharacters(pageNo : Int) : Result<Response<PeopleList?>> {
        Log.d("Api Calls", "API Fetched $pageNo")
        return runCatchingWithDispatcher(ioDispatcher){
             apiInterface.getAllPeople(pageNo)
        }
    }
}