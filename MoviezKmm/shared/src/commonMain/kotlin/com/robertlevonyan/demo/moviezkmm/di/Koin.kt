package com.robertlevonyan.demo.moviezkmm.di

import com.robertlevonyan.demo.moviezkmm.constants.Constants.BASE_URL
import com.robertlevonyan.demo.moviezkmm.constants.Constants.LOCAL_DS_MOVIES
import com.robertlevonyan.demo.moviezkmm.constants.Constants.LOCAL_DS_TV
import com.robertlevonyan.demo.moviezkmm.constants.Constants.REMOTE_DS_MOVIES
import com.robertlevonyan.demo.moviezkmm.constants.Constants.REMOTE_DS_TV
import com.robertlevonyan.demo.moviezkmm.data.local.DriverFactory
import com.robertlevonyan.demo.moviezkmm.data.local.LocalMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.data.local.LocalTvShowsDataSource
import com.robertlevonyan.demo.moviezkmm.data.local.LongListAdapter
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteTvShowsDataSource
import com.robertlevonyan.demo.moviezkmm.repository.MoviesRepository
import com.robertlevonyan.demo.moviezkmm.repository.MoviesRepositoryImpl
import com.robertlevonyan.demo.moviezkmm.repository.TvShowsRepository
import com.robertlevonyan.demo.moviezkmm.repository.TvShowsRepositoryImpl
import com.robertlevonyan.demo.moviezkmm.sqldelight.Movie
import com.robertlevonyan.demo.moviezkmm.sqldelight.MoviezDb
import com.robertlevonyan.demo.moviezkmm.sqldelight.Tvshow
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initKoin(modules: List<Module>) = startKoin {
    modules(listOf(networkModule, commonModule, localModule, repositoryModule) + modules)
}

val networkModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                })
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single(named(BASE_URL)) { "https://api.themoviedb.org/3" }
}

val localModule = module {
    single {
        MoviezDb(
            driver = get<DriverFactory>().createDriver(),
            movieAdapter = Movie.Adapter(LongListAdapter()),
            tvshowAdapter = Tvshow.Adapter(LongListAdapter()),
        )
    }

    single { get<MoviezDb>().moviezQueries }

    single { get<MoviezDb>().tvShowsQueries }
}

val commonModule = module {
    single(named(LOCAL_DS_MOVIES)) { LocalMoviesDataSource(get()) }
    single(named(LOCAL_DS_TV)) { LocalTvShowsDataSource(get()) }
    single(named(REMOTE_DS_MOVIES)) {
        RemoteMoviesDataSource(
            httpClient = get(),
            baseUrl = get(named(BASE_URL)),
        )
    }
    single(named(REMOTE_DS_TV)) {
        RemoteTvShowsDataSource(
            httpClient = get(),
            baseUrl = get(named(BASE_URL)),
        )
    }
}

val repositoryModule = module {
    single<MoviesRepository> {
        MoviesRepositoryImpl(
            remoteMoviesDataSource = get(named(REMOTE_DS_MOVIES)),
            localMoviesDataSource = get(named(LOCAL_DS_MOVIES)),
        )
    }
    single<TvShowsRepository> {
        TvShowsRepositoryImpl(
            remoteTvShowsDataSource = get(named(REMOTE_DS_TV)),
            localTvShowsDataSource = get(named(LOCAL_DS_TV)),
        )
    }
}
