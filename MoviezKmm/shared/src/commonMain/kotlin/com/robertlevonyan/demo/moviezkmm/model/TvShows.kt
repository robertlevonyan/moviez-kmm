package com.robertlevonyan.demo.moviezkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TvShows(
    @SerialName("id")
    val id: Long,
    @SerialName("backdrop_path")
    val backdropPath: String,
    @SerialName("poster_path")
    val posterPath: String,
    @SerialName("genre_ids")
    val genreIds: List<Long>,
    @SerialName("original_name")
    val originalName: String,
    @SerialName("name")
    val name: String,
    @SerialName("overview")
    val overview: String,
)
