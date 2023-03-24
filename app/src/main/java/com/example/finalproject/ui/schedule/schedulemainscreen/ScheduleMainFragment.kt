package com.example.finalproject.ui.schedule.schedulemainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class ScheduleMainFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleMainFragment()
    }

    private lateinit var viewModel: ScheduleMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScheduleMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}