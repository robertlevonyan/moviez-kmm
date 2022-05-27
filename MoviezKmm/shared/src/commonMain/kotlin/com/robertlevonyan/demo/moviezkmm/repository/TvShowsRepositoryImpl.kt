package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.data.local.LocalTvShowsDataSource
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteTvShowsDataSource
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class TvShowsRepositoryImpl(
    private val remoteTvShowsDataSource: RemoteTvShowsDataSource,
    private val localTvShowsDataSource: LocalTvShowsDataSource,
) : TvShowsRepository {
    override fun getTvShows(): Flow<List<TvShows>> = flow {
        emit(localTvShowsDataSource.get())
        emit(getAndStoreRemoteShows())
    }

    private suspend fun getAndStoreRemoteShows(): List<TvShows> =
        remoteTvShowsDataSource.get().results.also { movies ->
            localTvShowsDataSource.insertShows(*movies.toTypedArray())
        }
}
