package com.example.finalproject.ui.schedule.schedulemainscreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.finalproject.R
import com.example.finalproject.databinding.FragmentCategoriesBinding
import com.example.finalproject.databinding.FragmentScheduleMainBinding
import com.example.finalproject.ui.profile.categoriesscreen.CategoriesViewModel

class ScheduleMainFragment : Fragment() {

    companion object {
        fun newInstance() = ScheduleMainFragment()
    }

    private val viewModel: ScheduleMainViewModel by viewModels { ScheduleMainViewModel.Factory }
    private lateinit var binding: FragmentScheduleMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            binding.root.findNavController().navigate(R.id.action_scheduleMainFragment_to_habitsMainFragment)
        }
    }

}