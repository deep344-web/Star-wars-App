package com.example.starwarsapp.star_wars_characters.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.common.DefaultDispatcher
import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.common.OfflineHelper
import com.example.starwarsapp.common.OfflineHelperImpl
import com.example.starwarsapp.filters.model.FilterResponse
import com.example.starwarsapp.filters.model.SortBy
import com.example.starwarsapp.network_manager.di.ApiCallbackListener
import com.example.starwarsapp.network_manager.di.ErrorCodes
import com.example.starwarsapp.network_manager.di.handleApiException
import com.example.starwarsapp.network_manager.di.handleApiResponse
import com.example.starwarsapp.room_db.database.StarWarsDatabase
import com.example.starwarsapp.star_wars_characters.model.Gender
import com.example.starwarsapp.star_wars_characters.model.People
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_characters.model.ScreenState
import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StarWarsCharacterViewModel @Inject constructor(
    private val starWarsRepository: StarWarsRepository,
    private val db : StarWarsDatabase,
    private val application: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ViewModel(), OfflineHelper by OfflineHelperImpl(){

    private var page : Int = 1

    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenUI(loading = true))
    val screenState = _screenState.asStateFlow()

    private val _filterResponse = MutableStateFlow(FilterResponse(sortBy = null, filterFemale = true, filterMale = true, filterOthers = true))
    val filterResponse = _filterResponse.asStateFlow()

    init {
        getCharacters()
    }

    private fun getCharactersFromRoom(){
        viewModelScope.launch(ioDispatcher) {
            val list = db.dao.getAllCharacters()
            _screenState.value = ScreenState.Response(ArrayList(list), isOffline())
        }
    }

    private fun getCharacters(){
        val apiCallbackListener = object : ApiCallbackListener<PeopleList?>{
            override fun onApiSuccess(response: PeopleList?) {
                onAPISuccess(response)
            }

            override fun onApiFailure(message: String, code : Int?) {
                if(code == ErrorCodes.INTERNET_ERROR.value || code == ErrorCodes.TIME_OUT.value){
                    updateOffline(true)
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
                withContext(ioDispatcher) {
                    starWarsRepository.getAllStarWarsCharacters(page).fold({
                        it.handleApiResponse(application, apiCallbackListener)
                    },{
                        throw it
                    })
                }
            }
        )
    }

    private fun onAPISuccess(response: PeopleList?){
        viewModelScope.launch(ioDispatcher) {
            response?.results?.forEach{
                db.dao.insertCharacter(it)
            }
            val list = db.dao.getAllCharacters()
            _screenState.value = ScreenState.Response(applySortingLogic(ArrayList(list)), isOffline())
        }
    }

    private fun applySortingLogic(list : ArrayList<People>) : ArrayList<People>{
        val newList = arrayListOf<People>()
        list.forEach {
            if(it.gender == Gender.Male.value && filterResponse.value.filterMale){
                newList.add(it)
            } else if(it.gender == Gender.Female.value && filterResponse.value.filterFemale){
                newList.add(it)
            } else if(!it.gender.equals(Gender.Female.value) && !it.gender.equals(Gender.Male.value)){
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
        if(isOffline()) {
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

    private fun updateUI(){
        viewModelScope.launch(defaultDispatcher) {
            val list = db.dao.getAllCharacters()
            _screenState.value = ScreenState.Response(applySortingLogic(ArrayList(list)), isOffline())
        }
    }
}