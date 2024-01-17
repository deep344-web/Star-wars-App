package com.example.starwarsapp.star_wars_characters.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StarWarsRepoModule {

    @Binds
    @Singleton
    abstract fun bindStarWarsRepository(
        starWarsRepositoryImpl : StarWarsRepositoryImpl) : StarWarsRepository
}