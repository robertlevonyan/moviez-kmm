package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.PagedResponse
import com.robertlevonyan.demo.moviezkmm.sqldelight.MoviezQueries
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.robertlevonyan.demo.moviezkmm.sqldelight.Movie as DbMovie

internal class MoviesRepositoryImpl(
    private val remoteMoviesDataSource: DataSource<PagedResponse<Movie>>,
    private val moviezQueries: MoviezQueries,
) : MoviesRepository {
    override suspend fun getMovies(): Flow<List<Movie>> = flow {
        emit(getLocalMovies())
        emit(getAndStoreRemoteMovies())
    }

    private fun getLocalMovies() = moviezQueries.selectAll().executeAsList().map { dbMovie ->
        Movie(
            id = dbMovie._id,
            backdropPath = dbMovie.backdrop_path.orEmpty(),
            posterPath = dbMovie.poster_path.orEmpty(),
            genreIds = dbMovie.genre_ids.orEmpty(),
            originalTitle = dbMovie.original_title.orEmpty(),
            title = dbMovie.title.orEmpty(),
            overview = dbMovie.overview.orEmpty(),
        )
    }

    private suspend fun getAndStoreRemoteMovies(): List<Movie> =
        remoteMoviesDataSource.get().results.also { movies ->
            movies.map { movie ->
                DbMovie(
                    _id = movie.id,
                    backdrop_path = movie.backdropPath,
                    poster_path = movie.posterPath,
                    genre_ids = movie.genreIds,
                    original_title = movie.originalTitle,
                    title = movie.title,
                    overview = movie.overview,
                )
            }.forEach(moviezQueries::insert)
        }
}
