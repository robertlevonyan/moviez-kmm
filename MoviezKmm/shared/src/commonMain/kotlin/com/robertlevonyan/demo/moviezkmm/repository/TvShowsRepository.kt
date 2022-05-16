package com.robertlevonyan.demo.moviezkmm.repository

import com.robertlevonyan.demo.moviezkmm.model.TvShows
import kotlinx.coroutines.flow.Flow

interface TvShowsRepository {
    suspend fun getTvShows(): Flow<List<TvShows>>
}
