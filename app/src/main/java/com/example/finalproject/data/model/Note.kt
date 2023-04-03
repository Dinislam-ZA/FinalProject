package com.example.finalproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes_table", indices = [Index(value = ["title"],
    unique = true)])
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long?,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "note") var note:String? = "",
    @ColumnInfo(name = "createdAt") var createdAt:String
)
