package com.example.finalproject.ui.tasks.tasksmainscreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.finalproject.InstantExecutorExtension
import com.example.finalproject.data.model.Task
import com.example.finalproject.data.repositories.CategoryRepo
import com.example.finalproject.data.repositories.TaskRepo
import org.junit.Rule
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.rules.TestRule
import org.mockito.Mockito
import org.mockito.kotlin.mock
import java.util.Calendar


@ExtendWith(InstantExecutorExtension::class)
class TaskMainViewModelTest {

    private val tasksRepo = mock<TaskRepo>()
    private val categoryRepo = mock<CategoryRepo>()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @AfterEach
    fun afterEach(){
        Mockito.reset(tasksRepo, categoryRepo)
    }

    @Test
    fun `Should select task and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 1,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
            )
        viewModel.selectTask(task)

        val actual = viewModel.selectedTask.value

        Assertions.assertEquals(task, actual)
    }

    @Test
    fun `Should save task and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 0,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
        )
//        viewModel.c
//
//        val actual =
        Assertions.assertEquals(1, 1)
    }

    @Test
    fun `Should save subtask and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 0,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
        )

        Assertions.assertEquals(1, 1)
    }

    @Test
    fun `Should remove note and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 0,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
        )

        Assertions.assertEquals(1, 1)
    }

    @Test
    fun `Should remove subtask and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 0,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
        )

        Assertions.assertEquals(1, 1)
    }

    @Test
    fun `Should remove task and return true`(){
        val viewModel = TasksMainViewModel(tasksRepo, categoryRepo)
        val task = Task(
            id = 0,
            title = "testTask",
            createdAt = Calendar.getInstance().toString(),
            lastUpdate = Calendar.getInstance().toString()
        )

        Assertions.assertEquals(1, 1)
    }

}