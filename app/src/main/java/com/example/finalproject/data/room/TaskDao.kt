package com.example.finalproject.data.room

import androidx.room.*
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks_table")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM tasks_table WHERE id = :taskId")
    fun getTaskById(taskId: Int): Flow<Task>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}

@Dao
interface SubTaskDao {

    @Query("SELECT * FROM subtasks_table WHERE task_id = :taskId")
    fun getAllSubTasksByTaskId(taskId: Int): Flow<List<SubTask>>

    @Query("SELECT * FROM subtasks_table WHERE id = :subTaskId")
    fun getSubTaskById(subTaskId: Int): Flow<SubTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask)

    @Delete
    suspend fun deleteSubTask(subTask: SubTask)
}