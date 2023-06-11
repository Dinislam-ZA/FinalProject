package com.example.finalproject.data.model

import androidx.room.*

@Entity(tableName = "habits_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"])])
data class Habit(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "description") var description:String = "",
    @ColumnInfo(name = "createdAt") val createdAt:String,
    @ColumnInfo(name = "lastUpdate") val lastUpdate:String,
    @ColumnInfo(name = "date") var date:Long,
    @ColumnInfo(name = "time") var time:Long,
    @ColumnInfo(name = "period") var period:Long,
    @ColumnInfo(name = "category_id") var categorie: Long? = null
)
