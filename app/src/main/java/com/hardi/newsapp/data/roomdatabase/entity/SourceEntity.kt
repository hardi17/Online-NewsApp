package me.hardi.newsapp.data.local.entity

import androidx.room.ColumnInfo

data class SourceEntity (
    @ColumnInfo(name = "sourceId")
    val id: String?,
    @ColumnInfo(name = "sourceName")
    val name: String = ""
)