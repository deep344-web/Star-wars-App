package com.example.starwarsapp.network_manager

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe() : Flow<Status>

    enum class Status{
        AVAILABLE, UNAVAILABLE, LOST
    }
}