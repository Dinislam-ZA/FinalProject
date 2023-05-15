package com.example.finalproject.ui.tasks.taskscreatescreen

import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.finalproject.data.model.Category
import com.example.finalproject.data.model.Note
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.NoteRepo
import com.example.finalproject.data.repositories.TaskRepo
import com.example.finalproject.ui.notes.notecreatescreen.NoteCreateViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class TaskCreateViewModel(private val Repo: TaskRepo, private val categoryRepo: CategoryRepo) : ViewModel() {


    var categoryLive: MutableLiveData<Category?> = MutableLiveData(null)
    val categories = categoryRepo.getAllCategories().flowOn(Dispatchers.IO)

    private val _selectedTask = MutableLiveData<Task>()
    val selectedTask: LiveData<Task> = _selectedTask

    private val _notesForSelectedTask = Repo.getNotesForTask(selectedTask.value?.id ?: 0L).asLiveData()
    val notesForSelectedTask: LiveData<List<Note>> = _notesForSelectedTask

    // Метод для выбора задачи
    fun selectTask(task: Task) {
        _selectedTask.value = task
    }

    // Метод для добавления заметки к выбранной задаче
    suspend fun addNoteToSelectedTask(noteId: Long) {
        val selectedTaskId = selectedTask.value?.id ?: return
        Repo.addNoteToTask(noteId, selectedTaskId)
    }

    // Метод для удаления заметки из выбранной задачи
    suspend fun removeNoteFromSelectedTask(noteId: Long) {
        val selectedTaskId = selectedTask.value?.id ?: return
        Repo.removeNoteFromTask(noteId, selectedTaskId)
    }


    fun createTask(title:String, noteDes:String, categoryId:Long?){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){

            }
        }
    }

    fun updateTask(title:String, noteDes:String, categoryId:Long?){
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotBlank()&& title.isNotEmpty()){
//                val date = LocalDateTime.now().toLocalDate().toString()
//                val note = Note(noteLive.value?.id,title, noteDes, noteLive.value?.createdAt ?: date, "Author" ,categoryId)
//                noteRepo.updateNote(note)
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


                return TaskCreateViewModel(
                    TaskRepo(application.baseContext), CategoryRepo(application.baseContext)
                ) as T
            }
        }
    }
}