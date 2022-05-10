package com.robertlevonyan.demo.moviezkmm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedResponse<T>(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<T>,
)
