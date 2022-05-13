package com.robertlevonyan.demo.moviezkmm.data.local

import com.squareup.sqldelight.db.SqlDriver

actual interface DriverFactory {
    actual fun createDriver(): SqlDriver
}
