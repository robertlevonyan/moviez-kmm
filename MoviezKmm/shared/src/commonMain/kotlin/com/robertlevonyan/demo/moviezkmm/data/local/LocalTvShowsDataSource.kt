package com.robertlevonyan.demo.moviezkmm.data.local

import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.TvShows

internal class LocalTvShowsDataSource() : DataSource<List<TvShows>> {

    override suspend fun get(): List<TvShows> = emptyList()
}
