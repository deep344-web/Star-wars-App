package com.example.starwarsapp.star_wars_characters.model

import com.google.gson.annotations.SerializedName


class PeopleList {
    @SerializedName("count")
    val count = 0

    @SerializedName("next")
    var next: String? = null

    @SerializedName("previous")
    var previous: String? = null

    @SerializedName("results")
    var results: ArrayList<People>? = null
}