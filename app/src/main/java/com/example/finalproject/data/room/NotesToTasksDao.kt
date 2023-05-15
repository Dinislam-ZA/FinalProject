package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.NotesToTasks
import com.example.finalproject.data.model.Task
import kotlinx.coroutines.flow.Flow


@Dao
interface NotesToTasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(notesToTasks: NotesToTasks)

    @Delete
    suspend fun delete(notesToTasks: NotesToTasks)

    @Transaction
    @Query("SELECT * FROM notes_table WHERE id IN (SELECT note_id FROM notes_to_tasks_table WHERE task_id=:taskId)")
    fun getNotesForTask(taskId: Long): Flow<List<Note>>

    @Transaction
    @Query("SELECT * FROM tasks_table WHERE id IN (SELECT task_id FROM notes_to_tasks_table WHERE note_id=:noteId)")
    fun getTasksForNote(noteId: Long): Flow<List<Task>>
}