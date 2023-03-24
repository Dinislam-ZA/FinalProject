package com.example.finalproject.ui.tasks.tasksmainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class TasksMainFragment : Fragment() {

    companion object {
        fun newInstance() = TasksMainFragment()
    }

    private lateinit var viewModel: TasksMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TasksMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}