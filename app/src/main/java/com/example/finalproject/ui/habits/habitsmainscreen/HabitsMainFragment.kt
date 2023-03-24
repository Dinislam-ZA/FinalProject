package com.example.finalproject.ui.habits.habitsmainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalproject.R

class HabitsMainFragment : Fragment() {

    companion object {
        fun newInstance() = HabitsMainFragment()
    }

    private lateinit var viewModel: HabitsMainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_habits_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HabitsMainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}