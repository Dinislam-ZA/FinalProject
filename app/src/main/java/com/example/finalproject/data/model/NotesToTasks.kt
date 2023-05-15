package com.example.finalproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes_to_tasks_table")
data class NotesToTasks(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long? = null,
    @ColumnInfo(name = "task_id")
    val taskId: Long,
    @ColumnInfo(name = "note_id")
    val noteId: Long
)
