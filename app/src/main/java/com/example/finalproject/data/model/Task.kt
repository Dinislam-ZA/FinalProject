package com.example.finalproject.data.model

import androidx.room.*

@Entity(tableName = "tasks_table", indices = [Index(value = ["title"],
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
    var taskDuration:String? = null,
    @ColumnInfo(name = "createdAt")
    var createdAt:String,
    @ColumnInfo(name = "category_id")
    var categorie: Long? = null,
    @ColumnInfo(name = "author")
    var author: String? = "Author",
    @ColumnInfo(name = "status")
    var status: Int = 0
)

@Entity(tableName = "subtasks_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["task_id"])])
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long?,
    @ColumnInfo(name = "task_id") val task_id:Long?,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "deadline")
    var deadLine:String,
    @ColumnInfo(name = "timeOfRunning")
    var taskDuration:String? = null,
    @ColumnInfo(name = "status")
    var status: Int = 0
)
