package com.example.finalproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Categories_table")
data class Category(
    @PrimaryKey(true)
    val id:Long?,
    @ColumnInfo(name = "name")
    var name:String,
    @ColumnInfo(name = "color")
    var color:Int
)
