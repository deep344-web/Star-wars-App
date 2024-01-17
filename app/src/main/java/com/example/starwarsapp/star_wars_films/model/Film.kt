package com.example.starwarsapp.star_wars_films.model

import com.google.gson.annotations.SerializedName

class Film{
    var title: String? = null

    @SerializedName("episode_id")
    var episodeId = 0

    @SerializedName("opening_crawl")
    var openingCrawl: String? = null

    @SerializedName("release_date")
    var releaseDate : String? = null

    var director: String? = null
    var producer: String? = null
    var url: String? = null
    var created: String? = null
    var edited: String? = null

    @SerializedName("species")
    var speciesUrls: ArrayList<String>? = null

    @SerializedName("starships")
    var starshipsUrls: ArrayList<String>? = null

    @SerializedName("vehicles")
    var vehiclesUrls: ArrayList<String>? = null

    @SerializedName("planets")
    var planetsUrls: ArrayList<String>? = null

    @SerializedName("characters")
    var charactersUrls: ArrayList<String>? = null
}