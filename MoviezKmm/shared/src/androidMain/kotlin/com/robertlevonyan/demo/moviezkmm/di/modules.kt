package com.robertlevonyan.demo.moviezkmm.di

import com.robertlevonyan.demo.moviezkmm.data.local.AndroidDriverFactory
import com.robertlevonyan.demo.moviezkmm.data.local.DriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dbModule = module {
    single<DriverFactory> { AndroidDriverFactory(androidContext()) }
}
