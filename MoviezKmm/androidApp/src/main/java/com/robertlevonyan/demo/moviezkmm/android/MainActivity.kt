package com.robertlevonyan.demo.moviezkmm.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.robertlevonyan.demo.moviezkmm.model.Movie
import com.robertlevonyan.demo.moviezkmm.model.TvShows
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box {
                    val viewModel = getViewModel<MainViewModel>()

                    val movies by viewModel.movies.collectAsState()
                    val tvShows by viewModel.tvShows.collectAsState()

                    LazyVerticalGrid(columns = GridCells.Adaptive(20.dp)) {
                        if (movies.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Text(text = "Movies", modifier = Modifier.padding(8.dp))
                            }
                            items(movies.size) { index ->
                                val movie = movies[index]

                                Card(modifier = Modifier.padding(8.dp)) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        model = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
                                        contentDescription = null,
                                    )
                                }
                            }
                        }

                        if (tvShows.isNotEmpty()) {
                            item(span = { GridItemSpan(2) }) {
                                Text(text = "TV Shows", modifier = Modifier.padding(8.dp))
                            }
                            items(count = tvShows.size) { index ->
                                val tvShow = tvShows[index]

                                val imagePath = tvShow.backdropPath ?: tvShow.posterPath

                                Card(modifier = Modifier.padding(8.dp)) {
                                    AsyncImage(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight(),
                                        model = "https://image.tmdb.org/t/p/w500/$imagePath",
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }

//                    TabRow(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(tabHeight),
//                        selectedTabIndex = selectedTab,
//                    ) {
//                        tabTitles.forEachIndexed { index, title ->
//                            Tab(
//                                selected = selectedTab == index,
//                                onClick = { selectedTab = index },
//                                text = { Text(text = title) }
//                            )
//                        }
//                    }
//
//                    HorizontalPager(
//                        modifier = Modifier
//                            .padding(top = tabHeight)
//                            .fillMaxSize(),
//                        state = pagerState,
//                    ) { tabIndex ->
//                        when (tabIndex) {
//                            0 -> Movies(movies)
//                            1 -> TvShows(tvShows)
//                            else -> throw IndexOutOfBoundsException("Error!")
//                        }
//                    }
                }
            }
        }
    }

    @Composable
    private fun Movies(movies: List<Movie>) {
        if (movies.isEmpty()) return

        LazyColumn(content = {
            items(movies.size) { index ->
                val movie = movies[index]

                Card(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        model = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
                        contentDescription = null,
                    )
                }
            }
        })
    }

    @Composable
    private fun TvShows(shows: List<TvShows>) {
        if (shows.isEmpty()) return

        LazyColumn(content = {
            items(shows.size) { index ->
                val show = shows[index]

                Card(modifier = Modifier.padding(8.dp)) {
                    AsyncImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        model = "https://image.tmdb.org/t/p/w500${show.backdropPath}",
                        contentDescription = null,
                    )
                }
            }
        })
    }
}
