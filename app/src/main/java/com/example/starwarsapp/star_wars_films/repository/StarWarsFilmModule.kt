package com.example.starwarsapp.star_wars_films.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StarWarsFilmModule {

    @Binds
    @Singleton
    abstract fun bindStarWarsFilmRepository(
        starWarsRepositoryImpl : StarWarsFilmRepositoryImpl
    ) : StarWarsFilmRepository
}