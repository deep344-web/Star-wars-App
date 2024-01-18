package com.example.starwarsapp.star_wars_characters.repository.di

import com.example.starwarsapp.star_wars_characters.repository.StarWarsRepository
import com.example.starwarsapp.star_wars_characters.repository.impl.StarWarsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StarWarsRepoModule {

    @Binds
    @Singleton
    abstract fun bindStarWarsRepository(
        starWarsRepositoryImpl : StarWarsRepositoryImpl
    ) : StarWarsRepository
}