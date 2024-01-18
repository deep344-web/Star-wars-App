package com.example.starwarsapp.room_db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.starwarsapp.star_wars_characters.model.People
import com.example.starwarsapp.star_wars_films.model.Film

@Dao
interface StarWarsDao {
    @Insert
    suspend fun insertCharacter(people: People)

    @Query("DELETE FROM people")
    fun deleteAllData()

    @Query("SELECT * FROM people")
    suspend fun getAllCharacters() : List<People>

    @Insert
    suspend fun insertFilm(film: Film)
    @Query("SELECT * FROM films WHERE url = :filmURL")
    suspend fun getFilmByURL(filmURL : String) : Film?
}