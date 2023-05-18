package com.example.finalproject.data.model

import androidx.room.*

@Entity(tableName = "tasks_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Category::class,
        parentColumns = ["id"],
        childColumns = ["category_id"])])
data class Task(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "deadline")
    var deadLine:Long? = null,
    @ColumnInfo(name = "timeOfRunning")
    var taskDuration:Long? = null,
    @ColumnInfo(name = "createdAt")
    var createdAt:String,
    @ColumnInfo(name = "category_id")
    var categorie: Long? = null,
    @ColumnInfo(name = "author")
    var author: Long? = null,
    @ColumnInfo(name = "status")
    var status: Int = 0,
    @ColumnInfo(name = "execution_date")
    var executionDate:Long? = null,
    @ColumnInfo(name = "execution_time")
    var executionTime:Long? = null
)

@Entity(tableName = "subtasks_table", indices = [Index(value = ["title"],
    unique = true)], foreignKeys = [
    ForeignKey(entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["task_id"])])
data class SubTask(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id:Long? = null,
    @ColumnInfo(name = "task_id") val task_id:Long?,
    @ColumnInfo(name = "title")
    var title:String,
    @ColumnInfo(name = "status")
    var status: Boolean,
    @ColumnInfo(name = "position")
    var position:Int
)
