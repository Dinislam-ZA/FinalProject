package com.example.finalproject.data.model

import androidx.room.*

@Entity(tableName = "title_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"])])
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long?,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "deadline")
    var deadLine:String,
    @ColumnInfo(name = "timeOfRunning")
    var timeOfRunning:String
)
