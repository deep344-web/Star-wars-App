package com.example.starwarsapp.star_wars_films.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.common.OfflineHelper
import com.example.starwarsapp.common.OfflineHelperImpl
import com.example.starwarsapp.common.runCatchingWithDispatcher
import com.example.starwarsapp.network_manager.di.ApiCallbackListener
import com.example.starwarsapp.network_manager.di.handleApiException
import com.example.starwarsapp.network_manager.di.handleApiFailure
import com.example.starwarsapp.network_manager.di.handleApiResponse
import com.example.starwarsapp.room_db.database.StarWarsDatabase
import com.example.starwarsapp.star_wars_films.model.Film
import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import com.example.starwarsapp.star_wars_films.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class StarWarsFilmsViewModel @Inject constructor(
    private val repository: StarWarsFilmRepository,
    private val savedStateHandle: SavedStateHandle? = null,
    private val application: Application,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val db : StarWarsDatabase,
) : ViewModel(), OfflineHelper by OfflineHelperImpl() {

    companion object {
        private const val LIST_OF_FILMS = "list_of_films"
    }

    private val _screenState  = MutableStateFlow<ScreenState>(ScreenState.SetLoading(true))
    val screenState = _screenState.asStateFlow()

    private val filmUrlList = savedStateHandle?.get<ArrayList<String>>(LIST_OF_FILMS)

    init {
        if(isOffline()) {
            loadFilmsFromOffline()
        }
        else{
            loadFilmsFromOnline()
        }
    }

    private fun loadFilmsFromOnline(){
        val apiCallbackListener = object : ApiCallbackListener<Film?> {
            override fun onApiSuccess(response: Film?) {
            }

            override fun onApiFailure(message: String, code : Int?) {
                _screenState.value = ScreenState.ErrorState(message, code)
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
                    val responseList = getFilmResponses().getOrNull()
                    var noFilmFound = true
                    var errorMsg = ""
                    var errorCode : Int? = -1
                    val listOfFilms : ArrayList<Film> = arrayListOf()

                    responseList?.forEach {
                        if(it.isSuccessful && it.body() != null){
                            noFilmFound = false
                            db.dao.insertFilm(it.body()!!)
                            listOfFilms.add(it.body()!!)
                        }
                        else{
                            errorMsg = it.message()
                            it.code().let { code -> errorCode = code }
                        }
                    }

                    if(noFilmFound){
                        _screenState.value = ScreenState.ErrorState(errorMsg, errorCode)
                    }
                    else {
                        _screenState.value = ScreenState.FilmListState(listOfFilms)
                    }
                }
            }
        )
    }

    private fun loadFilmsFromOffline(){
        viewModelScope.launch(ioDispatcher) {
            val films: ArrayList<Film> = arrayListOf()
            filmUrlList?.forEach {
                val film = db.dao.getFilmByURL(it)
                films.add(film)
            }
            _screenState.value = ScreenState.FilmListState(films)
        }
    }

    private suspend fun getFilmResponses(): Result<List<Response<Film?>>>{
        return runCatchingWithDispatcher(ioDispatcher){
            val responseList = arrayListOf<Response<Film?>>()
            val subItems = filmUrlList?.map { filmUrl ->
                viewModelScope.async { repository.getFilm(filmUrl).getOrNull() }
            }?.awaitAll()
            subItems?.forEach {
                it?.let {
                    responseList.add(it)
                }
            }
            responseList
        }
    }

}

