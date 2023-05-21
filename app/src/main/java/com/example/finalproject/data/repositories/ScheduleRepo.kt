package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.NotesToTasks
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class ScheduleRepo(val context: Context) {
    private val db = AppDatabase.getDatabase(context)
    private val taskDao = db.taskDao()

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTaskById(taskId: Int): Flow<Task> {
        return taskDao.getTaskById(taskId)
    }

    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task){
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

}