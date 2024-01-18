package com.example.starwarsapp.room_db.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabaseObject(@ApplicationContext appContext: Context) : StarWarsDatabase {
        return Room.databaseBuilder(
            appContext,
            StarWarsDatabase::class.java,
            "starwars.db").build()
    }

}

