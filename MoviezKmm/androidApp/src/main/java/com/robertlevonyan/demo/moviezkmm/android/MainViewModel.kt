package com.robertlevonyan.demo.moviezkmm.android

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import com.robertlevonyan.demo.moviezkmm.repository.MoviesRepository
import com.robertlevonyan.demo.moviezkmm.repository.TvShowsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(
    moviesRepository: MoviesRepository,
    tvShowsRepository: TvShowsRepository,
) : ViewModel() {
    val movies = MutableStateFlow<List<Movie>>(emptyList())
    val tvShows = MutableStateFlow<List<TvShows>>(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getMovies().collectLatest {
                movies.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            tvShowsRepository.getTvShows().collectLatest {
                tvShows.value = it
            }
        }
    }
}
