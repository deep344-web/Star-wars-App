package com.example.starwarsapp.filters.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FilterResponse(
    val sortBy: SortBy?,
    val filterMale : Boolean = false,
    val filterFemale : Boolean = false,
    val filterOthers : Boolean = false
) : Parcelable

enum class SortBy(val value: String) {
    NAME("Name"),
}