package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.model.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun getMovies(): Flow<List<Movie>>
}
