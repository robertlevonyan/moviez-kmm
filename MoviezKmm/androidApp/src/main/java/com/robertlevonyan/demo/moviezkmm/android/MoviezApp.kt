package com.robertlevonyan.demo.moviezkmm.android

import android.app.Application
import com.robertlevonyan.demo.moviezkmm.di.dbModule
import com.robertlevonyan.demo.moviezkmm.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class MoviezApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(dbModule + module {
            viewModel {
                MainViewModel(
                    moviesRepository = get(),
                    tvShowsRepository = get(),
                )
            }
        }
        ).apply { androidContext(this@MoviezApp) }
    }
}
