package com.robertlevonyan.demo.moviezkmm.data.local

import android.content.Context
import com.robertlevonyan.demo.moviezkmm.sqldelight.MoviezDb
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

class AndroidDriverFactory(private val context: Context) : DriverFactory {
    override fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(MoviezDb.Schema, context, "moviez.db")
    }
}
