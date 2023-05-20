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

    @Update
    suspend fun updateTask(task: Task)
}

@Dao
interface SubTaskDao {

    @Query("SELECT * FROM subtasks_table WHERE task_id = :taskId ORDER BY position ASC")
    fun getAllSubTasksByTaskId(taskId: Long): Flow<List<SubTask>>

    @Query("SELECT * FROM subtasks_table WHERE id = :subTaskId")
    fun getSubTaskById(subTaskId: Long): Flow<SubTask>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask)

    @Delete
    suspend fun deleteSubTask(subTask: SubTask)

    @Update
    suspend fun updateSubTask(subTask: SubTask)
}