package com.example.finalproject.ui.tasks.tasksmainscreen

import android.content.Context
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.SubTask
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.TaskRepo
import com.example.finalproject.workers.NotificationWorker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class TasksMainViewModel (private val taskRepo: TaskRepo,private val categoryRepo: CategoryRepo): ViewModel() {

    private val _selectedTask = MutableLiveData<Task?>()
    val selectedTask: LiveData<Task?> = _selectedTask

    private val _selectedTaskCategory = MutableLiveData<Category?>(null)
    val selectedTaskCategory: LiveData<Category?> = _selectedTaskCategory

    val tasks = taskRepo.getAllTasks().flowOn(Dispatchers.IO)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    val notesForSelectedTask: Flow<List<Note>> = _selectedTask.asFlow().flatMapLatest {
        it -> taskRepo.getNotesForTask(it?.id ?: 0)
    }.flowOn(Dispatchers.IO)
 //   val notesForSelectedTask = taskRepo.getNotesForTask(selectedTask.value?.id ?: 0).flowOn(Dispatchers.IO)

    @OptIn(ExperimentalCoroutinesApi::class)
    val subTasksForSelectedTask: Flow<List<SubTask>> = _selectedTask.asFlow().flatMapLatest {
            it -> taskRepo.getAllSubTasksByTaskId(it?.id ?: 0)
    }.flowOn(Dispatchers.IO)
  //  val subTasksForSelectedTask = taskRepo.getAllSubTasksByTaskId(selectedTask.value?.id ?: 0).flowOn(Dispatchers.IO)


    // Метод для выбора задачи
    fun selectTask(task: Task?) {
        _selectedTask.value = task
        viewModelScope.launch(Dispatchers.IO) {
            val taskCategory = task?.categorie?.let { categoryRepo.findCategoryById(it) }
            _selectedTaskCategory.postValue(taskCategory)
        }
    }


    fun createTask(title: String){
        viewModelScope.launch(Dispatchers.IO) {
            val date = LocalDateTime.now().toLocalDate().toString()
            val task = Task(title = title, createdAt = date, id = 0, lastUpdate = date)
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
                val task = Task(id = selectedTask.value?.id ?: 0, title = title,
                    deadLine = deadline,
                    taskDuration = duration,
                    executionDate = executionDate,
                    author = author,
                    status = status,
                    executionTime = executionTime ,
                    createdAt = selectedTask.value?.createdAt ?: date,
                    categorie = categoryId,
                    lastUpdate = date
                    )
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

    fun deleteSubTask(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO){
            taskRepo.deleteSubTask(subTask)
        }
    }




    fun addSubTaskToSelectedTask(subTask: SubTask){
        viewModelScope.launch(Dispatchers.IO) {
            if (selectedTask.value?.id != null) {
                val selectedTaskId = selectedTask.value?.id!!
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

    fun setNotification(context: Context, id:Long){
        val taskId = id.toInt()
        val data = workDataOf("task_id" to taskId)
        val delay = 60L

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInputData(data)
            .setInitialDelay(delay, TimeUnit.MINUTES)
            .build()

        WorkManager
            .getInstance(context)
            .enqueue(workRequest)
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