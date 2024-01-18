package com.example.starwarsapp.star_wars_characters.model

import com.google.gson.annotations.SerializedName

/**
 * Response Model for List of People getting from API.
 */
class PeopleList {
    @SerializedName("results")
    var results: ArrayList<People>? = null
}