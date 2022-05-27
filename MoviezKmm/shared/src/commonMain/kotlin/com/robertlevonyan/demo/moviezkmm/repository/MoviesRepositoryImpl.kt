package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.data.local.LocalMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: RemoteMoviesDataSource,
    private val localMoviesDataSource: LocalMoviesDataSource,
) : MoviesRepository {
    override fun getMovies(): Flow<List<Movie>> = flow {
        emit(localMoviesDataSource.get())
        emit(getAndStoreRemoteMovies())
    }

    private suspend fun getAndStoreRemoteMovies(): List<Movie> =
        remoteMoviesDataSource.get().results.also { movies ->
            localMoviesDataSource.insertMovies(*movies.toTypedArray())
        }
}
