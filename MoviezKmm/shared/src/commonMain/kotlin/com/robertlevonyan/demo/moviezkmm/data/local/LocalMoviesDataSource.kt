package com.robertlevonyan.demo.moviezkmm.data.local

import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.sqldelight.MoviezQueries

internal class LocalMoviesDataSource(private val moviezQueries: MoviezQueries) : DataSource<List<Movie>> {

    override suspend fun get(): List<Movie> = moviezQueries.selectAll().executeAsList().map { dbMovie ->
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

    fun insertMovies(vararg movies: Movie) {
        movies.map { movie ->
            com.robertlevonyan.demo.moviezkmm.sqldelight.Movie(
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
