package com.example.finalproject.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "notes_to_tasks_table", primaryKeys = ["note_id", "task_id"], foreignKeys = [
    ForeignKey(entity = Task::class,
        parentColumns = ["id"],
        childColumns = ["task_id"],
        onDelete = ForeignKey.CASCADE
        ),
    ForeignKey(entity = Note::class,
        parentColumns = ["id"],
        childColumns = ["note_id"],
        onDelete = ForeignKey.CASCADE
        )])
data class NotesToTasks(
    @ColumnInfo(name = "task_id")
    val taskId: Long,
    @ColumnInfo(name = "note_id")
    val noteId: Long
)
