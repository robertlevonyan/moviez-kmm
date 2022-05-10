package com.robertlevonyan.demo.moviezkmm.data

interface DataSource<Data> {
    suspend fun get(): Data
}
