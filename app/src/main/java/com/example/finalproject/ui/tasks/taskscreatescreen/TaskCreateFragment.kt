package com.example.finalproject.ui.tasks.taskscreatescreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class TaskCreateFragment : Fragment() {

    companion object {
        fun newInstance() = TaskCreateFragment()
    }

    private lateinit var viewModel: TaskCreateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_task_create, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TaskCreateViewModel::class.java)
        // TODO: Use the ViewModel
    }

}