package com.example.starwarsapp.star_wars_films.model

import com.google.gson.annotations.SerializedName

/**
 * Model for Film Entity.
 */
data class Film(
    val title: String? = null,

    @SerializedName("release_date")
    val releaseDate : String? = null
){
    override fun toString(): String {
        return "$title | $releaseDate"
    }
}