package com.robertlevonyan.demo.moviezkmm.android

import android.app.Application
import com.robertlevonyan.demo.moviezkmm.di.initKoin
import com.robertlevonyan.demo.moviezkmm.di.dbModule

class MoviezApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(dbModule)
        }
    }
}
