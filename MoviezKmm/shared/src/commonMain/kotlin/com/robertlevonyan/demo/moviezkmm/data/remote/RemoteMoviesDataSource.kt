package com.robertlevonyan.demo.moviezkmm.data.remote

import com.robertlevonyan.demo.moviezkmm.constants.Constants.API_TOKEN
import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.PagedResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class RemoteMoviesDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : DataSource<PagedResponse<Movie>> {

    override suspend fun get(): PagedResponse<Movie> {
        val get = httpClient.request("${baseUrl}/discover/movie") {
            method = HttpMethod.Get
            header("Authorization", "Bearer $API_TOKEN")
        }
        val call = get.call
        val resp = call.response
        return resp.body()
    }
}
