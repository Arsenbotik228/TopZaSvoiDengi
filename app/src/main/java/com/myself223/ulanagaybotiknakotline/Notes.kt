package com.myself223.ulanagaybotiknakotline

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Notes")
data class Notes(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @ColumnInfo(name = "title")
        val title: String,
        @ColumnInfo(name = "date")
        val date: String,
        @ColumnInfo(name = "desc")
        val desc: String,



): Serializable

