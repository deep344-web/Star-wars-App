package com.example.starwarsapp.star_wars_films.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Model for Film Entity.
 */

@Entity(tableName = "films")
data class Film(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val title: String? = null,

    @SerializedName("release_date")
    val releaseDate : String? = null,

    @SerializedName("url")
    val url : String? = null
){
    override fun toString(): String {
        return "$title | $releaseDate"
    }
}