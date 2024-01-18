package com.example.starwarsapp.star_wars_characters.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Model for People Entity.
 */
@Entity(tableName = "people")
data class People(

    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("gender")
    var gender: String? = null,

    @SerializedName("films")
    var filmsUrls: ArrayList<String>? = null
){
    override fun toString(): String {
        var identifier = "$id | $name | $gender"
        for(film in filmsUrls ?: ArrayList()){
            identifier += " | $film"
        }
        return identifier
    }
}