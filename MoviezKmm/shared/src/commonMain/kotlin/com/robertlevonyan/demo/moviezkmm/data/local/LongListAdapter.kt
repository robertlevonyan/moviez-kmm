package com.robertlevonyan.demo.moviezkmm.data.local

import com.squareup.sqldelight.ColumnAdapter

class LongListAdapter : ColumnAdapter<List<Long>, String> {
    override fun decode(databaseValue: String): List<Long> =
        databaseValue.split(SEPARATOR).map { it.toLongOrNull() ?: 0L }

    override fun encode(value: List<Long>): String =
        value.joinToString(SEPARATOR)

    companion object {
        private const val SEPARATOR = ","
    }
}
