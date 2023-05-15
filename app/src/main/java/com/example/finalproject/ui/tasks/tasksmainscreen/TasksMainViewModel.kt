package com.example.finalproject.ui.tasks.tasksmainscreen

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.TaskRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TasksMainViewModel (private val taskRepo: TaskRepo,private val categoryRepo: CategoryRepo): ViewModel() {

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> = _selectedTask

    val tasks = taskRepo.getAllTasks().flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)
    val notesForSelectedTask = taskRepo.getNotesForTask(selectedTask.value?.id ?: 0L).flowOn(Dispatchers.IO)
    val subTasksForSelectedTask = taskRepo.getAllSubTasksByTaskId(selectedTask.value?.id ?: 0L).flowOn(Dispatchers.IO)

    var categoryLive: MutableLiveData<Category?> = MutableLiveData(null)


    // Метод для выбора задачи
    fun selectTask(task: Task?) {
        _selectedTask.value = task
    }


    fun createTask(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            val date = LocalDateTime.now().toLocalDate().toString()
            val task = Task(title = title, createdAt = date)
            taskRepo.insertTask(task)
        }
    }
    fun createOrUpdateTask(createOrUpdate:Boolean,
                           title:String,
                   deadline:Long?,
                   duration: Long?,
                   executionDate: Long?,
                   executionTime: Long?,
                   author:Long?,
                   status: Int , categoryId:Long?){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){
                val date = LocalDateTime.now().toLocalDate().toString()
                val task = Task(id = selectedTask.value?.id, title = title,
                    deadLine = deadline,
                    taskDuration = duration,
                    executionDate = executionDate,
                    author = author,
                    status = status,
                    executionTime = executionTime ,
                    createdAt = date,
                    categorie = categoryId)
                if(createOrUpdate){
                    taskRepo.insertTask(task)
                }
                else{
                    taskRepo.updateTask(task)
                }
            }
        }
    }


    fun deleteTask(task: Task){
        viewModelScope.launch(Dispatchers.IO) {
            taskRepo.deleteTask(task)
        }
    }

    fun createSubTask(title: String, status: Boolean, position: Int){
        viewModelScope.launch(Dispatchers.IO){
            val subTask = SubTask(title = title, status = status, task_id = selectedTask.value?.id!!, position = position)
            taskRepo.insertSubTask(subTask)
        }
    }

    fun deleteSubTask(title: String, status: Boolean, position: Int){
        viewModelScope.launch(Dispatchers.IO){
            val subTask = SubTask(title = title, status = status, task_id = selectedTask.value?.id!!, position = position)
            taskRepo.deleteSubTask(subTask)
        }
    }

    // Метод для добавления заметки к выбранной задаче
    fun addNoteToSelectedTask(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedTask.value?.id != null) {
                val selectedTaskId = selectedTask.value?.id!!
                taskRepo.addNoteToTask(noteId, selectedTask.value?.id!!)
            }
        }
    }

    fun addSubTaskToSelectedTask(title: String,status: Boolean, position: Int){
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedTask.value?.id != null) {
                val selectedTaskId = selectedTask.value?.id!!
                val subTask = SubTask(task_id = selectedTaskId, title = title, status = status, position = position)
                taskRepo.insertSubTask(subTask)
            }
        }
    }

    // Метод для удаления заметки из выбранной задачи
    fun removeNoteFromSelectedTask(noteId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedTask.value?.id != null) {
                taskRepo.removeNoteFromTask(noteId, selectedTask.value?.id!!)
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])
                // Create a SavedStateHandle for this ViewModel from extras


                return TasksMainViewModel(
                    TaskRepo(application.baseContext),  CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}