package com.example.starwarsapp.network_manager

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class APIModule {

    @Provides
    fun provideAPIService(retrofit: Retrofit) : APIInterface{
        return retrofit.create(APIInterface::class.java)
    }
}