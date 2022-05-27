package com.robertlevonyan.demo.moviezkmm.data.remote

import com.robertlevonyan.demo.moviezkmm.constants.Constants.API_TOKEN
import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.PagedResponse
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

internal class RemoteTvShowsDataSource(
    private val httpClient: HttpClient,
    private val baseUrl: String,
) : DataSource<PagedResponse<TvShows>> {

    override suspend fun get(): PagedResponse<TvShows> {
        val get = httpClient.request("${baseUrl}/discover/tv") {
            method = HttpMethod.Get
            header("Authorization", "Bearer $API_TOKEN")
        }
        val call = get.call
        val resp = call.response
        return resp.body()
    }

}
