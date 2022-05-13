package com.robertlevonyan.demo.moviezkmm.data.local

import com.squareup.sqldelight.db.SqlDriver

expect interface DriverFactory {
    fun createDriver(): SqlDriver
}
