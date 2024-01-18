package com.example.starwarsapp.network_manager.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    companion object{
        private const val NIA_BASE_URL = "https://swapi.dev/api/"
    }

    @Provides
    @Singleton
    fun provideNetworkInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(NIA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}