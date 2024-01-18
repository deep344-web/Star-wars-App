package com.example.starwarsapp.network_manager.di

enum class ErrorCodes(val value : Int) {
    TIME_OUT(1),
    INTERNET_ERROR(2),
    DO_NOT_KNOW(3),
    SOMETHING_WENT_WRONG(4)
}