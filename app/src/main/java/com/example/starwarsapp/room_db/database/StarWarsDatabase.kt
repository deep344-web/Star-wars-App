package com.example.starwarsapp.room_db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwarsapp.room_db.dao.StarWarsDao
import com.example.starwarsapp.room_db.type_converters.StarWarsTypeConverters
import com.example.starwarsapp.star_wars_characters.model.People
import com.example.starwarsapp.star_wars_films.model.Film

@Database(
    entities = [People::class, Film::class],
    version = 2 ,
    exportSchema = true
)
@TypeConverters(StarWarsTypeConverters::class)
abstract class StarWarsDatabase : RoomDatabase(){
    abstract val dao : StarWarsDao
}