package com.robertlevonyan.demo.moviezkmm.di

import com.robertlevonyan.demo.moviezkmm.constants.Constants.BASE_URL
import com.robertlevonyan.demo.moviezkmm.constants.Constants.LOCAL_DS_MOVIES
import com.robertlevonyan.demo.moviezkmm.constants.Constants.LOCAL_DS_TV
import com.robertlevonyan.demo.moviezkmm.constants.Constants.REMOTE_DS_MOVIES
import com.robertlevonyan.demo.moviezkmm.constants.Constants.REMOTE_DS_TV
import com.robertlevonyan.demo.moviezkmm.data.DataSource
import com.robertlevonyan.demo.moviezkmm.data.local.LocalMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteMoviesDataSource
import com.robertlevonyan.demo.moviezkmm.data.remote.RemoteTvShowsDataSource
import io.ktor.client.*
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(networkModule, commonModule)
}

// called by iOS
fun initKoin() = initKoin {
    modules(networkModule, commonModule)
}

val networkModule = module {
    single { HttpClient() }

    single(named(BASE_URL)) { "https://api.themoviedb.org/3/" }
}

val commonModule = module {
    single<DataSource<*>>(named(LOCAL_DS_MOVIES)) { LocalMoviesDataSource() }
    single<DataSource<*>>(named(LOCAL_DS_TV)) { LocalMoviesDataSource() }
    single<DataSource<*>>(named(REMOTE_DS_MOVIES)) {
        RemoteMoviesDataSource(
            httpClient = get(),
            baseUrl = get(named(BASE_URL)),
        )
    }
    single<DataSource<*>>(named(REMOTE_DS_TV)) {
        RemoteTvShowsDataSource(
            httpClient = get(),
            baseUrl = get(named(BASE_URL)),
        )
    }
}
