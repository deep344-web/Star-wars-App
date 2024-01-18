package com.example.starwarsapp.star_wars_films.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.starwarsapp.MyApplication
import com.example.starwarsapp.common.IoDispatcher
import com.example.starwarsapp.network_manager.di.ApiCallbackListener
import com.example.starwarsapp.network_manager.di.ErrorCodes
import com.example.starwarsapp.network_manager.di.handleApiException
import com.example.starwarsapp.network_manager.di.handleApiResponse
import com.example.starwarsapp.room_db.database.StarWarsDatabase
import com.example.starwarsapp.star_wars_characters.model.PeopleList
import com.example.starwarsapp.star_wars_films.model.Film
import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import com.example.starwarsapp.star_wars_films.ui.state.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
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
) : ViewModel() {

    companion object {
        private const val LIST_OF_FILMS = "list_of_films"
    }

    private val _screenState  = MutableStateFlow<ScreenState>(ScreenState.SetLoading(true))
    val screenState = _screenState.asStateFlow()

    init {
        val list = savedStateHandle?.get<ArrayList<String>>(LIST_OF_FILMS)
        val films: ArrayList<Film> = arrayListOf()

        if(MyApplication.IS_OFFLINE) {
            viewModelScope.launch(Dispatchers.IO) {
                list?.forEach {
                    val film = db.dao.getFilmByURL(it)
                    films.add(film)
                }
                _screenState.value = ScreenState.FilmListState(films)
            }
        }
        else{
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
                    withContext(Dispatchers.IO) {
                        val responseList = arrayListOf<Response<Film?>>()
                        list?.forEach {
                            repository.getFilm(it).fold({response ->
                                responseList.add(response)
                            }, {e ->
                                throw e
                            })
                        }

                        var noFilmFound : Boolean = true;
                        var errorMsg : String = ""
                        var errorCode : Int? = -1
                        var listOfFilms : ArrayList<Film> = arrayListOf()
                        responseList.forEach {
                            it.handleApiResponse(application, object : ApiCallbackListener<Film?> {
                                override fun onApiSuccess(response: Film?) {
                                    viewModelScope.launch(Dispatchers.IO) {
                                        response?.let { it ->
                                            noFilmFound = false
                                            db.dao.insertFilm(it)
                                            listOfFilms.add(it)
                                        }
                                    }
                                }

                                override fun onApiFailure(message: String, code : Int?) {
                                    errorMsg = message
                                    code?.let { code -> errorCode = code }
                                }
                            })
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
    }

}