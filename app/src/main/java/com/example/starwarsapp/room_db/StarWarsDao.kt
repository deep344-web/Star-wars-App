package com.example.starwarsapp.room_db

import People
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StarWarsDao {
    @Insert
    suspend fun insertCharacter(people: People)

    @Delete
    suspend fun deleteCharacter()

    @Query("SELECT * FROM people")
    suspend fun getAllCharacters() : List<People>
}