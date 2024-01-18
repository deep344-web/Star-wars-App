package com.example.starwarsapp.network_manager.di

import com.example.starwarsapp.network_manager.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class APIModule {
    /**
     * Provides [APIInterface] for other module usage.
     */
    @Provides
    fun provideAPIService(retrofit: Retrofit) : APIInterface {
        return retrofit.create(APIInterface::class.java)
    }
}