package com.robertlevonyan.demo.moviezkmm.data.remote

import com.robertlevonyan.demo.moviezkmm.constants.Constants.API_TOKEN
import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.PagedResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

internal class RemoteMoviesDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : DataSource<PagedResponse<Movie>> {

    override suspend fun get(): PagedResponse<Movie> = httpClient.get {
        host = "$baseUrl/discover/movie"
        headers { append("Authorization", "Bearer $API_TOKEN") }
    }.call.response.body()
}
