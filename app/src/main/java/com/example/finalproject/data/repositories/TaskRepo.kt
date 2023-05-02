package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class TaskRepo(val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val taskDao = db.taskDao()
    private val subTaskDao = db.subTaskDao()

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTaskById(taskId: Int): Flow<Task> {
        return taskDao.getTaskById(taskId)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    fun getAllSubTasksByTaskId(taskId: Int): Flow<List<SubTask>> {
        return subTaskDao.getAllSubTasksByTaskId(taskId)
    }

    fun getSubTaskById(subTaskId: Int): Flow<SubTask> {
        return subTaskDao.getSubTaskById(subTaskId)
    }

    suspend fun insertSubTask(subTask: SubTask) {
        subTaskDao.insertSubTask(subTask)
    }

    suspend fun deleteSubTask(subTask: SubTask) {
        subTaskDao.deleteSubTask(subTask)
    }
}