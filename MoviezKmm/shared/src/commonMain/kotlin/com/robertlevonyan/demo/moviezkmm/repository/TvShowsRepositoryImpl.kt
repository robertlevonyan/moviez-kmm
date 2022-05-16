package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteTvShowsDataSource
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import com.robertlevonyan.demo.moviezkmm.sqldelight.TvShowsQueries
import com.robertlevonyan.demo.moviezkmm.sqldelight.Tvshow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TvShowsRepositoryImpl(
    private val remoteTvShowsDataSource: RemoteTvShowsDataSource,
    private val tvShowsQueries: TvShowsQueries,
) : TvShowsRepository {
    override suspend fun getTvShows(): Flow<List<TvShows>> = flow {
        emit(getLocalShows())
        emit(getAndStoreRemoteShows())
    }

    private fun getLocalShows() = tvShowsQueries.selectAll().executeAsList().map { dbTvShow ->
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

    private suspend fun getAndStoreRemoteShows(): List<TvShows> =
        remoteTvShowsDataSource.get().results.also { movies ->
            movies.map { tvShow ->
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
