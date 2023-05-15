package com.example.finalproject.data.repositories

import android.content.Context
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.NotesToTasks
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.room.AppDatabase
import kotlinx.coroutines.flow.Flow

class TaskRepo(val context: Context) {

    private val db = AppDatabase.getDatabase(context)
    private val taskDao = db.taskDao()
    private val subTaskDao = db.subTaskDao()
    private val notesToTasksDao = db.notesToTasksDao()

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

    fun getAllSubTasksByTaskId(taskId: Long): Flow<List<SubTask>> {
        return subTaskDao.getAllSubTasksByTaskId(taskId)
    }

    fun getSubTaskById(subTaskId: Long): Flow<SubTask> {
        return subTaskDao.getSubTaskById(subTaskId)
    }

    suspend fun insertSubTask(subTask: SubTask) {
        subTaskDao.insertSubTask(subTask)
    }

    suspend fun updateSubTask(subTask: SubTask){
        subTaskDao.updateSubTask(subTask)
    }

    suspend fun deleteSubTask(subTask: SubTask) {
        subTaskDao.deleteSubTask(subTask)
    }

    // Метод для добавления заметки к задаче
    suspend fun addNoteToTask(noteId: Long, taskId: Long) {
        val notesToTasks = NotesToTasks(null,noteId, taskId)
        notesToTasksDao.insert(notesToTasks)
    }

    // Метод для удаления заметки из задачи
    suspend fun removeNoteFromTask(noteId: Long, taskId: Long) {
        val notesToTasks = NotesToTasks(null,noteId, taskId)
        notesToTasksDao.delete(notesToTasks)
    }

    // Метод для получения списка заметок, связанных с задачей
    fun getNotesForTask(taskId: Long): Flow<List<Note>> {
        return notesToTasksDao.getNotesForTask(taskId)
    }

    // Метод для получения списка задач, связанных с заметкой
    fun getTasksForNote(noteId: Long): Flow<List<Task>> {
        return notesToTasksDao.getTasksForNote(noteId)
    }
}