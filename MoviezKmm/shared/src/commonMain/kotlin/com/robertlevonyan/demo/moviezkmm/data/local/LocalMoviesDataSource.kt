package com.robertlevonyan.demo.moviezkmm.data.local

import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie

internal class LocalMoviesDataSource() : DataSource<List<Movie>> {

    override suspend fun get(): List<Movie> = emptyList()
}
