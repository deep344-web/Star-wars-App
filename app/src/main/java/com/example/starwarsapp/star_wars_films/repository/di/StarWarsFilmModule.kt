package com.example.starwarsapp.star_wars_films.repository.di

import com.example.starwarsapp.star_wars_films.repository.StarWarsFilmRepository
import com.example.starwarsapp.star_wars_films.repository.impl.StarWarsFilmRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StarWarsFilmModule {

    @Binds
    @Singleton
    abstract fun bindStarWarsFilmRepository(
        starWarsRepositoryImpl : StarWarsFilmRepositoryImpl
    ) : StarWarsFilmRepository
}