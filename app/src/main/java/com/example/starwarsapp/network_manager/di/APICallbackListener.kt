package com.example.starwarsapp.network_manager.di
interface ApiCallbackListener<T> {
    fun onApiSuccess(response: T?)
    fun onApiFailure(message: String, code : Int? = -1)

}
