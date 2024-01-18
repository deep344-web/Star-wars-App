package com.example.starwarsapp.star_wars_characters.viewmodel

import android.app.Application
import android.util.Log
import com.example.starwarsapp.star_wars_characters.model.People
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.MyApplication
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.filters.model.SortBy
import com.example.starwarsapp.network_manager.di.ApiCallbackListener
import com.example.starwarsapp.network_manager.di.ErrorCodes
import com.example.starwarsapp.network_manager.di.handleApiException
import com.example.starwarsapp.network_manager.di.handleApiResponse
import com.example.starwarsapp.room_db.database.StarWarsDatabase
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_characters.model.ScreenState
import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StarWarsCharacterViewModel @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
    private val db : StarWarsDatabase,
    private val application: Application
): ViewModel() {

    var page : Int = 1

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenUI(loading = true))
    val screenState = _screenState.asStateFlow()

    private val _filterResponse = MutableStateFlow(FilterResponse(sortBy = null, filterFemale = true, filterMale = true, filterOthers = true))
    val filterResponse = _filterResponse.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharactersFromRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            val list = db.dao.getAllCharacters()
            _screenState.value = ScreenState.Response(ArrayList(list), MyApplication.IS_OFFLINE)
        }
    }

    private fun getCharacters(){
        val apiCallbackListener = object : ApiCallbackListener<PeopleList?>{
            override fun onApiSuccess(response: PeopleList?) {
                viewModelScope.launch(Dispatchers.IO) {
                    Log.d("characters count", db.dao.getAllCharacters().size.toString())
                    Log.d("characters count", response?.results?.size.toString())

                    response?.results?.forEach{
                        db.dao.insertCharacter(it)
                    }
                    val list = db.dao.getAllCharacters()
                    Log.d("characters count", db.dao.getAllCharacters().size.toString())

                    _screenState.value = ScreenState.Response(applySortingLogic(ArrayList(list)), MyApplication.IS_OFFLINE)
                }
            }

            override fun onApiFailure(message: String, code : Int?) {
                if(code == ErrorCodes.INTERNET_ERROR.value || code == ErrorCodes.TIME_OUT.value){
                    MyApplication.IS_OFFLINE = true
                    getCharactersFromRoom()
                }
                else{
                    _screenState.value = ScreenState.ErrorState(message, code)

                }
            }
        }

        viewModelScope.launch (
            context = CoroutineExceptionHandler { _, throwable ->
                throwable.handleApiException(
                    apiCallbackListener
                )
            },
            block = {
                withContext(Dispatchers.IO) {
                    starWarsRepository.getAllStarWarsCharacters(page).handleApiResponse(application, apiCallbackListener)
                }
            }
        )
    }

    private fun applySortingLogic(list : ArrayList<People>) : ArrayList<People>{
        val newList = arrayListOf<People>()
        list.forEach {
            if(it.gender == "male" && filterResponse.value.filterMale){
                newList.add(it)
            } else if(it.gender == "female" && filterResponse.value.filterFemale){
                newList.add(it)
            } else if(!it.gender.equals("female") && !it.gender.equals("male")){
                if(filterResponse.value.filterOthers){
                    newList.add(it)
                }
            }
        }

        if(filterResponse.value.sortBy == SortBy.NAME){
            return ArrayList( newList.sortedWith( compareBy { it.name }))
        }

        return newList
    }

    fun loadNextPage(){
        if(!MyApplication.IS_OFFLINE) {
            page++
            _screenState.value = ScreenState.ScreenUI(loading = true)
            getCharacters()
        }
    }

    fun updateFilterResponse(filterResponse : FilterResponse){
        _filterResponse.value = filterResponse
        _screenState.value = ScreenState.ScreenUI(loading = true)
        updateUI()
    }

    private fun deleteAllData(){
        if(page == 1) {
            viewModelScope.launch(Dispatchers.IO) {
                db.dao.deleteAllData()
            }
        }
    }

    private fun updateUI(){
        viewModelScope.launch(Dispatchers.Default) {
            val list = db.dao.getAllCharacters()
            _screenState.value = ScreenState.Response(applySortingLogic(ArrayList(list)), MyApplication.IS_OFFLINE)
        }
    }
}