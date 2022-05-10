package com.robertlevonyan.demo.moviezkmm.android

import android.app.Application
import com.robertlevonyan.demo.moviezkmm.di.initKoin

class MoviezApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
//            modules() // viewmodels
        }
    }
}