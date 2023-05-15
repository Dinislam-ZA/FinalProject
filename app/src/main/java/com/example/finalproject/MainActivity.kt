package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.finalproject.ui.tasks.taskscreatescreen.TaskCreateViewModel
import com.example.finalproject.ui.tasks.tasksmainscreen.TasksMainViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: TasksMainViewModel by viewModels { TasksMainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main)
    }
}