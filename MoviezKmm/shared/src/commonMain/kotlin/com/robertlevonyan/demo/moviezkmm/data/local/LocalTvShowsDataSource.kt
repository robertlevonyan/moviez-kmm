package com.robertlevonyan.demo.moviezkmm.data.local

import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import com.robertlevonyan.demo.moviezkmm.sqldelight.TvShowsQueries
import com.robertlevonyan.demo.moviezkmm.sqldelight.Tvshow

internal class LocalTvShowsDataSource(private val tvShowsQueries: TvShowsQueries) : DataSource<List<TvShows>> {

    override suspend fun get(): List<TvShows> = tvShowsQueries.selectAll().executeAsList().map { dbTvShow ->
        TvShows(
            id = dbTvShow._id,
            backdropPath = dbTvShow.backdrop_path.orEmpty(),
            posterPath = dbTvShow.poster_path.orEmpty(),
            genreIds = dbTvShow.genre_ids.orEmpty(),
            originalName = dbTvShow.original_name.orEmpty(),
            name = dbTvShow.name.orEmpty(),
            overview = dbTvShow.overview.orEmpty(),
        )
    }

    fun insertShows(vararg shows: TvShows) {
        shows.map { tvShow ->
            Tvshow(
                _id = tvShow.id,
                backdrop_path = tvShow.backdropPath,
                poster_path = tvShow.posterPath,
                genre_ids = tvShow.genreIds,
                original_name = tvShow.originalName,
                name = tvShow.name,
                overview = tvShow.overview,
            )
        }.forEach(tvShowsQueries::insert)
    }
}
